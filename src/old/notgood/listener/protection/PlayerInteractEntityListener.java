package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import panda.std.Option;

public class PlayerInteractEntityListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public PlayerInteractEntityListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    // NPC (klikanie w entity)
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

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
