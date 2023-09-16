package com.eternalcode.plots.notgood.plot.old.plotblock.recoded;

import com.eternalcode.plots.notgood.configuration.implementation.MessageConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.feature.create.PlotCreation;
import com.eternalcode.plots.notgood.feature.limit.PlotsLimit;
import com.eternalcode.plots.good.notification.NotificationBroadcaster;
import com.eternalcode.plots.notgood.plot.old.region.RegionManager;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.plotblock.old.PlotBlockService;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import panda.std.Option;
import panda.utilities.text.Formatter;

public class PlotBlockController implements Listener {

    private final MessageConfiguration lang;
    private final PlotBlockService plotBlockService;
    private final PlotCreation plotCreation;
    private final PlotsLimit plotsLimit;
    private final PlotManager plotManager;
    private final NotificationBroadcaster notificationBroadcaster;

    public PlotBlockController(PluginConfiguration pluginConfiguration, MessageConfiguration messageConfiguration, PlotBlockService plotBlockService, UserManager userManager, PlotManager plotManager, RegionManager regionManager, PlotsLimit plotsLimit, NotificationBroadcaster notificationBroadcaster, Plugin plugin) {
        this.lang = messageConfiguration;
        this.plotBlockService = plotBlockService;
        this.plotsLimit = plotsLimit;
        this.notificationBroadcaster = notificationBroadcaster;
        this.plotCreation = new PlotCreation(pluginConfiguration, this.lang, userManager, plotManager, regionManager, plugin, notificationBroadcaster);
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (!this.plotBlockService.isPlotBlock(event.getItemInHand())) {
            return;
        }

        int startSize = this.plotBlockService.getPlotBlockStartSize(event.getItemInHand());

        if (!this.plotBlockService.canSetupPlot(location)) {
            event.setCancelled(true);
            this.notificationBroadcaster.sendMessage(player, this.lang.plotCreation.plotDetected);
            return;
        }

        if (!this.plotManager.isSafeRegion(location)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Działkę możesz stworzyć 250m od spawna");
            return;
        }

        Formatter formatter = new Formatter()
            .register("{LIMIT}", this.plotsLimit.getLimit(player) + "");

        if (this.plotsLimit.hasLimit(player)) {
            event.setCancelled(true);
            this.notificationBroadcaster.sendMessage(player, formatter.format(this.lang.plotCreation.hasLimit));
            return;
        }

        this.plotCreation.createPlot(player, location, startSize);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();
        Option<Region> regionOpt = this.plotManager.getPlotRegionByLocation(location);

        if (regionOpt.isEmpty()) {
            return;
        }

        Region region = regionOpt.get();

        if (location.getBlockX() == region.getCenter().x() &&
            location.getBlockY() == region.getCenter().y() &&
            location.getBlockZ() == region.getCenter().z()) {

            event.setCancelled(true);
        }

    }
}
