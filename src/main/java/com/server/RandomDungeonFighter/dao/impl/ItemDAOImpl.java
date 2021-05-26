package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.ItemDAO;
import com.server.RandomDungeonFighter.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ItemDAOImpl implements ItemDAO {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    @Override
    public Item save(Item item) {
        return em.merge(item);
    }

    @Transactional
    @Override
    public Item getItemByID(long id) {
        return em.createQuery("select a from Item a where id = :id", Item.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public Item getItemByName(String name) {
        return em.createQuery("select a from Item a where item_name = :name", Item.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Transactional
    @Override
    public List<Item> getItemsByInventoryID(long id) {
        System.out.println(id);
        return em.createQuery("select a from Item a where parent_inventory_id = :id", Item.class)
                .setParameter("id", id)
                .getResultList();
    }
}
