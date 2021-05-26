package com.server.RandomDungeonFighter.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BasicEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
