package com.eternalcode.plots.configuration.implementations.gui;

import com.eternalcode.plots.configuration.implementations.gui.models.ConfigAction;
import com.eternalcode.plots.configuration.implementations.gui.models.ConfigBorder;
import com.eternalcode.plots.configuration.implementations.gui.models.ConfigFiller;
import com.eternalcode.plots.configuration.implementations.gui.models.ConfigItem;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlotPlayersConfiguration extends OkaeriConfig {

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
        .build();

    @Comment({ "", "# Players in gui template" })
    public PlayerTemplate playerTemplate = new PlayerTemplate();

    public static class PlayerTemplate extends OkaeriConfig {

        public String itemName = "&a{PLAYER_NAME}";
        public List<String> itemLore = List.of("&7Kliknij aby wyrzucić");
        public ConfigAction leftClickAction = ConfigAction.KICK_PLAYER;
        public ConfigAction rightClickAction = ConfigAction.KICK_PLAYER;
    }
}
