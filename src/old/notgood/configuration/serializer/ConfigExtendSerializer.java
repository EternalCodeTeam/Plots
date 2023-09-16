package com.eternalcode.plots.notgood.configuration.serializer;

import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigExtend;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ConfigExtendSerializer implements ObjectSerializer<ConfigExtend> {
    @Override
    public boolean supports(Class<? super ConfigExtend> type) {
        if (type == null) {
            throw new java.lang.NullPointerException("type is marked non-null but is null");
        }
        return ConfigExtend.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ConfigExtend object, SerializationData data, GenericsDeclaration generics) {
        if (object == null) {
            throw new java.lang.NullPointerException("object is marked non-null but is null");
        }
        if (data == null) {
            throw new java.lang.NullPointerException("data is marked non-null but is null");
        }
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        if (object.items != null && !object.items.isEmpty()) {
            data.add("items", object.items);
        }
        if (object.money != 0) {
            data.add("money", object.money);
        }
        data.add("blocks", object.blocks);
    }

    @Override
    public ConfigExtend deserialize(DeserializationData data, GenericsDeclaration generics) {
        if (data == null) {
            throw new java.lang.NullPointerException("data is marked non-null but is null");
        }
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        Map<Material, Integer> items = data.containsKey("items") ? data.getAsMap("items", Material.class, Integer.class) : new HashMap<>();
        double money = data.containsKey("money") ? data.get("money", Double.class) : 0.0;
        int blocks = data.get("blocks", Integer.class);
        return new ConfigExtend(blocks, money, items);
    }
}
