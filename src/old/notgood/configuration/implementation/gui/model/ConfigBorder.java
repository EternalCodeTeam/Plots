package com.eternalcode.plots.notgood.configuration.implementation.gui.model;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.configs.OkaeriConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigBorder extends OkaeriConfig {

    public List<Material> materials = new ArrayList<>();

    public ConfigBorder(Material... materials) {
        this.materials = Arrays.asList(materials);
    }

    public ConfigBorder() {

    }

    public List<GuiItem> getBorder() {
        List<GuiItem> guiItems = new ArrayList<>();

        for (Material material : materials) {

            if (material == null) {
                continue;
            }

            guiItems.add(
                ItemBuilder.from(material)
                    .name(Component.text(""))
                    .asGuiItem()
            );
        }
        return guiItems;
    }
}