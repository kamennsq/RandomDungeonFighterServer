package com.server.RandomDungeonFighter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Inventory extends BasicEntity{

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CHARACTER_ID")
    private Character parentCharacter;


    public void setParentCharacter(Character parentCharacter) {
        this.parentCharacter = parentCharacter;
    }

    public Character getParentCharacter() {
        return parentCharacter;
    }
}
