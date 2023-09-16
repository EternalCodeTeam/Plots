package com.eternalcode.plots.notgood.configuration.serializer;

import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotMenuConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

public class PlotSectionSerializer implements ObjectSerializer<PlotMenuConfiguration.PlotSection> {
    @Override
    public boolean supports(Class<? super PlotMenuConfiguration.PlotSection> type) {
        if (type == null) {
            throw new java.lang.NullPointerException("type is marked non-null but is null");
        }
        return PlotMenuConfiguration.PlotSection.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(PlotMenuConfiguration.PlotSection plotSection, @NotNull SerializationData data, GenericsDeclaration generics) {
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        if (plotSection.plotItem != null) {
            data.add("plotItem", plotSection.plotItem);
        }
        if (plotSection.optionsItem != null) {
            data.add("optionsItem", plotSection.optionsItem);
        }
        if (plotSection.teleportItem != null) {
            data.add("teleportItem", plotSection.teleportItem);
        }
    }

    @Override
    public PlotMenuConfiguration.PlotSection deserialize(DeserializationData data, GenericsDeclaration generics) {
        if (data == null) {
            throw new java.lang.NullPointerException("data is marked non-null but is null");
        }
        if (generics == null) {
            throw new java.lang.NullPointerException("generics is marked non-null but is null");
        }
        ConfigItem plotItem = data.containsKey("plotItem") ? data.get("plotItem", ConfigItem.class) : null;
        ConfigItem optionsItem = data.containsKey("optionsItem") ? data.get("optionsItem", ConfigItem.class) : null;
        ConfigItem teleportItem = data.containsKey("teleportItem") ? data.get("teleportItem", ConfigItem.class) : null;
        return new PlotMenuConfiguration.PlotSection(plotItem, optionsItem, teleportItem);
    }
}
