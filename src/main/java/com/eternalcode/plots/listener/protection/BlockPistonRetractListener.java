package com.eternalcode.plots.listener.protection;

import com.eternalcode.plots.configuration.implementations.ProtectionConfiguration;
import com.eternalcode.plots.plot.PlotManager;
import com.eternalcode.plots.region.Region;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
import panda.std.Option;

public class BlockPistonRetractListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public BlockPistonRetractListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.config = config;
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!config.getGriefing().getPiston().isProtection()) {
            return;
        }

        Block piston = event.getBlock();

        Option<Region> pistonRegionOpt = this.plotManager.getRegion(piston.getLocation());

        for (Block block : event.getBlocks()) {
            Option<Region> blockRegion = plotManager.getRegion(block.getLocation());
            if (!blockRegion.isPresent()) {
                continue;
            }
            if (pistonRegionOpt.isPresent() && (blockRegion.get() == pistonRegionOpt.get())) {
                continue;
            }
            event.setCancelled(true);
            return;
        }
    }
}
