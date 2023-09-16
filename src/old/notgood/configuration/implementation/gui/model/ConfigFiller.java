package com.eternalcode.plots.notgood.configuration.implementation.gui.model;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import eu.okaeri.configs.OkaeriConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigFiller extends OkaeriConfig {

    public List<Material> materials = new ArrayList<>();

    public ConfigFiller(Material... materials) {
        this.materials = Arrays.asList(materials);
    }

    public ConfigFiller() {

    }

    public List<GuiItem> getFiller() {
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