package com.eternalcode.plots.notgood.configuration.implementation.gui;

import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigAction;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigBorder;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigFiller;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PlotPanelConfiguration extends OkaeriConfig {

    @Comment({ "# Gui title" })
    public String title = "&7Panel Dzialki <gradient:#66ff99:#00ffff>{PLOT_NAME}</gradient>";

    @Comment({ "", "# Gui rows [1-6]" })
    public int rows = 5;

    @Comment({ "", "# Gui border materials" })
    public ConfigBorder border = new ConfigBorder(Material.GRAY_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE);

    @Comment({ "", "# Gui filler materials" })
    public ConfigFiller filler = new ConfigFiller(Material.GRAY_STAINED_GLASS_PANE);

    @Comment({ "", "# Items in gui" })
    public Map<String, ConfigItem> items = new ImmutableMap.Builder<String, ConfigItem>()

        .put("item2", new ConfigItem(13, Material.HOPPER, "&7Powiekszenie Dzialki", new ArrayList<>(), ConfigAction.EXTEND_MENU))

        .put("item4", new ConfigItem(21, Material.SPRUCE_SIGN, "&7Zmiana Nazwy", new ArrayList<>(), ConfigAction.NAME_MENU))
        .put("item5", new ConfigItem(23, Material.CHEST_MINECART, "&7Przedluzenie Dzialki", new ArrayList<>(), ConfigAction.NONE))

        .put("item6", new ConfigItem(29, Material.WRITABLE_BOOK, "&7Zarzadzaj Dodanymi", new ArrayList<>(), ConfigAction.PLAYERS_MENU))
        .put("item7", new ConfigItem(31, Material.GLOWSTONE_DUST, "&7Zmiana Czasteczek", new ArrayList<>(), ConfigAction.NONE))
        .put("item8", new ConfigItem(33, Material.NETHERITE_AXE, "&7Ochrona Dzialki", new ArrayList<>(), ConfigAction.FLAGS_MENU))

        .put("item9", new ConfigItem(18, Material.PAINTING, "&7Informacje o <gradient:#66ff99:#00ffff>{PLOT_NAME}</gradient>", Arrays.asList(
            "",
            "&7Wlasciciel: &c{PLOT_OWNER_NAME}",
            "&7Czlonkowie: <gradient:#66ff99:#00ffff>{PLOT_MEMBERS}</gradient>",
            "&7Rozmiar: <gradient:#66ff99:#00ffff>{PLOT_SIZE}x{PLOT_SIZE}</gradient>",
            "",
            "&7Zalozono: <gradient:#66ff99:#00ffff>{PLOT_CREATED}</gradient>",
            "&7Wygasa: <gradient:#66ff99:#00ffff>{PLOT_EXPIRES}</gradient>"
        ), ConfigAction.NONE))
        .put("closeGui", new ConfigItem(26, Material.BARRIER, "&cZamknij Panel", new ArrayList<>(), ConfigAction.CLOSE))

        .build();
}
