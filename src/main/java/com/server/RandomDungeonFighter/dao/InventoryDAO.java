package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Deck;
import com.server.RandomDungeonFighter.entity.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDAO {

    Inventory save(Inventory inventory);

    Inventory getInventoryByCharacterID(long id);
}
