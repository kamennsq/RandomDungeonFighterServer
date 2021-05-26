package com.server.RandomDungeonFighter.dao;

import com.server.RandomDungeonFighter.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDAO {

    Account save(Account account);

    Account getAccountByLogin(String login);

    boolean doesAccountExistByLogin(String login);
}
