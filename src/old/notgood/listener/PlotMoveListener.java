package com.eternalcode.plots.notgood.listener;

import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.old.region.Region;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import panda.std.Option;

public class PlotMoveListener implements Listener {

    private final PlotManager plotManager;

    public PlotMoveListener(PlotManager plotManager) {
        this.plotManager = plotManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {

        Location from = event.getFrom().getBlock().getLocation();

        if (event.getTo() == null) {
            return;
        }
        Location to = event.getTo().getBlock().getLocation();

        Option<Region> optRegFrom = this.plotManager.getPlotRegionByLocation(from);
        Option<Region> optRegTo = this.plotManager.getPlotRegionByLocation(to);

        // nie wchodzi i nie wychodzi
        if (optRegTo.isEmpty() && optRegFrom.isEmpty()) {
            return;
        }

        // chodzi po dzialce
        if (optRegTo.isPresent() && optRegFrom.isPresent()) {
            return;
        }

        // wchodzi na dzialke
        if (optRegTo.isPresent() && optRegFrom.isEmpty()) {

            Region region = optRegTo.get();
            Plot plot = this.plotManager.getPlot(region);

            event.getPlayer().sendTitle(" ", "§aWchodzisz na teren §2" + plot.getName(), 5, 20, 5);
            return;
        }

        // wychodzi z dzialki
        if (optRegTo.isEmpty() && optRegFrom.isPresent()) {

            Region region = optRegFrom.get();
            Plot plot = this.plotManager.getPlot(region);

            event.getPlayer().sendTitle(" ", "§cOpuszczasz teren §4" + plot.getName(), 5, 20, 5);
        }
    }
}
