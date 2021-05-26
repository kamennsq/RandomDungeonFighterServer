package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDAO {

    Card getCardByName(String name);

    Card getCardByID(long id);

}
