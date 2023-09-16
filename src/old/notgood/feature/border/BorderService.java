package com.eternalcode.plots.notgood.feature.border;

import com.eternalcode.plots.notgood.plot.old.region.Region;
import com.eternalcode.plots.good.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BorderService {

    public Set<Location> getBorder(Plot plot) {
        Set<Location> locations = new HashSet<>();

        Region region = plot.getRegion();

        Location min = PositionAdapter.convert(region.getPosMin()).add(0, 0, 0);
        Location max = PositionAdapter.convert(region.getPosMax()).add(1, 0, 1);

        for (double i = 0; i <= (region.getSize() + 1); i++) {
            Location loc = new Location(min.getWorld(), min.getX() + i, min.getY(), min.getZ());
            Location loc2 = new Location(min.getWorld(), min.getX(), min.getY(), min.getZ() + i);
            locations.add(loc);
            locations.add(loc2);
        }

        for (double i = 0; i <= (region.getSize() + 1); i++) {
            Location loc = new Location(max.getWorld(), max.getX() - i, max.getY(), max.getZ());
            Location loc2 = new Location(max.getWorld(), max.getX(), max.getY(), max.getZ() - i);
            locations.add(loc);
            locations.add(loc2);
        }

        locations.add(min);
        locations.add(max);

        return locations;
    }

    public double getDistance(Player player, Plot plot) {
        Location closest = PositionAdapter.convert(plot.getRegion().getCenter());

        for (Location location : getBorder(plot)) {
            location = new Location(location.getWorld(), location.getX(), player.getLocation().getY(), location.getZ());

            if (player.getLocation().distance(location) < player.getLocation().distance(closest)) {
                closest = location;
            }
        }

        return player.getLocation().distance(closest);
    }
}