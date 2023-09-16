package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import panda.std.Option;

public class EntityDamageByEntityListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;
    private final PlotManager plotManager;

    public EntityDamageByEntityListener(ProtectionManager protectionManager, ProtectionConfiguration config, PlotManager plotManager) {
        this.protectionManager = protectionManager;
        this.config = config;
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());

        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.PVP))) {
            return;

        }

        if (!(event.getDamager() instanceof Player damager) || !(event.getEntity() instanceof Player)) {
            return;
        }

        if (protectionManager.hasBypass(damager)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity2(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());

        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.FRIENDLY_MOBS))) {
            return;

        }

        if (
            !(event.getDamager() instanceof Player player) ||
                (event.getEntity() instanceof Player) ||
                (event.getEntity() instanceof Monster && event.getEntity().getCustomName() == null)) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }

        if (this.protectionManager.isAllowed(player, event.getEntity().getLocation().getBlock().getLocation())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity6(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());

        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.MONSTERS))) {
            return;

        }

        if (!(event.getDamager() instanceof Player player) ||
            !(event.getEntity() instanceof Monster) ||
            (event.getEntity().getCustomName() != null)) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }

        if (this.protectionManager.isAllowed(player, event.getEntity().getLocation().getBlock().getLocation())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity3(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());

        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.PVP))) {
            return;

        }

        if (!(event.getDamager() instanceof Projectile projectile) ||
            !(event.getEntity() instanceof Player) ||
            !(projectile.getShooter() instanceof Player player)) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity4(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());
        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.FRIENDLY_MOBS))) {
            return;

        }

        if (!(event.getDamager() instanceof Projectile projectile) ||
            (event.getEntity() instanceof Player) ||
            (event.getEntity() instanceof Monster && event.getEntity().getCustomName() == null) ||
            !(projectile.getShooter() instanceof Player player)) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }

        if (this.protectionManager.isAllowed(player, event.getEntity().getLocation().getBlock().getLocation())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity5(EntityDamageByEntityEvent event) {

        Option<Plot> plot = this.protectionManager.getPlot(event.getEntity().getLocation().getBlock().getLocation());
        if (plot.isPresent() && (!protectionManager.isProtection(plot.get(), FlagType.MONSTERS))) {
            return;

        }

        if (!(event.getDamager() instanceof Projectile projectile) ||
            !(event.getEntity() instanceof Monster) ||
            (event.getEntity().getCustomName() != null) ||
            !(projectile.getShooter() instanceof Player player)) {
            return;
        }

        if (protectionManager.hasBypass(player)) return;

        Option<Region> region = plotManager.getPlotRegionByLocation(event.getEntity().getLocation().getBlock().getLocation());
        if (!region.isPresent()) {
            return;
        }

        if (!plot.isPresent()) {
            return;
        }

        if (this.protectionManager.isAllowed(player, event.getEntity().getLocation().getBlock().getLocation())) {
            return;
        }
        event.setCancelled(true);
    }
}
