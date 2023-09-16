package com.eternalcode.plots.notgood.plot.old.region;

import com.eternalcode.plots.good.position.Position;
import com.eternalcode.plots.good.position.PositionAdapter;
import org.bukkit.Location;

import java.util.Objects;
import java.util.UUID;

public class Region {

    private final UUID regionUUID;
    private int size;
    private int range;
    private int extendLevel;
    private Position posMax;
    private Position posMin;

    private Position center;

    public Region(UUID regionUUID, int size, int extendLevel, Location posMax, Location posMin, Location center) {
        this.regionUUID = regionUUID;
        this.posMax = PositionAdapter.convert(posMax);
        this.posMin = PositionAdapter.convert(posMin);
        this.center = PositionAdapter.convert(center);
        this.size = size;
        this.extendLevel = extendLevel;
        this.range = size / 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return this.regionUUID.equals(region.regionUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.regionUUID);
    }

    public UUID getRegionUUID() {
        return this.regionUUID;
    }

    public int getSize() {
        return this.size;
    }

    void setSize(int size) {
        this.size = size;
        this.range = size / 2;
        this.posMin = new Position(this.center.x() - this.range, this.center.y(), this.center.z() - this.range, 0.0f, 0.0f, this.center.world());
        this.posMax = new Position(this.center.x() + this.range, this.center.y(), this.center.z() + this.range, 0.0f, 0.0f, this.center.world());
    }

    public int getRange() {
        return this.range;
    }

    void setRange(int range) {
        this.range = range;
    }

    public int getExtendLevel() {
        return this.extendLevel;
    }

    void setExtendLevel(int level) {
        this.extendLevel = level;
    }

    public Position getPosMax() {
        return this.posMax;
    }

    void setPosMax(Location posMax) {
        this.posMax = PositionAdapter.convert(posMax);
    }

    public Position getPosMin() {
        return this.posMin;
    }

    void setPosMin(Location posMin) {
        this.posMin = PositionAdapter.convert(posMin);
    }

    public Position getCenter() {
        return this.center;
    }

    void setCenter(Location center) {
        this.center = PositionAdapter.convert(center);
    }
}
