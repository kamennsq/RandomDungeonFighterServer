package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.CardDAO;
import com.server.RandomDungeonFighter.entity.Card;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CardDAOImpl implements CardDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Card getCardByName(String name) {
        return em.createQuery("select a from Card a where card_name = :name", Card.class).setParameter("name", name).getSingleResult();
    }

    @Transactional
    @Override
    public Card getCardByID(long id) {
        return em.createQuery("select a from Card a where id = :id", Card.class).setParameter("id", id).getSingleResult();
    }
}
