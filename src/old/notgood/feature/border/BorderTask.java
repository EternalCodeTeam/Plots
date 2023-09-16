package com.eternalcode.plots.notgood.feature.border;

import com.eternalcode.plots.notgood.plot.old.PlotManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BorderTask implements Runnable {

    private final Set<UUID> showed = new HashSet<>();
    private final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 0.8F);
    private final PlotManager plotManager;
    private final Server server;
    private final BorderService borderService;

    public BorderTask(PlotManager plotManager, Server server, BorderService borderService) {
        this.plotManager = plotManager;
        this.server = server;
        this.borderService = borderService;
    }

    @Override
    public void run() {
        for (Player player : server.getOnlinePlayers()) {
            if (player == null || !player.isOnline()) {
                continue;
            }

            if (!hasShowed(player)) {
                continue;
            }

            plotManager.getPlots().stream()
                .filter(plot -> player.getLocation().getWorld().getName().equalsIgnoreCase(plot.getRegion().getCenter().getWorld()))
                .filter(plot -> borderService.getDistance(player, plot) <= 100)
                .forEach(plot -> {
                    Set<Location> locations = borderService.getBorder(plot);
                    locations.stream()
                        .filter(loc -> player.getLocation().distance(new Location(loc.getWorld(), loc.getX(), player.getLocation().getY(), loc.getZ())) < 100)
                        .forEach(loc -> player.spawnParticle(Particle.REDSTONE, new Location(loc.getWorld(), loc.getX(), player.getLocation().getBlockY(), loc.getZ()), 5, dustOptions));
                });
        }
    }

    public boolean hasShowed(Player player) {
        return showed.contains(player.getUniqueId());
    }

    public void hide(Player player) {
        showed.remove(player.getUniqueId());
    }

    public void show(Player player) {
        showed.add(player.getUniqueId());
    }
}