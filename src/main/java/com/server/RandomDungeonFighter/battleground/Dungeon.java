package com.server.RandomDungeonFighter.battleground;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.RandomDungeonFighter.entity.Character;
import com.server.RandomDungeonFighter.entity.Deck;
import com.server.RandomDungeonFighter.entity.Inventory;
import com.server.RandomDungeonFighter.entity.Item;
import com.server.RandomDungeonFighter.parser.ServerResponseParser;
import com.server.RandomDungeonFighter.session.SingleConnectionThread;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class Dungeon {

    private final SingleConnectionThread client;
    private int earnedMoney;
    private int earnedMagicPowder;
    private int maxHealthPoints;
    private int currentHealthPoints;
    private int currentCell;
    private int cellsToGo;
    private final Inventory inventory;
    private final Deck deck;
    private final HashMap<Integer, Integer> cellMap = new HashMap<>();
    private final Random dice = new Random();
    private final Character character;
    private final ServerResponseParser parser;
    private boolean moveDone;
    private boolean isFightingMob;
    private boolean isFightingBoss;
    private final Timer timer = new Timer();
    private TimerTask task;
    private List<Item> items;

    public Dungeon(SingleConnectionThread client, Deck deck, Character character, Inventory inventory,
                   List<Item> items, ServerResponseParser parser){
        this.client = client;
        this.deck = deck;
        this.character = character;
        this.parser = parser;
        this.items = items;
        this.inventory = inventory;
        createMap();
        earnedMagicPowder = 0;
        earnedMoney = 0;
        maxHealthPoints = 100;
        currentHealthPoints = maxHealthPoints;
        currentCell = 0;
        cellsToGo = 0;
        sendDungeon();
        moveDone = false;
        isFightingMob = false;
        isFightingBoss = false;
    }

    private void createMap(){
        for (int i = 1; i < 29; i++){
            cellMap.put(i, dice.nextInt(5));
        }
        cellMap.put(28, 5);
    }

    public void throwDice(){
        if (!isFightingMob && !isFightingBoss){
            cellsToGo = dice.nextInt(6) + 1;
            client.sendToClient(parser.getJsonObjectWithThrownDice(cellsToGo));
            moveDone = false;
        }
    }

    public void sendDungeon(){
        client.sendToClient(parser.getJsonObjectWithDungeon(earnedMoney, earnedMagicPowder, maxHealthPoints, currentHealthPoints,
                currentCell, 0, inventory, items, cellMap));
    }

    public void moveToNewCell(){
        if (!moveDone && !isFightingMob && !isFightingBoss) {
            currentCell += cellsToGo;
            if (currentCell > 28) currentCell = 28;
            switch (cellMap.get(currentCell)) {
                case 0:
                    applyBuff();
                    break;
                case 1:
                    applyDebuff();
                    break;
                case 2:
                    increaseMagicPowder();
                    break;
                case 3:
                    increaseCoins();
                    break;
                case 4:
                    fightMob();
                    break;
                case 5:
                    fightBoss();
                    break;
            }
            moveDone = true;
        }
    }

    private void increaseCoins(){
        earnedMoney += dice.nextInt(20) + 10;
        client.sendToClient(parser.getJsonObjectWithEarnedMoney(earnedMoney));
    }

    private void increaseMagicPowder(){
        earnedMagicPowder += dice.nextInt(50) + 50;
        client.sendToClient(parser.getJsonObjectWithEarnedMagicPowder(earnedMagicPowder));
    }

    private void applyBuff(){

    }

    private void applyDebuff(){

    }

    private void fightMob(){
        client.sendToClient(parser.getJsonObjectWithMobParameters(100, 0));
        isFightingMob = true;
        task = new TimerTask() {
            @Override
            public void run() {
                endTurn();
            }
        };
        timerExecution();
    }

    private void fightBoss(){
        client.sendToClient(parser.getJsonObjectWithMobParameters(1000, 0));
        isFightingMob = true;
        task = new TimerTask() {
            @Override
            public void run() {
                endTurn();
            }
        };
        timerExecution();
    }

    private void timerExecution(){
        //timer.schedule(task, 120000);
        timer.schedule(task, 10000);
    }

    private void endTurn(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("capture", "endTurn");
        client.sendToClient(jsonObject);
    }

    public void finishBattle(){
        if (isFightingMob){
            task.cancel();
            timer.purge();
            isFightingMob = false;
            sendDungeon();
        }
        if (isFightingBoss){
            task.cancel();
            timer.purge();
            isFightingBoss = false;
            sendDungeon();
        }
    }

    public void finishDungeon(){
        items.get(0).setAmount(items.get(0).getAmount() + earnedMoney);
        client.saveItemChanges(items.get(0));
        items.get(1).setAmount(items.get(1).getAmount() + earnedMagicPowder);
        client.saveItemChanges(items.get(1));
    }
}
