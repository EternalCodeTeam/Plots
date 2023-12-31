package com.eternalcode.plots.listener.protection;

import com.eternalcode.plots.configuration.implementations.ProtectionConfiguration;
import com.eternalcode.plots.plot.PlotManager;
import com.eternalcode.plots.plot.protection.ProtectionManager;
import com.eternalcode.plots.region.Region;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import panda.std.Option;

public class PotionSplashListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public PotionSplashListener(ProtectionManager protectionManager, ProtectionConfiguration config, PlotManager plotManager) {
        this.protectionManager = protectionManager;
        this.config = config;
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent event) {
        if (!config.getGriefing().getNegativeEffects().isProtection()) {
            return;
        }

        if (!this.protectionManager.isBadPotion(event.getPotion().getEffects())) {
            return;
        }

        for (LivingEntity entity : event.getAffectedEntities()) {
            if (entity.getType() != EntityType.PLAYER) {
                return;
            }

            Option<Region> regionOpt = this.plotManager.getRegion(entity.getLocation());

            if (regionOpt.isEmpty()) {
                return;
            }

            event.setIntensity(entity, 0);
        }
    }
}
