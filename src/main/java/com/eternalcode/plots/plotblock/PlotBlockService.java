package com.eternalcode.plots.plotblock;

import com.eternalcode.plots.configuration.implementations.BlocksConfiguration;
import com.eternalcode.plots.configuration.implementations.PluginConfiguration;
import com.eternalcode.plots.plot.PlotManager;
import com.eternalcode.plots.position.Position;
import com.eternalcode.plots.position.PositionAdapter;
import com.eternalcode.plots.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlotBlockService {

    private final BlocksConfiguration blocksConfiguration;
    private final PlotManager plotManager;
    private final PluginConfiguration pluginConfiguration;
    private final PlotBlockMatcher plotBlockMatcher;

    private HashMap<BlocksConfiguration.PlotBlock, Integer> plotBlocks = new HashMap<>();

    public PlotBlockService(BlocksConfiguration blocksConfiguration, PluginConfiguration pluginConfiguration,
                            PlotManager plotManager, PlotBlockMatcher plotBlockMatcher) {
        this.blocksConfiguration = blocksConfiguration;
        this.pluginConfiguration = pluginConfiguration;
        this.plotManager = plotManager;
        this.plotBlockMatcher = plotBlockMatcher;
    }

    public boolean isPlotBlock(ItemStack itemStack) {
        return plotBlockMatcher.isBlockMatching(itemStack);
    }

    public int getPlotBlockStartSize(ItemStack itemStack) {
        return this.plotBlocks.get(plotBlockMatcher.getMatchingBlock(itemStack).get());
    }

    public void setupPlotBlocks(Plugin plugin) {
        this.plotBlocks = blocksConfiguration.plotBlocks.values().stream()
            .peek(plotBlock -> BlocksConfiguration.addRecipe(plotBlock, "eternalplots-plot-recipe", plugin))
            .collect(Collectors.toMap(Function.identity(), plotBlock -> plotBlock.startSize, (a, b) -> b, HashMap::new));
    }

    public boolean canSetupPlot(Location location) {
        if (plotManager.getRegion(location).isPresent()) {
            return false;
        }

        int maxSize = pluginConfiguration.plot.maxSize;
        int addonSize = pluginConfiguration.spaceBlocks;
        int size = (maxSize / 2) + addonSize;

        List<Location> locations = generateCheckLocations(location, size);

        return plotManager.getPlots().stream()
            .noneMatch(plot -> locations.stream()
                .anyMatch(plotLocation -> {
                    Position center = plot.getRegion().getCenter();
                    Location convert = PositionAdapter.convert(center);

                    return isInPlotRange(convert, location, size);
                }));
    }

    private List<Location> generateCheckLocations(Location loc, int size) {
        return Stream.of(
            new Location(loc.getWorld(), loc.getX() + size, loc.getY(), loc.getZ() + size),
            new Location(loc.getWorld(), loc.getX() - size, loc.getY(), loc.getZ() - size),
            new Location(loc.getWorld(), loc.getX() - size, loc.getY(), loc.getZ() + size),
            new Location(loc.getWorld(), loc.getX() + size, loc.getY(), loc.getZ() - size),
            new Location(loc.getWorld(), loc.getX() + size, loc.getY(), loc.getZ()),
            new Location(loc.getWorld(), loc.getX() - size, loc.getY(), loc.getZ()),
            new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + size),
            new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - size),
            new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ())
        ).toList();
    }

    private boolean isInPlotRange(Location plotCenter, Location locationToCheck, int size) {
        Location plotPos1 = new Location(plotCenter.getWorld(), plotCenter.getX() + size, plotCenter.getY(), plotCenter.getZ() + size);
        Location plotPos2 = new Location(plotCenter.getWorld(), plotCenter.getX() - size, plotCenter.getY(), plotCenter.getZ() - size);

        return LocationUtils.isIn(plotPos1, plotPos2, locationToCheck);
    }

    public boolean isSafeRegion(Location location) {
        return !((location.getX() < 250 && location.getX() > -250) && (location.getZ() < 250 && location.getZ() > -250));
    }
}