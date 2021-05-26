package com.server.RandomDungeonFighter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Item extends BasicEntity{

    @NotNull
    @Column(name = "ITEM_NAME")
    private String itemName;

    @NotNull
    @Column(name = "ITEM_AMOUNT")
    private int amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_INVENTORY_ID")
    private Inventory parentInventory;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Inventory getParentInventory() {
        return parentInventory;
    }

    public void setParentInventory(Inventory parentInventory) {
        this.parentInventory = parentInventory;
    }
}
