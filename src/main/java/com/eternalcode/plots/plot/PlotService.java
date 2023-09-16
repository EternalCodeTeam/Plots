package com.eternalcode.plots.plot;

import com.eternalcode.plots.region.Region;
import com.eternalcode.plots.region.RegionService;
import org.bukkit.Location;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlotService {

    private final RegionService regionService;
    private final PlotSettings settings;

    private final Set<Plot> plots = new HashSet<>();
    private final PlotRepository plotRepository;

    public PlotService(RegionService regionService, PlotSettings settings, PlotRepository plotRepository) {
        this.regionService = regionService;
        this.settings = settings;
        this.plotRepository = plotRepository;
    }

    public void createPlot(String name, int x, int z) {
        Instant createdAt = Instant.now();
        Instant expireAt = createdAt.plus(settings.getExpire());

        UUID regionId = this.regionService.createRegion(x, z);
        Plot plot = new Plot(regionId, name, createdAt, expireAt);

        plots.add(plot);
    }

    public Plot getPlotForLocation(Location location) {
        for (Plot plot : plots) {
            Region region = this.regionService.getRegion(plot.plotId());
            if (region != null && region.isInRegion(location)) {
                return plot;
            }
        }
        return null;
    }

}