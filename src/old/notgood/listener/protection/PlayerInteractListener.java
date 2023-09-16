package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import panda.std.Option;

public class PlayerInteractListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public PlayerInteractListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!config.getGriefing().getFarm().isProtection()) {
            return;
        }

        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

        if (event.getHand() != null) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        Player player = event.getPlayer();

        if (this.protectionManager.hasBypass(event.getPlayer())) return;

        if (this.protectionManager.isAllowed(player, clickedBlock.getLocation())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract2(PlayerInteractEvent event) {

        if (this.protectionManager.hasBypass(event.getPlayer())) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND && event.getHand() != EquipmentSlot.OFF_HAND) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        Option<Plot> plotOpt = this.protectionManager.getPlot(block.getLocation());

        if (plotOpt.isEmpty()) {
            return;
        }

        Plot plot = plotOpt.get();

        if (event.getPlayer().isSneaking() && event.getItem() != null && !event.getItem().getType().isAir()) {
            return;
        }

        FlagType flagType;

        // buttons, levers, pressure plates, doors itp.
        if (Tag.BUTTONS.isTagged(block.getType()) ||
            Tag.PRESSURE_PLATES.isTagged(block.getType()) ||
            Tag.DOORS.isTagged(block.getType()) ||
            Tag.BUTTONS.isTagged(block.getType()) ||
            block.getType() == Material.LEVER ||
            block.getBlockData() instanceof Openable) {
            flagType = FlagType.USE;
        }

        // skrzynie itp.
        else if (block.getState() instanceof InventoryHolder) {
            flagType = FlagType.CHEST_ACCESS;

        } else {
            return;
        }

        if (!this.protectionManager.isProtection(plot, flagType)) {
            return;
        }

        if (this.protectionManager.isAllowed(player, block.getLocation())) {
            return;
        }

        event.setCancelled(true);
    }
}
