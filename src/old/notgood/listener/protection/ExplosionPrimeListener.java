package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionPrimeListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public ExplosionPrimeListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.plotManager = plotManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (!config.getGriefing().getExplosion().isProtection()) {
            return;
        }

        for (Entity e : event.getEntity().getWorld().getNearbyEntities(
            event.getEntity().getLocation(), event.getRadius(), event.getRadius(), event.getRadius())) {
            if ((e instanceof Vehicle) || e instanceof Hanging) {
                if (plotManager.getPlotRegionByLocation(e.getLocation()).isPresent()) {
                    event.setRadius(0);
                    return;
                }
            }
        }
    }
}
