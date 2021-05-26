package com.server.RandomDungeonFighter.service;

import com.server.RandomDungeonFighter.dao.AccountDAO;
import com.server.RandomDungeonFighter.entity.Account;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    @Inject
    public AccountService (AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public void createNewAccount(String login, String password){
        Account account = new Account();

        account.setLogin(login);
        account.setPassword(password);

        accountDAO.save(account);
    }
}
