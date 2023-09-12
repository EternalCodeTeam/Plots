package com.eternalcode.plots.database.warp;

import com.eternalcode.plots.database.persister.LocationPersister;
import com.eternalcode.plots.position.PositionAdapter;
import com.eternalcode.plots.region.Region;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;

@DatabaseTable(tableName = "ep-regions")
public class RegionWarp {

    @Getter
    @DatabaseField(columnName = "region_uuid", id = true)
    private UUID uuid;

    @Getter
    @DatabaseField(columnName = "region_size")
    private int size;

    @Getter
    @DatabaseField(columnName = "extend_level")
    private int extendLevel;

    @Getter
    @DatabaseField(columnName = "region_center", persisterClass = LocationPersister.class)
    private Location center;

    @Getter
    @DatabaseField(columnName = "region_max", persisterClass = LocationPersister.class)
    private Location posMax;

    @Getter
    @DatabaseField(columnName = "region_min", persisterClass = LocationPersister.class)
    private Location posMin;


    private RegionWarp() {

    }

    private RegionWarp(UUID regionUUID, int size, int extendLevel, Location posMax, Location posMin, Location center) {
        this.uuid = regionUUID;
        this.size = size;
        this.extendLevel = extendLevel;
        this.posMax = posMax;
        this.posMin = posMin;
        this.center = center;
    }

    public static RegionWarp from(Region region) {
        return new RegionWarp(region.getRegionUUID(), region.getSize(), region.getExtendLevel(), PositionAdapter.convert(region.getPosMax()), PositionAdapter.convert(region.getPosMin()), PositionAdapter.convert(region.getCenter()));
    }

}