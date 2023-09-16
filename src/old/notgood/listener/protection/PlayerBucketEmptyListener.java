package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBucketEmptyListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public PlayerBucketEmptyListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!config.getGriefing().getWaterLavaFlow().isProtection()) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (this.protectionManager.hasBypass(player)) return;

        this.protectionManager.getPlaced().put(block, player.getUniqueId());

        if (this.protectionManager.isAllowed(player, block.getLocation())) {
            return;
        }

        event.setCancelled(true);
    }
}
