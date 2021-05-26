package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Deck;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckDAO {

    Deck save(Deck deck);

    Deck getDeckByCharacterID(long id);
}
