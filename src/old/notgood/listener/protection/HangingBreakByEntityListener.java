package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import panda.std.Option;

public class HangingBreakByEntityListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;
    private final PlotManager plotManager;
    private final UserManager userManager;

    public HangingBreakByEntityListener(ProtectionManager protectionManager, ProtectionConfiguration config, PlotManager plotManager, UserManager userManager) {
        this.protectionManager = protectionManager;
        this.config = config;
        this.plotManager = plotManager;
        this.userManager = userManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation());
        if (plot.isPresent()) {
            if (!protectionManager.isProtection(plot.get(), FlagType.BLOCK_BREAK)) {
                return;
            }
        }

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation());
        if (!region.isPresent()) {
            return;
        }
        if (event.getRemover() instanceof Player player) {

            if (this.protectionManager.hasBypass(player)) return;

            if (plot.isPresent()) {
                if (plot.get().isMember(userManager.findOrCreate(event.getRemover().getUniqueId(), event.getRemover().getName()))) {
                    return;
                }
            }
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingBreakByEntity2(HangingBreakByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation());
        if (plot.isPresent()) {
            if (!protectionManager.isProtection(plot.get(), FlagType.BLOCK_BREAK)) {
                return;
            }
        }

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }
        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation());
        if (!region.isPresent()) {
            return;
        }
        event.setCancelled(true);
    }
}
