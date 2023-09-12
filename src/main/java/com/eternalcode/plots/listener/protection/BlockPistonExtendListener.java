package com.eternalcode.plots.listener.protection;

import com.eternalcode.plots.configuration.implementations.ProtectionConfiguration;
import com.eternalcode.plots.plot.PlotManager;
import com.eternalcode.plots.region.Region;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import panda.std.Option;

import java.util.ArrayList;
import java.util.List;

public class BlockPistonExtendListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public BlockPistonExtendListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.plotManager = plotManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (!config.getGriefing().getPiston().isProtection()) {
            return;
        }

        Block piston = event.getBlock();
        Option<Region> pistonRegionOpt = this.plotManager.getRegion(piston.getLocation());

        List<Block> targets = new ArrayList<>(event.getBlocks());

        if (!targets.isEmpty()) {
            targets.add(targets.get(targets.size() - 1).getRelative(event.getDirection(), 1));
        }

        for (Block block : targets) {
            Option<Region> blockRegionOpt = this.plotManager.getRegion(block.getLocation());

            if (blockRegionOpt.isEmpty()) {
                continue;
            }

            if (pistonRegionOpt.isPresent() && (blockRegionOpt.get() == pistonRegionOpt.get())) {
                    continue;
            }

            event.setCancelled(true);
            return;
        }
    }
}
