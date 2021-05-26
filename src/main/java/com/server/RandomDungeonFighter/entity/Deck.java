package com.server.RandomDungeonFighter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck extends BasicEntity{

    @NotNull
    @Column(name = "DECK_CAPTURE")
    private String deckCapture;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CHARACTER_ID")
    private Character parentCharacter;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();

    public Character getParentCharacter() {
        return parentCharacter;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getDeckCapture() {
        return deckCapture;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setDeckCapture(String deckCapture) {
        this.deckCapture = deckCapture;
    }

    public void setParentCharacter(Character parentCharacter) {
        this.parentCharacter = parentCharacter;
    }
}
