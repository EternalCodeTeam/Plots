package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.Iterator;

public class BlockExplodeListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public BlockExplodeListener(ProtectionConfiguration protectionConfig, PlotManager plotManager) {
        this.config = protectionConfig;
        this.plotManager = plotManager;
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!config.getGriefing().getExplosion().isProtection()) {
            return;
        }

        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if (!this.plotManager.getPlotRegionByLocation(block.getLocation()).isPresent()) {
                continue;
            }
            it.remove();
        }
    }
}
