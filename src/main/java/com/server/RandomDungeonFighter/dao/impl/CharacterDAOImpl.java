package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.CharacterDAO;
import com.server.RandomDungeonFighter.entity.Character;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Repository;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CharacterDAOImpl implements CharacterDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Character save(Character character) {
        return em.merge(character);
    }

    @Transactional
    @Override
    public List<Character> getCharactersByAccountLogin(String login) {
        return em.createQuery("select a from Character a where parent_account_id = (select b.id from Account b where login = :login)", Character.class)
                .setParameter("login", login)
                .getResultList();
    }

    @Transactional
    @Override
    public List<Character> getCharactersByAccountID(long id) {
        return em.createQuery("select a from Character a where parent_account_id = :id", Character.class)
                .setParameter("id", id)
                .getResultList();
    }
}
