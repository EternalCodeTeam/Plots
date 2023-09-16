package com.eternalcode.plots.notgood.configuration.implementation;

import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigExtend;
import com.eternalcode.plots.database.recoded.DatabaseType;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfiguration extends OkaeriConfig {

    public Storage storage = new Storage();

    @Comment("")
    public StartUp startUp = new StartUp();

    @Comment("")
    public int validityDays = 3;
    public int spaceBlocks = 0;

    @Comment("")
    public Plot plot = new Plot();

    public static class Storage extends OkaeriConfig {

        @Comment({ "", "SQL Drivers and ports:", "- MySQL (3306), MariaDB (3306), PostgreSQL (5432)", "- SQLite, H2" })
        public DatabaseType driver = DatabaseType.H2;

        @Comment("")
        public Settings settings = new Settings();

        public static class Settings extends OkaeriConfig {
            public String hostname = "localhost";
            public String database = "database";
            public String username = "root";
            public String password = "";
            public int port = 3306;
        }
    }

    public static class StartUp extends OkaeriConfig {
        public boolean logo = true;
    }

    public static class Plot extends OkaeriConfig {

        @Comment({ "'75' -> Plot max size will be 75x75", "WARNING: EDITING THIS OPTION WHEN PLOTS ARE CREATED CAN BRAKE EXTEND SYSTEM!" })
        public int maxSize = 75;

        @Comment({ "Maximum plots number for permissions", "example: 'vip' -> eternalplots.limit.vip", "'default' not need permission!" })
        public Map<String, Integer> limits = new ImmutableMap.Builder<String, Integer>()
            .put("default", 1)
            .put("vip", 2)
            .put("svip", 3)
            .put("mvp", 5)
            .build();

        @Comment("")
        public Extend extend = new Extend();

        public static class Extend extends OkaeriConfig {

            @Comment({ "Line of section 'neededItems'", " {AMOUNT} - amount of item", " {ITEM} - translated item name from 'translatedItems'" })
            public String itemFormat = "x{AMOUNT} - {ITEM}";

            @Comment({ "", "Declaimer of neededItems List" })
            public String joinerFormat = "&7, ";

            @Comment({
                "",
                " blocks - blocks added to plot size after successful extend (ex. 10: 10x10 -> 20x20)",
                " money - money needed for extend (need Vault plugin)",
                " items - items needed for extend",
                "WARNING: EDITING THIS OPTION WHEN PLOTS ARE CREATED CAN BRAKE EXTEND SYSTEM!" })
            public Map<Integer, ConfigExtend> extendLevels = new ImmutableMap.Builder<Integer, ConfigExtend>()
                .put(1, new ConfigExtend(10, 0, new ImmutableMap.Builder<Material, Integer>().put(Material.DIAMOND, 1).build()))
                .put(2, new ConfigExtend(10, 100, new ImmutableMap.Builder<Material, Integer>().put(Material.DIAMOND, 5).build()))
                .put(3, new ConfigExtend(10, 200, null))
                .put(4, new ConfigExtend(10, 0, new ImmutableMap.Builder<Material, Integer>().put(Material.DIAMOND, 10).put(Material.EMERALD, 15).build()))
                .put(5, new ConfigExtend(10, 300, new ImmutableMap.Builder<Material, Integer>().put(Material.DIAMOND, 15).put(Material.EMERALD, 30).build()))
                .build();

            @Comment({ "", "Items to translate", "Example: <item_name>:<translated_name>" })
            public Map<String, String> translatedItems = new ImmutableMap.Builder<String, String>()
                .put("DIAMOND", "Diament")
                .put("GOLD_INGOT", "Zloto")
                .build();

            @Comment({ "", "No costs messages" })
            public String noItemsMessage = "&cYou don't have enough items to extend plot";
            public String noMoneyMessage = "&cYou don't have enough money to extend plot";
            public String noBothMessage = "&cYou don't have enough items or money to extend plot";
        }
    }

}
