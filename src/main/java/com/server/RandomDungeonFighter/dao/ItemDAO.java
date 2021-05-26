package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO {

    Item save(Item item);

    Item getItemByID(long id);

    Item getItemByName(String name);

    List<Item> getItemsByInventoryID(long id);
}
