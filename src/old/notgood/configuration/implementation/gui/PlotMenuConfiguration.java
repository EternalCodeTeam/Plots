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
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PlotMenuConfiguration extends OkaeriConfig {

    @Comment({ "# Gui title" })
    public String title = "Select Plot";

    @Comment({ "", "# Gui rows [1-6]" })
    public int rows = 5;

    @Comment({ "", "# Gui border materials" })
    public ConfigBorder border = new ConfigBorder(Material.GRAY_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE);

    @Comment({ "", "# Gui filler materials" })
    public ConfigFiller filler = new ConfigFiller();

    @Comment({ "", "# Additional items in gui" })
    public Map<String, ConfigItem> items = new ImmutableMap.Builder<String, ConfigItem>()

        .put("nextPage", new ConfigItem(9, Material.ARROW, "&bNastępna Strona", new ArrayList<>(), ConfigAction.NEXT_PAGE))
        .put("previousPage", new ConfigItem(27, Material.ARROW, "&bPoprzednia Strona", new ArrayList<>(), ConfigAction.PREVIOUS_PAGE))
        .put("closeGui", new ConfigItem(26, Material.BARRIER, "&cZamknij Panel", new ArrayList<>(), ConfigAction.CLOSE))

        .put("glass1", new ConfigItem(10, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))
        .put("glass2", new ConfigItem(19, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))
        .put("glass3", new ConfigItem(28, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))
        .put("glass4", new ConfigItem(16, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))
        .put("glass5", new ConfigItem(25, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))
        .put("glass6", new ConfigItem(34, Material.GRAY_STAINED_GLASS_PANE, "", new ArrayList<>(), ConfigAction.NONE))

        .build();

    @Comment({ "", "# Plots in gui", "# Do not add random items here! (do it in section 'items')", "# Variables: {PLOT_NAME}, {PLOT_UUID}, {PLOT_OWNER_NAME}, {PLOT_OWNER_UUID}" })
    public Map<String, PlotSection> plotsPlaces = new ImmutableMap.Builder<String, PlotSection>()

        .put("slot-1", new PlotSection(
            new ConfigItem(11, Material.PLAYER_HEAD, "&aDziałka {PLOT_NAME}", new ArrayList<>(), ConfigAction.NONE),
            new ConfigItem(20, Material.COMPASS, "&aOtwórz Panel", new ArrayList<>(), ConfigAction.PLOT_MENU),
            new ConfigItem(29, Material.DARK_OAK_DOOR, "&aTeleport do działki", new ArrayList<>(), ConfigAction.TELEPORT_PLOT)
        ))

        .put("slot-2", new PlotSection(
            new ConfigItem(12, Material.PLAYER_HEAD, "&aDziałka {PLOT_NAME}", new ArrayList<>(), ConfigAction.NONE),
            new ConfigItem(21, Material.COMPASS, "&aOtwórz Panel", new ArrayList<>(), ConfigAction.PLOT_MENU),
            new ConfigItem(30, Material.DARK_OAK_DOOR, "&aTeleport do działki", new ArrayList<>(), ConfigAction.TELEPORT_PLOT)
        ))

        .put("slot-3", new PlotSection(
            new ConfigItem(13, Material.PLAYER_HEAD, "&aDziałka {PLOT_NAME}", new ArrayList<>(), ConfigAction.NONE),
            new ConfigItem(22, Material.COMPASS, "&aOtwórz Panel", new ArrayList<>(), ConfigAction.PLOT_MENU),
            new ConfigItem(31, Material.DARK_OAK_DOOR, "&aTeleport do działki", new ArrayList<>(), ConfigAction.TELEPORT_PLOT)
        ))

        .put("slot-4", new PlotSection(
            new ConfigItem(14, Material.PLAYER_HEAD, "&aDziałka {PLOT_NAME}", new ArrayList<>(), ConfigAction.NONE),
            new ConfigItem(23, Material.COMPASS, "&aOtwórz Panel", new ArrayList<>(), ConfigAction.PLOT_MENU),
            new ConfigItem(32, Material.DARK_OAK_DOOR, "&aTeleport do działki", new ArrayList<>(), ConfigAction.TELEPORT_PLOT)
        ))

        .put("slot-5", new PlotSection(
            new ConfigItem(15, Material.PLAYER_HEAD, "&aDziałka {PLOT_NAME}", new ArrayList<>(), ConfigAction.NONE),
            new ConfigItem(24, Material.COMPASS, "&aOtwórz Panel", new ArrayList<>(), ConfigAction.PLOT_MENU),
            new ConfigItem(33, Material.DARK_OAK_DOOR, "&aTeleport do działki", new ArrayList<>(), ConfigAction.TELEPORT_PLOT)
        ))

        .build();

    public static class PlotSection {
        public ConfigItem plotItem = new ConfigItem();
        public ConfigItem optionsItem = new ConfigItem();
        public ConfigItem teleportItem = new ConfigItem();

        public PlotSection() {

        }

        public PlotSection(ConfigItem plotItem, ConfigItem optionsItem, ConfigItem teleportItem) {
            this.plotItem = plotItem;
            this.optionsItem = optionsItem;
            this.teleportItem = teleportItem;
        }
    }

}
