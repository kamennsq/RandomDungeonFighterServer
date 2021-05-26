package com.server.RandomDungeonFighter.service;

import com.server.RandomDungeonFighter.dao.*;
import com.server.RandomDungeonFighter.entity.*;
import com.server.RandomDungeonFighter.entity.Character;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    private final CharacterDAO characterDAO;
    private final AccountDAO accountDAO;
    private final DeckDAO deckDAO;
    private final CardDAO cardDAO;
    private final InventoryDAO inventoryDAO;
    private final ItemDAO itemDAO;

    @Inject
    public CharacterService (CharacterDAO characterDAO, AccountDAO accountDAO, DeckDAO deckDAO, CardDAO cardDAO,
                             InventoryDAO inventoryDAO, ItemDAO itemDAO){
        this.characterDAO = characterDAO;
        this.accountDAO = accountDAO;
        this.deckDAO = deckDAO;
        this.cardDAO = cardDAO;
        this.inventoryDAO = inventoryDAO;
        this.itemDAO = itemDAO;
    }

    public void createNewAccount(String className, int level, String nickname, String accountName){
        Character character = new Character();

        character.setClassName(className);
        character.setLevel(level);
        character.setNickname(nickname);
        character.setParentAccount(accountDAO.getAccountByLogin(accountName));

        character = characterDAO.save(character);

        Card card_1 = cardDAO.getCardByName("Attack_basic");
        Card card_2 = cardDAO.getCardByName("Defense_basic");

        List<Card> cards = new ArrayList<>();
        cards.add(card_1);
        cards.add(card_2);
        Deck deck = new Deck();
        deck.setDeckCapture("DEFAULT");
        deck.setParentCharacter(character);
        deck.setCards(cards);
        deckDAO.save(deck);

        Inventory inventory = new Inventory();
        inventory.setParentCharacter(character);
        inventory = inventoryDAO.save(inventory);

        Item money = new Item();
        money.setAmount(0);
        money.setItemName("Money");
        money.setParentInventory(inventory);

        Item magicPowder = new Item();
        magicPowder.setAmount(0);
        magicPowder.setItemName("Magic powder");
        magicPowder.setParentInventory(inventory);

        itemDAO.save(money);
        itemDAO.save(magicPowder);
    }
}
