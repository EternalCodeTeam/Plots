package com.eternalcode.plots.region;

import java.util.UUID;

public interface RegionRepository {

    void saveRegion(Region region);

    void deleteRegion(Region region);

    Region getRegion(UUID regionUUID);

}
