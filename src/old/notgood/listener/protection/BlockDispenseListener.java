package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import panda.std.Option;

public class BlockDispenseListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public BlockDispenseListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.plotManager = plotManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent event) {
        if (!config.getGriefing().getDispenser().isProtection()) {
            return;
        }

        Block dispenser = event.getBlock();

        Directional directional = (Directional) dispenser.getBlockData();
        Block target = dispenser.getRelative(directional.getFacing(), 1);

        Option<Region> targetRegionOpt = this.plotManager.getPlotRegionByLocation(target.getLocation());

        if (targetRegionOpt.isEmpty()) {
            return;
        }

        Region targetRegion = targetRegionOpt.get();


        Option<Region> dispenserRegionOpt = this.plotManager.getPlotRegionByLocation(dispenser.getLocation());

        if (dispenserRegionOpt.isEmpty()) {
            return;
        }

        Region dispenserRegion = dispenserRegionOpt.get();

        if (targetRegion == dispenserRegion) {
            return;
        }

        event.setCancelled(true);
    }

}
