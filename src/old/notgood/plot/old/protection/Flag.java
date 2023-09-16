package com.eternalcode.plots.notgood.plot.old.protection;

import java.util.UUID;

public class Flag {

    private final UUID uuid;
    private final FlagType flagType;
    private boolean status;

    public Flag(UUID uuid, FlagType flagType, boolean status) {
        this.uuid = uuid;
        this.flagType = flagType;
        this.status = status;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public FlagType getFlagType() {
        return this.flagType;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
