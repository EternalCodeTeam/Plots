package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.position.PositionAdapter;
import com.eternalcode.plots.good.user.User;
import com.eternalcode.plots.good.user.UserManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import panda.std.Option;

import java.util.ArrayList;
import java.util.List;

public class EntityDamageListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;
    private final PlotManager plotManager;
    private final UserManager userManager;

    public EntityDamageListener(ProtectionManager protectionManager, ProtectionConfiguration config, PlotManager plotManager, UserManager userManager) {
        this.protectionManager = protectionManager;
        this.plotManager = plotManager;
        this.config = config;
        this.userManager = userManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!config.getGriefing().getFriendlyMobs().isProtection()) {
            return;
        }

        Entity entity = event.getEntity();

        if (entity instanceof Player || (entity instanceof Monster && entity.getCustomName() == null)) {
            return;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK ||
            event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ||
            event.getCause() == EntityDamageEvent.DamageCause.FALL ||
            event.getCause() == EntityDamageEvent.DamageCause.CRAMMING ||
            event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return;
        }

        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

        if (regionOpt.isEmpty()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageFire(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if (!(entity instanceof Player player)) {
            return;
        }

        if (event.getCause() != EntityDamageEvent.DamageCause.FIRE) {
            return;
        }

        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

        if (regionOpt.isEmpty()) {
            return;
        }

        Block block = player.getLocation().getBlock();

        for (Block nearbyBlock : getNearbyBlocks(PositionAdapter.convert(regionOpt.get().getCenter()), 1)) {
            if (block.getX() == nearbyBlock.getX() && block.getY() == nearbyBlock.getY() && block.getZ() == nearbyBlock.getZ()) {
                event.setCancelled(true);
                return;
            }
        }
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage3(EntityDamageEvent event) {
        if (!config.getGriefing().getMonsters().isProtection()) {
            return;
        }

        Entity entity = event.getEntity();

        if (!(entity instanceof Monster) || entity.getCustomName() != null) {
            return;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
            event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK ||
            event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE ||
            event.getCause() == EntityDamageEvent.DamageCause.FALL ||
            event.getCause() == EntityDamageEvent.DamageCause.CRAMMING ||
            event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return;
        }

        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

        if (regionOpt.isEmpty()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage2(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        Option<Plot> plotOpt = this.protectionManager.getPlot(entity.getLocation());

        if (plotOpt.isEmpty()) {
            return;
        }

        Plot plot = plotOpt.get();

        if (!protectionManager.isProtection(plot, FlagType.PVP)) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(entity.getLocation());

        if (regionOpt.isEmpty()) {
            return;
        }

        User user = this.userManager.findOrCreate(entity.getUniqueId(), entity.getName());

        if (plot.isMember(user)) {
            return;
        }

        event.setCancelled(true);
    }
}
