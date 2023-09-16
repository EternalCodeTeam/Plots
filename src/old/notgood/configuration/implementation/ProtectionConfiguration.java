package com.eternalcode.plots.notgood.configuration.implementation;

import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ProtectionConfiguration extends OkaeriConfig {

    @Comment({ "ConfigProtection flags for plots (not plot members)", "enabled - is protection enabled", "displayInGui - is protection visible in flags plot gui" })
    Map<String, ConfigProtection> protections = new ImmutableMap.Builder<String, ConfigProtection>()
        .put("pvp", new ConfigProtection(FlagType.PVP, true, true, Material.DIAMOND_SWORD, "Players PVP", "PVP between players on plot"))
        .put("block-place", new ConfigProtection(FlagType.BLOCK_PLACE, true, true, Material.DIAMOND_SHOVEL, "Placing Blocks", "Placing blocks on plot"))
        .put("block-break", new ConfigProtection(FlagType.BLOCK_BREAK, true, true, Material.DIAMOND_PICKAXE, "Breaking Blocks", "Breaking blocks on plot"))
        .put("friendly-mobs", new ConfigProtection(FlagType.FRIENDLY_MOBS, true, true, Material.CARROT, "Friendly Mobs", "Attacking friendly mobs/nametag mobs on plot"))
        .put("monsters", new ConfigProtection(FlagType.MONSTERS, false, true, Material.ZOMBIE_HEAD, "Monsters", "Attacking monsters on plot"))
        .put("vehicle-destroy", new ConfigProtection(FlagType.VEHICLE_DESTROY, true, true, Material.OAK_BOAT, "Vehicles Destroying", "Destroying vehicles on plot"))
        .put("clicked-entities", new ConfigProtection(FlagType.CLICKED_ENTITIES, true, true, Material.VILLAGER_SPAWN_EGG, "Clicking Mobs", "NPC, armor-stands protection on plot"))
        .put("chest-access", new ConfigProtection(FlagType.CHEST_ACCESS, true, true, Material.CHEST, "Chest-like Access", "Open chests, furnaces, craftings etc. on plot"))
        .put("use", new ConfigProtection(FlagType.USE, true, true, Material.DARK_OAK_DOOR, "Use Blocks", "Open doors, click buttons, levers etc. on plot"))
        .build();

    @Comment({ "", "Griefing protection flags for plots (not plot members)", "enabled - is protection enabled", "displayInGui - is protection visible in flags plot gui" })
    SectionGriefing griefing = new SectionGriefing();

    public Map<String, ConfigProtection> getProtections() {
        return this.protections;
    }

    public SectionGriefing getGriefing() {
        return this.griefing;
    }

    public static class SectionGriefing extends OkaeriConfig {

        @Comment({ "", "Can friendly mobs + nametag mobs get damage by explosion, freezing, potions etc. on plot?" })
        Griefing friendlyMobs = new Griefing(true);

        @Comment({ "", "Can monsters get damage by explosion, freezing, potions etc. on plot?" })
        Griefing monsters = new Griefing(false);

        @Comment({ "", "Can water/lava spill over to plot?" })
        Griefing waterLavaFlow = new Griefing(true);

        @Comment({ "", "Can guests spawn chickens by egg throwing on plot?" })
        Griefing eggThrow = new Griefing(true);

        @Comment({ "", "Can guests fish other entities by fishing rod on plot?" })
        Griefing fishingRod = new Griefing(true);

        @Comment({ "", "Can tnt, creeper etc. explode on plot?" })
        Griefing explosion = new Griefing(true);

        @Comment({ "", "Can guests destroy farm by jumping on plot?" })
        Griefing farm = new Griefing(true);

        @Comment({ "", "Can guests move blocks from/to plot using pistons?" })
        Griefing piston = new Griefing(true);

        @Comment({ "", "Can guests use dispensers behind plot border?" })
        Griefing dispenser = new Griefing(true);

        @Comment({ "", "Can guests use bone meal etc. behind plot border?" })
        Griefing fertilize = new Griefing(true);

        @Comment({ "", "Can all players receive negative effects on plot?" })
        Griefing negativeEffects = new Griefing(true);


        public Griefing getFriendlyMobs() {
            return this.friendlyMobs;
        }

        public Griefing getMonsters() {
            return this.monsters;
        }

        public Griefing getWaterLavaFlow() {
            return this.waterLavaFlow;
        }

        public Griefing getEggThrow() {
            return this.eggThrow;
        }

        public Griefing getFishingRod() {
            return this.fishingRod;
        }

        public Griefing getExplosion() {
            return this.explosion;
        }

        public Griefing getFarm() {
            return this.farm;
        }

        public Griefing getPiston() {
            return this.piston;
        }

        public Griefing getDispenser() {
            return this.dispenser;
        }

        public Griefing getFertilize() {
            return this.fertilize;
        }

        public Griefing getNegativeEffects() {
            return this.negativeEffects;
        }
    }

    public static class ConfigProtection extends OkaeriConfig {

        private FlagType type;

        @CustomKey("default-protection")
        private boolean defaultProtection = true;
        private boolean editInGui = true;
        private Material guiMaterial = Material.STONE;
        private String guiName = "flag name in gui";
        private String guiDescription = "flag description in gui";

        private ConfigProtection() {
        }

        private ConfigProtection(FlagType flagType, boolean defaultProtection, boolean editInGui, Material guiMaterial, String guiName, String guiDescription) {
            this.type = flagType;
            this.editInGui = editInGui;
            this.defaultProtection = defaultProtection;
            this.guiMaterial = guiMaterial;
            this.guiName = guiName;
            this.guiDescription = guiDescription;
        }

        public boolean getDefaultProtection() {
            return defaultProtection;
        }

        public FlagType getType() {
            return this.type;
        }

        public boolean isEditInGui() {
            return this.editInGui;
        }

        public Material getGuiMaterial() {
            return this.guiMaterial;
        }

        public String getGuiName() {
            return this.guiName;
        }

        public String getGuiDescription() {
            return this.guiDescription;
        }
    }

    public static class Griefing extends OkaeriConfig {

        @CustomKey("protected")
        private boolean protection = true;

        private Griefing() {

        }

        private Griefing(boolean protection) {
            this.protection = protection;
        }

        public boolean isProtection() {
            return this.protection;
        }
    }

}






