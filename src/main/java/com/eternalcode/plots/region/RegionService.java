package com.eternalcode.plots.region;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionService {

    private final Map<UUID, Region> regions = new HashMap<>();
    private final RegionSettings regionSettings;
    private final RegionRepository regionRepository;

    public RegionService(RegionSettings regionSettings, RegionRepository regionRepository) {
        this.regionSettings = regionSettings;
        this.regionRepository = regionRepository;
    }

    public UUID createRegion(int x, int z) {
        UUID regionUUID = UUID.randomUUID();
        Region region = new Region(regionUUID, regionSettings.startSize(), x, z);

        regions.put(regionUUID, region);
        return regionUUID;
    }

    public Region getRegion(UUID regionUUID) {
        return regions.values().stream().filter(region -> region.regionUUID().equals(regionUUID)).findFirst().orElse(null);
    }

    public void deleteRegion(UUID regionUUID) {
        Region region = getRegion(regionUUID);
        if (region != null) {
            regions.remove(region);
        }
    }

}