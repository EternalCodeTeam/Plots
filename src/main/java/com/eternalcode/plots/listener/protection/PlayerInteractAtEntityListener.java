package com.eternalcode.plots.listener.protection;

import com.eternalcode.plots.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.plot.old.protection.FlagType;
import com.eternalcode.plots.plot.old.protection.ProtectionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import panda.std.Option;

public class PlayerInteractAtEntityListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public PlayerInteractAtEntityListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {

        if (this.protectionManager.hasBypass(event.getPlayer())) return;

        Option<Plot> plot = this.protectionManager.getPlot(event.getRightClicked().getLocation());

        if (plot.isPresent()) {
            if (!protectionManager.isProtection(plot.get(), FlagType.CLICKED_ENTITIES)) {
                return;
            }
        }

        if (this.protectionManager.isAllowed(event.getPlayer(), event.getRightClicked().getLocation())) {
            return;
        }

        event.setCancelled(true);
    }
}
