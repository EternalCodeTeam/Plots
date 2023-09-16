package com.eternalcode.plots.notgood.configuration.serializer;

import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigAction;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigItemSerializer implements ObjectSerializer<ConfigItem> {
    @Override
    public boolean supports(Class<? super ConfigItem> type) {
        if (type == null) {
            throw new java.lang.NullPointerException("type is marked non-null but is null");
        }
        return ConfigItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ConfigItem configItem, @NotNull SerializationData data, GenericsDeclaration generics) {
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        if (configItem.getMaterial() != null && configItem.getMaterial() != Material.AIR) {
            data.add("material", configItem.getMaterial());
        }
        if (configItem.getName() != null && !configItem.getName().equalsIgnoreCase("")) {
            data.add("name", configItem.getName());
        }
        if (configItem.getLore() != null && !configItem.getLore().isEmpty()) {
            data.add("lore", configItem.getLore());
        }
        if (configItem.getAction() != null && configItem.getAction() != ConfigAction.NONE) {
            data.add("action", configItem.getAction());
        }
        data.add("slot", configItem.getSlot());
    }

    @Override
    public ConfigItem deserialize(DeserializationData data, GenericsDeclaration generics) {
        if (data == null) {
            throw new java.lang.NullPointerException("data is marked non-null but is null");
        }
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        String materialName = data.get("material", String.class);
        Material material = Material.valueOf(materialName);
        String actionName = data.get("action", String.class);
        ConfigAction configAction;
        if (actionName == null) {
            configAction = ConfigAction.NONE;
        } else {
            configAction = ConfigAction.valueOf(actionName);
        }
        int slot =  // contains
            data.containsKey("slot") ? data.get("slot", Integer.class) // yes
                : 0; // no
        String name = data.containsKey("name") ? data.get("name", String.class) : "";
        List<String> lore = data.containsKey("lore") ? data.getAsList("lore", String.class) : new ArrayList<>();
        return new ConfigItem(slot, material, name, lore, configAction);
    }
}
