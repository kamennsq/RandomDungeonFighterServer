package com.server.RandomDungeonFighter.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.server.RandomDungeonFighter.entity.Character;

public class ClientResponseParser {

    public Character getCharacterFromClient(JsonObject json){
        Character newCharacter = new Character();
        JsonArray array = json.get("characters").getAsJsonArray();

        newCharacter.setNickname(array.get(0).getAsJsonObject().get("nickname").getAsString());
        newCharacter.setClassName(array.get(0).getAsJsonObject().get("className").getAsString());
        newCharacter.setLevel(1);

        return newCharacter;
    }

}
