package com.server.RandomDungeonFighter.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.RandomDungeonFighter.dao.CardDAO;
import com.server.RandomDungeonFighter.dao.DeckDAO;
import com.server.RandomDungeonFighter.entity.*;
import com.server.RandomDungeonFighter.entity.Character;

import java.util.HashMap;
import java.util.List;

public class ServerResponseParser {

    public JsonObject getJsonObjectWithCharacters(List<Character> list, DeckDAO charDeck){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "characterInfo");
        JsonArray objects = new JsonArray();
        JsonArray deck = new JsonArray();
        for (int i = 0; i < list.size(); i++){
            JsonObject tempJsonObject = new JsonObject();
            List<Card> charCards = charDeck.getDeckByCharacterID(list.get(i).getId()).getCards();
            for (int k = 0; k < charCards.size(); k++){
                JsonObject tempCard = new JsonObject();
                tempCard.addProperty("cardType", charCards.get(k).getCardType());
                tempCard.addProperty("cardName", charCards.get(k).getCardName());
                tempCard.addProperty("cardLevel", charCards.get(k).getCardLevel());
                tempCard.addProperty("cardDamage", charCards.get(k).getCardDamage());
                tempCard.addProperty("cardDefense", charCards.get(k).getCardDefense());
                deck.add(tempCard);
            }
            tempJsonObject.addProperty("nickname", list.get(i).getNickname());
            tempJsonObject.addProperty("className", list.get(i).getClassName());
            tempJsonObject.addProperty("level", list.get(i).getLevel());
            tempJsonObject.add("deck", deck);
            objects.add(tempJsonObject);
        }
        jsonObject.add("characters", objects);
        return jsonObject;
    }

    public JsonObject getJsonObjectWithDungeon(int earnedMoney, int earnedMagicPowder, int maxHealthPoints,
                                               int currentHealthPoints, int currentCell, int cellsToGo,
                                               Inventory inventory, List<Item> items, HashMap<Integer, Integer> cellMap){
        JsonObject jsonObject = new JsonObject();
        JsonArray map = new JsonArray();
        jsonObject.addProperty("capture", "dungeonParameters");
        jsonObject.addProperty("earnedMoney", earnedMoney);
        jsonObject.addProperty("earnedMagicPowder", earnedMagicPowder);
        jsonObject.addProperty("maxHealthPoints", maxHealthPoints);
        jsonObject.addProperty("currentHealthPoints", currentHealthPoints);
        jsonObject.addProperty("currentCell", currentCell);
        jsonObject.addProperty("cellsToGo", cellsToGo);
        JsonArray charInventory = new JsonArray();
        for (int k = 0; k < items.size(); k++){
            JsonObject tempItem = new JsonObject();
            tempItem.addProperty("itemName", items.get(k).getItemName());
            tempItem.addProperty("amount", items.get(k).getAmount());
            charInventory.add(tempItem);
        }
        for (int i = 1; i < cellMap.size() + 1; i++){
            JsonObject tempObject = new JsonObject();
            tempObject.addProperty("cellID", i);
            tempObject.addProperty("cellType", cellMap.get(i));
            map.add(tempObject);
        }
        jsonObject.add("map", map);
        return  jsonObject;
    }

    public JsonObject getJsonObjectWithThrownDice(int cellsToGo){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "newCellsToGo");
        jsonObject.addProperty("cellsToGo", cellsToGo);
        return jsonObject;
    }

    public JsonObject getJsonObjectWithEarnedMoney(int earnedMoney){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "newEarnedMoney");
        jsonObject.addProperty("earnedMoney", earnedMoney);
        return jsonObject;
    }

    public JsonObject getJsonObjectWithEarnedMagicPowder(int earnedMagicPowder){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "newEarnedMagicPowder");
        jsonObject.addProperty("earnedMagicPowder", earnedMagicPowder);
        return jsonObject;
    }

    public JsonObject getJsonObjectWithMobParameters(int mobHealth, int mobDeck){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "mobParameters");
        jsonObject.addProperty("health", mobHealth);
        jsonObject.addProperty("deck", mobDeck);
        return jsonObject;
    }
}
