package com.server.RandomDungeonFighter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Card extends BasicEntity{

    @NotNull
    @Column(name = "CARD_TYPE")
    private String cardType;

    @NotNull
    @Column(name = "CARD_NAME")
    private String cardName;

    @NotNull
    @Column(name = "CARD_LEVEL")
    private int cardLevel;

    @NotNull
    @Column(name = "CARD_DAMAGE")
    private int cardDamage;

    @NotNull
    @Column(name = "CARD_DEFENSE")
    private int cardDefense;

    public int getCardLevel() {
        return cardLevel;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardDamage() {
        return cardDamage;
    }

    public int getCardDefense() {
        return cardDefense;
    }

    public void setCardDamage(int cardDamage) {
        this.cardDamage = cardDamage;
    }

    public void setCardDefense(int cardDefense) {
        this.cardDefense = cardDefense;
    }
}
