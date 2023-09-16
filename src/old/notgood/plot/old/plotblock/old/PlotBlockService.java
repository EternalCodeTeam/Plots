package com.eternalcode.plots.notgood.plot.old.plotblock.old;

import com.eternalcode.plots.notgood.configuration.implementation.BlocksConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.good.position.Position;
import com.eternalcode.plots.good.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// Use Piotrulla CraftingLib maybe?
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
        if (plotManager.getPlotRegionByLocation(location).isPresent()) {
            return false;
        }

        int maxSize = pluginConfiguration.plot.maxSize;
        int addonSize = pluginConfiguration.spaceBlocks;
        int size = (maxSize / 2) + addonSize;

        List<Location> locations = this.plotManager.generateCheckLocations(location, size);

        return plotManager.getPlots().stream()
            .noneMatch(plot -> locations.stream()
                .anyMatch(plotLocation -> {
                    Position center = plot.getRegion().getCenter();
                    Location convert = PositionAdapter.convert(center);

                    return this.plotManager.isInPlotRange(convert, location, size);
                }));
    }
}