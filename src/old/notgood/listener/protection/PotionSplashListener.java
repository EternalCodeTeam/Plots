package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.util.PotionUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import panda.std.Option;

public class PotionSplashListener implements Listener {

    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public PotionSplashListener(ProtectionConfiguration config, PlotManager plotManager) {
        this.config = config;
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent event) {
        if (!config.getGriefing().getNegativeEffects().isProtection()) {
            return;
        }

        if (PotionUtil.isBadPotion(event.getPotion().getEffects())) {
            return;
        }

        for (LivingEntity entity : event.getAffectedEntities()) {
            if (entity.getType() != EntityType.PLAYER) {
                return;
            }

            Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

            if (regionOpt.isEmpty()) {
                return;
            }

            event.setIntensity(entity, 0);
        }
    }
}
