package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.DeckDAO;
import com.server.RandomDungeonFighter.entity.Card;
import com.server.RandomDungeonFighter.entity.Deck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class DeckDAOImpl implements DeckDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Deck save(Deck deck) {
        return em.merge(deck);
    }

    @Transactional
    @Override
    public Deck getDeckByCharacterID(long id) {
        return em.createQuery("select a from Deck a where parent_character_id = :id", Deck.class).setParameter("id", id).getSingleResult();
    }
}
