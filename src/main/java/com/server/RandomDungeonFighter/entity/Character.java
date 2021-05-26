package com.server.RandomDungeonFighter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Character extends BasicEntity{

    @NotNull
    @Column(name = "CLASS_NAME")
    private String className;

    @NotNull
    @Column(name = "LEVEL")
    private int level;

    @NotNull
    @Column(name = "NICKNAME")
    private String nickname;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ACCOUNT_ID")
    private Account parentAccount;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }
}
