package com.eternalcode.plots.notgood.feature.create;

import com.eternalcode.plots.notgood.configuration.implementation.MessageConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.feature.name.PlotChangeName;
import com.eternalcode.plots.good.notification.NotificationBroadcaster;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.notgood.plot.old.region.RegionManager;
import com.eternalcode.plots.good.user.User;
import com.eternalcode.plots.good.user.UserManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import panda.std.Option;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PlotCreation {

    private final PluginConfiguration pluginConfiguration;
    private final MessageConfiguration lang;
    private final UserManager userManager;
    private final PlotManager plotManager;
    private final RegionManager regionManager;
    private final Plugin plugin;
    private final NotificationBroadcaster notificationBroadcaster;

    public PlotCreation(PluginConfiguration pluginConfiguration, MessageConfiguration messageConfiguration, UserManager userManager, PlotManager plotManager, RegionManager regionManager, Plugin plugin, NotificationBroadcaster notificationBroadcaster) {
        this.pluginConfiguration = pluginConfiguration;
        this.lang = messageConfiguration;
        this.userManager = userManager;
        this.plotManager = plotManager;
        this.regionManager = regionManager;
        this.plugin = plugin;
        this.notificationBroadcaster = notificationBroadcaster;
    }

    public void createPlot(Player player, Location center, int startSize) {

        // get user
        User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName());

        // check if generated uuid exists
        UUID plotUUID;
        do {
            plotUUID = UUID.randomUUID();
        }
        while (this.plotManager.getPlot(plotUUID).isPresent());

        // get plot default name (player_name{nr})
        int nr = 0;
        do {
            nr++;
        }
        while (this.plotManager.getPlot(player.getName() + nr).isPresent());
        String plotName = player.getName() + nr;

        // fix start size (for one addon block in center)
        if (startSize % 2 == 0) {
            startSize++;
        }

        // get plot size and range
        int range = startSize / 2;

        // setup min, max plot locations
        Location min = new Location(center.getWorld(), center.getX() - range, center.getY(), center.getZ() - range);
        Location max = new Location(center.getWorld(), center.getX() + range, center.getY(), center.getZ() + range);

        // setup region
        Region region = this.regionManager.create(startSize, max, min, center);

        // setup expires instant
        Instant validity = Instant.now().plus(this.pluginConfiguration.validityDays, ChronoUnit.DAYS);

        // create plot
        Option<Plot> plotOpt = this.plotManager.create(
            plotUUID,
            plotName,
            user,
            region,
            Instant.now(),
            validity
        );

        if (plotOpt.isEmpty()) {
            throw new IllegalStateException("Plot cannot be created: Plot with this UUID already exists");
        }

        center.getBlock().setType(Material.CAMPFIRE);

        Plot plot = plotOpt.get();

        // create anvil (name change)
        new PlotChangeName(plot, this.lang, this.plotManager, this.plugin, this.notificationBroadcaster).sendGui(player);

        player.sendMessage(LegacyUtils.color(this.lang.plotCreation.plotCreated.replace("{NAME}", plot.getName())));
    }
}