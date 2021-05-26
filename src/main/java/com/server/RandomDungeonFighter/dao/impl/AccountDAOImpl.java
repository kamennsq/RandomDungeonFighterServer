package com.server.RandomDungeonFighter.dao.impl;

import com.server.RandomDungeonFighter.dao.AccountDAO;
import com.server.RandomDungeonFighter.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Account save(Account account) {
        return em.merge(account);
    }

    @Transactional
    @Override
    public Account getAccountByLogin(String login) {
        return em.createQuery("select a from Account a where login = :login", Account.class).setParameter("login", login).getSingleResult();
    }

    @Transactional
    @Override
    public boolean doesAccountExistByLogin(String login) {
        long accountCounter = em.createQuery("select count(a) from Account a where login = :login", Long.class)
                .setParameter("login", login)
                .getSingleResult();
        return accountCounter > 0;
    }
}
