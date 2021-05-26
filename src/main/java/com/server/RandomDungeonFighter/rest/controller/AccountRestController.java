package com.server.RandomDungeonFighter.rest.controller;

import com.server.RandomDungeonFighter.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "api/account")
public class AccountRestController {

    private AccountService accountService;

    @Inject
    public AccountRestController (AccountService accountService){
        this.accountService = accountService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createNewAccount (@RequestParam(name = "login") String login,
                                     @RequestParam(name = "password") String password)
    {
        accountService.createNewAccount(login, password);

        return ResponseEntity.ok().build();
    }
}
