package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.InventoryDAO;
import com.server.RandomDungeonFighter.entity.Inventory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class InventoryDAOImpl implements InventoryDAO {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Inventory save(Inventory inventory) {
        return em.merge(inventory);
    }

    @Transactional
    @Override
    public Inventory getInventoryByCharacterID(long id) {
        return em.createQuery("select a from Inventory a where parent_character_id = :id", Inventory.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
