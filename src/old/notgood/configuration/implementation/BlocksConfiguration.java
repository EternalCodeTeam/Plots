package com.eternalcode.plots.notgood.configuration.implementation;

import com.google.common.collect.ImmutableMap;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class BlocksConfiguration extends OkaeriConfig {

    @Comment({
        "# Crafting Schema:",
        "# [1][2][3]",
        "# [4][5][6] -> [ITEM]",
        "# [7][8][9]",
        "# AIR is empty place"
    })

    @Comment({ "", "List of plot creating blocks", "You can add how much do you want!" })
    public Map<String, PlotBlock> plotBlocks = new ImmutableMap.Builder<String, PlotBlock>()
        .put("block1", new PlotBlock(20, Material.DIAMOND_BLOCK, "Plot Block", Arrays.asList("Description1", "Description2"), new Crafting(
            true,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR)))

        .put("block2", new PlotBlock(40, Material.EMERALD_BLOCK, "VIP Plot Block", Arrays.asList("Description1", "Description2"), new Crafting(
            true,
            Material.AIR,
            Material.EMERALD,
            Material.AIR,
            Material.EMERALD,
            Material.AIR,
            Material.EMERALD,
            Material.AIR,
            Material.EMERALD,
            Material.AIR)))

        .build();

    public static ItemStack getItemStack(PlotBlock plotBlock) {

        List<Component> lore = new ArrayList<>();

        for (String line : plotBlock.itemLore) {
            lore.add(Component.text(LegacyUtils.color(line)));
        }

        return ItemBuilder.from(plotBlock.itemMaterial)
            .name(Component.text(LegacyUtils.color(plotBlock.itemName)))
            .lore(lore)
            .build();
    }

    public static void addRecipe(PlotBlock plotBlock, String recipeName, Plugin plugin) {

        if (!plotBlock.crafting.enabled) {
            return;
        }

        NamespacedKey key = new NamespacedKey(plugin, recipeName);
        ShapedRecipe sr = new ShapedRecipe(key, getItemStack(plotBlock));
        sr.shape("ABC", "DEF", "GHI");

        char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

        int i = 0;
        for (int number : plotBlock.crafting.getCrafting().keySet()) {
            sr.setIngredient(chars[i], plotBlock.crafting.getCrafting().get(number));
            i++;
        }
        Recipe recipe = Bukkit.getRecipe(key);
        if (recipe != null) {
            Bukkit.removeRecipe(key);
        }
        Bukkit.addRecipe(sr);
    }

    public static class PlotBlock extends OkaeriConfig {

        @Comment({ "Start plot size placed by this block" })
        public int startSize = 20;

        @Comment({ "Plot block material name" })
        public Material itemMaterial = Material.DIAMOND_BLOCK;

        @Comment({ "Plot block item name" })
        public String itemName = "Plot Block";

        @Comment({ "Plot block item lore" })
        public List<String> itemLore = Arrays.asList("Description 1", "Description 2");

        public Crafting crafting = new Crafting(
            true,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR,
            Material.DIAMOND,
            Material.AIR);

        PlotBlock() {

        }

        PlotBlock(int startSize, Material itemMaterial, String itemName, List<String> itemLore, Crafting crafting) {
            this.startSize = startSize;
            this.itemMaterial = itemMaterial;
            this.itemName = itemName;
            this.itemLore = itemLore;
            this.crafting = crafting;
        }
    }

    public static class Crafting extends OkaeriConfig {

        public boolean enabled = true;
        @CustomKey("1")
        public Material c1 = Material.AIR;
        @CustomKey("2")
        public Material c2 = Material.AIR;
        @CustomKey("3")
        public Material c3 = Material.AIR;
        @CustomKey("4")
        public Material c4 = Material.AIR;
        @CustomKey("5")
        public Material c5 = Material.AIR;
        @CustomKey("6")
        public Material c6 = Material.AIR;
        @CustomKey("7")
        public Material c7 = Material.AIR;
        @CustomKey("8")
        public Material c8 = Material.AIR;
        @CustomKey("9")
        public Material c9 = Material.AIR;

        public Crafting(boolean enabled, Material c1, Material c2, Material c3, Material c4, Material c5, Material c6, Material c7, Material c8, Material c9) {
            this.enabled = enabled;
            this.c1 = c1;
            this.c2 = c2;
            this.c3 = c3;
            this.c4 = c4;
            this.c5 = c5;
            this.c6 = c6;
            this.c7 = c7;
            this.c8 = c8;
            this.c9 = c9;
        }

        public Crafting() {

        }

        public Map<Integer, Material> getCrafting() {
            return new ImmutableMap.Builder<Integer, Material>()
                .put(1, this.c1)
                .put(2, this.c2)
                .put(3, this.c3)
                .put(4, this.c4)
                .put(5, this.c5)
                .put(6, this.c6)
                .put(7, this.c7)
                .put(8, this.c8)
                .put(9, this.c9)
                .build();
        }
    }
}
