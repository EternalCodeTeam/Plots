package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import panda.std.Option;

public class VehicleDamageListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public VehicleDamageListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleDamage(VehicleDamageEvent event) {

        Entity vehicle = event.getVehicle();

        Option<Plot> plotOpt = this.protectionManager.getPlot(vehicle.getLocation());

        if (plotOpt.isEmpty()) {
            return;
        }

        Plot plot = plotOpt.get();

        if (!protectionManager.isProtection(plot, FlagType.VEHICLE_DESTROY)) {
            return;
        }

        if (event.getAttacker() instanceof Player player) {

            if (this.protectionManager.hasBypass(player)) return;
            if (this.protectionManager.isAllowed(player, vehicle.getLocation())) {
                return;
            }
        }

        event.setCancelled(true);
    }
}
