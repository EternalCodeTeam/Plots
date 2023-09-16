package com.eternalcode.plots.notgood.configuration.implementation.gui.model;

import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.feature.extend.ExtendType;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConfigExtend extends OkaeriConfig {

    public int blocks;
    public double money;
    public Map<Material, Integer> items;

    public ConfigExtend(int blocks, double money, Map<Material, Integer> items) {
        this.blocks = blocks;
        this.money = money;
        this.items = items;
    }

    public ConfigExtend() {

    }

    public static ConfigExtend getConfigExtend(Plot plot, PluginConfiguration pluginConfiguration) {

        int extendLevel = plot.getRegion().getExtendLevel();

        SortedMap<Integer, ConfigExtend> sortedMap = new TreeMap<>(pluginConfiguration.plot.extend.extendLevels);

        int i = 0;
        for (Map.Entry<Integer, ConfigExtend> entry : sortedMap.entrySet()) {
            if (i == extendLevel) {
                return entry.getValue();
            }
            i++;
        }

        return null;
    }

    public ExtendType getType() {

        if (money == 0 && !items.isEmpty()) {
            return ExtendType.ITEM;
        }

        if (money != 0 && items.isEmpty()) {
            return ExtendType.VAULT;
        }

        return ExtendType.BOTH;
    }
}