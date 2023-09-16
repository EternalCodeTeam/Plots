package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public EntityExplodeListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.plotManager = plotManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!config.getGriefing().getExplosion().isProtection()) {
            return;
        }

        event.blockList().removeIf(block -> plotManager.getPlotRegionByLocation(block.getLocation()).isPresent());
    }
}
