package com.eternalcode.plots.notgood.plot.old.plotblock.old;

import com.eternalcode.plots.notgood.configuration.implementation.BlocksConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import panda.std.Option;

import java.util.Objects;

public class PlotBlockMatcher {

    private final BlocksConfiguration blocksConfiguration;

    public PlotBlockMatcher(BlocksConfiguration blocksConfiguration) {
        this.blocksConfiguration = blocksConfiguration;
    }

    public boolean isBlockMatching(ItemStack itemStack) {
        return getMatchingBlock(itemStack).isPresent();
    }

    Option<BlocksConfiguration.PlotBlock> getMatchingBlock(ItemStack itemStack) {
        for (BlocksConfiguration.PlotBlock plotBlock : this.blocksConfiguration.plotBlocks.values()) {
            if (doItemsMatch(itemStack, BlocksConfiguration.getItemStack(plotBlock))) {
                return Option.of(plotBlock);
            }
        }
        return Option.none();
    }

    private boolean doItemsMatch(ItemStack item1, ItemStack item2) {
        if (item1.getType() != item2.getType()) {
            return false;
        }
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        if (meta1 == null || meta2 == null) {
            return meta1 == meta2;
        }

        return meta1.getDisplayName().equalsIgnoreCase(meta2.getDisplayName()) &&
            Objects.equals(meta1.getLore(), meta2.getLore());
    }
}