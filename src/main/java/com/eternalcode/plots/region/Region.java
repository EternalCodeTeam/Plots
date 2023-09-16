package com.eternalcode.plots.region;

import org.bukkit.Location;

import java.util.UUID;

public record Region(
    UUID regionUUID,
    int size,
    int x,
    int z
) {

    public Region withSize(int size) {
        return new Region(this.regionUUID, size, this.x, this.z);
    }

    public Region withX(int x) {
        return new Region(this.regionUUID, this.size, x, this.z);
    }

    public Region withZ(int z) {
        return new Region(this.regionUUID, this.size, this.x, z);
    }

    public boolean isInRegion(Location location) {
        int coordinateX = Math.abs(location.getBlockX() - this.x);
        int coordinateZ = Math.abs(location.getBlockZ() - this.z);

        return coordinateX <= this.size && coordinateZ <= this.size;
    }

}
