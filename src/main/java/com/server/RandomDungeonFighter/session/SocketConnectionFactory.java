package com.server.RandomDungeonFighter.session;

import com.server.RandomDungeonFighter.dao.*;
import com.server.RandomDungeonFighter.entity.Inventory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class SocketConnectionFactory implements Runnable{

    private AccountDAO accountDAO;
    private CharacterDAO characterDAO;
    private DeckDAO deckDAO;
    private CardDAO cardDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private Socket client;

    @Inject
    public SocketConnectionFactory(AccountDAO accountDAO, CharacterDAO characterDAO, DeckDAO deckDAO, CardDAO cardDAO,
                                   InventoryDAO inventoryDAO, ItemDAO itemDAO){
        this.accountDAO = accountDAO;
        this.characterDAO = characterDAO;
        this.deckDAO = deckDAO;
        this.cardDAO = cardDAO;
        this.inventoryDAO = inventoryDAO;
        this.itemDAO = itemDAO;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            ServerSocket socket = new ServerSocket(5051);
            while (!socket.isClosed()){
                Socket client = socket.accept();
                SingleConnectionThread clientThread = new SingleConnectionThread(accountDAO, characterDAO, deckDAO, cardDAO,
                        inventoryDAO, itemDAO, client);
                Thread thread = new Thread(clientThread);
                thread.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
