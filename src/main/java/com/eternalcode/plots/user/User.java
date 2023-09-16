package com.eternalcode.plots.user;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private String name;

    User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String name() {
        return this.name;
    }

    void updateName(String name) {
        this.name = name;
    }

}