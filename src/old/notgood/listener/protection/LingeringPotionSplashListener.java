package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.util.PotionUtil;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import panda.std.Option;

public class LingeringPotionSplashListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public LingeringPotionSplashListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.config = config;
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
        if (!config.getGriefing().getNegativeEffects().isProtection()) {
            return;
        }

        ThrownPotion entity = event.getEntity();

        if (PotionUtil.isBadPotion(entity.getEffects())) {
            return;
        }

        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

        if (regionOpt.isEmpty()) {
            return;
        }

        event.setCancelled(true);
    }
}
