package com.server.RandomDungeonFighter.session;

import com.google.gson.*;
import com.server.RandomDungeonFighter.battleground.Dungeon;
import com.server.RandomDungeonFighter.dao.*;
import com.server.RandomDungeonFighter.entity.Account;
import com.server.RandomDungeonFighter.entity.Card;
import com.server.RandomDungeonFighter.entity.Character;
import com.server.RandomDungeonFighter.entity.Item;
import com.server.RandomDungeonFighter.parser.ClientResponseParser;
import com.server.RandomDungeonFighter.parser.ServerResponseParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SingleConnectionThread implements Runnable{

    private final AccountDAO accountDAO;
    private final CharacterDAO characterDAO;
    private final DeckDAO deckDAO;
    private final CardDAO cardDAO;
    private final InventoryDAO inventoryDAO;
    private final ItemDAO itemDAO;
    private final Socket client;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Account account;
    private final JsonParser jsonParser = new JsonParser();
    private final Gson gson = new Gson();
    private final ServerResponseParser serverResponseParser = new ServerResponseParser();
    private final ClientResponseParser clientResponseParser = new ClientResponseParser();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final HttpPost postCreate = new HttpPost("http://localhost:8080/api/character/create");
    private final HttpPost postDelete = new HttpPost("http://localhost:8080/api/account/character/delete");
    private Dungeon dungeon;
    private int currentCharacter;

    public SingleConnectionThread(AccountDAO accountDAO, CharacterDAO characterDAO, DeckDAO deckDAO, CardDAO cardDAO,
                                  InventoryDAO inventoryDAO, ItemDAO itemDAO, Socket client){
        this.accountDAO = accountDAO;
        this.characterDAO = characterDAO;
        this.client = client;
        this.deckDAO = deckDAO;
        this.cardDAO = cardDAO;
        this.inventoryDAO = inventoryDAO;
        this.itemDAO = itemDAO;
        currentCharacter = -1;
    }

    @Override
    public void run() {
        try{
            outputStream = new DataOutputStream(client.getOutputStream());
            inputStream = new DataInputStream(client.getInputStream());
            StringBuilder message = new StringBuilder();
            JsonElement jsonElement;
            JsonObject jsonObject;

            byte[] byteMessage = new byte[256];
            int bytes;

            while (!client.isClosed()) {
                bytes = inputStream.read(byteMessage);
                message.append(new String(byteMessage, StandardCharsets.US_ASCII));
                System.out.println(message);
                jsonElement = jsonParser.parse(message.toString().trim());
                jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.get("capture").getAsString().equals("login")){
                    if (accountDAO.doesAccountExistByLogin(jsonObject.get("login").getAsString())){
                        account = accountDAO.getAccountByLogin(jsonObject.get("login").getAsString());
                        if (account.getPassword().equals(jsonObject.get("password").getAsString())){
                            jsonObject = new JsonObject();
                            jsonObject.addProperty("capture", "loginResult");
                            jsonObject.addProperty("result", "true");
                            sendToClient(jsonObject);
                            sendToClient(serverResponseParser
                                    .getJsonObjectWithCharacters(
                                            characterDAO.getCharactersByAccountID(account.getId()),
                                            deckDAO));
                            if (characterDAO.getCharactersByAccountID(account.getId()).size() > 0){
                                currentCharacter = 0;
                            }
                            message.setLength(0);
                            byteMessage = new byte[256];
                        }
                    }
                    else {
                        jsonObject = new JsonObject();
                        jsonObject.addProperty("capture", "loginResult");
                        jsonObject.addProperty("result", "false");
                        sendToClient(jsonObject);
                        message.setLength(0);
                        byteMessage = new byte[256];
                    }
                }

                if (jsonObject.get("capture").getAsString().equals("logout")){
                    if (dungeon != null){
                        dungeon = null;
                    }
                    break;
                }

                if (jsonObject.get("capture").getAsString().equals("newCharacter")){
                    JsonArray array = jsonObject.get("characters").getAsJsonArray();
                    List<NameValuePair> urlParameters = new ArrayList<>();
                    urlParameters.add(new BasicNameValuePair("accountLogin", account.getLogin()));
                    urlParameters.add(new BasicNameValuePair("className", array.get(0).getAsJsonObject().get("className").getAsString()));
                    urlParameters.add(new BasicNameValuePair("nickname", array.get(0).getAsJsonObject().get("nickname").getAsString()));
                    urlParameters.add(new BasicNameValuePair("level", "1"));
                    postCreate.setEntity(new UrlEncodedFormEntity(urlParameters));
                    String status = httpClient.execute(postCreate).getStatusLine().toString();
                    if (status.substring(9).trim().equals("200")){
                        jsonObject = new JsonObject();
                        jsonObject.addProperty("capture", "newCharacterResult");
                        jsonObject.addProperty("result", "true");
                        sendToClient(jsonObject);
                        sendToClient(serverResponseParser
                                .getJsonObjectWithCharacters(
                                        characterDAO.getCharactersByAccountLogin(account.getLogin()),
                                        deckDAO));
                    }
                    message.setLength(0);
                    byteMessage = new byte[256];
                }

                if (jsonObject.get("capture").getAsString().equals("chooseCharacter")){
                    currentCharacter = jsonObject.get("id").getAsInt();
                }

                if (jsonObject.get("capture").getAsString().equals("newDungeon")){
                    if (currentCharacter > -1)
                    dungeon = new Dungeon(this,
                            deckDAO.getDeckByCharacterID(characterDAO.getCharactersByAccountID(account.getId()).get(currentCharacter).getId()),
                            characterDAO.getCharactersByAccountID(account.getId()).get(currentCharacter),
                            inventoryDAO.getInventoryByCharacterID(characterDAO.getCharactersByAccountID(account.getId()).get(currentCharacter).getId()),
                            itemDAO.getItemsByInventoryID(inventoryDAO.getInventoryByCharacterID(characterDAO.getCharactersByAccountID(account.getId()).get(currentCharacter).getId()).getId()),
                            serverResponseParser);
                }

                if (jsonObject.get("capture").getAsString().equals("throwDice")){
                    if (dungeon != null){
                        dungeon.throwDice();
                    }
                }

                if (jsonObject.get("capture").getAsString().equals("moveToNewCell")){
                    if (dungeon != null){
                        dungeon.moveToNewCell();
                    }
                }

                if (jsonObject.get("capture").getAsString().equals("finishBattle")){
                    if (dungeon != null){
                        dungeon.finishBattle();
                    }
                }

                if (jsonObject.get("capture").getAsString().equals("finishDungeon")){
                    if (dungeon != null){
                        dungeon.finishDungeon();
                    }
                }

                message.setLength(0);
                byteMessage = new byte[256];
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                outputStream.close();
                inputStream.close();

                if(!client.isClosed()){
                    client.close();
                }

                if (dungeon != null){
                    dungeon = null;
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(JsonObject jsonObject){
        try {
            System.out.println("Message I send " + jsonObject);
            outputStream.write(gson.toJson(jsonObject).getBytes());
            outputStream.flush();
        }
        catch (IOException e){
            System.out.println("Nothing to read or write");
        }
    }

    public void saveCharacterChanges(Character character){
        characterDAO.save(character);
    }

    public void saveItemChanges(Item item){
        itemDAO.save(item);
    }

}
