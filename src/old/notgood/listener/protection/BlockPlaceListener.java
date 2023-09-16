package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import panda.std.Option;

public class BlockPlaceListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public BlockPlaceListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (protectionManager.hasBypass(event.getPlayer())) return;

        Option<Plot> plotOpt = this.protectionManager.getPlot(block.getLocation());

        if (plotOpt.isEmpty()) {
            return;
        }

        Plot plot = plotOpt.get();

        if (!protectionManager.isProtection(plot, FlagType.BLOCK_PLACE)) {
            return;
        }

        if (this.protectionManager.isAllowed(player, block.getLocation())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingPlace(HangingPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player == null) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Plot> plotOpt = this.protectionManager.getPlot(block.getLocation());

        if (plotOpt.isEmpty()) {
            return;
        }

        Plot plot = plotOpt.get();

        if (!protectionManager.isProtection(plot, FlagType.BLOCK_PLACE)) {
            return;
        }

        if (this.protectionManager.isAllowed(player, block.getLocation())) {
            return;
        }

        event.setCancelled(true);
    }
}
