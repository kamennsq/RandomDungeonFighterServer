package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Character;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterDAO {

    Character save(Character character);

    List<Character> getCharactersByAccountLogin(String login);

    List<Character> getCharactersByAccountID(long id);
}
