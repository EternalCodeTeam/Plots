package com.eternalcode.plots.notgood.configuration.implementation.gui;

import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigBorder;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigFiller;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.google.common.collect.ImmutableMap;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PlotFlagsConfiguration extends OkaeriConfig {

    @Comment({ "# Gui title" })
    public String title = "Players Permissions";

    @Comment({ "", "# Gui rows [1-6]" })
    public int rows = 5;

    @Comment({ "", "# Gui border materials" })
    public ConfigBorder border = new ConfigBorder(Material.GRAY_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE);

    @Comment({ "", "# Gui filler materials" })
    public ConfigFiller filler = new ConfigFiller();

    @Comment({ "", "# Status formats" })
    public String enabledStatus = "<gradient:#9CFB00:#13FD2A>Enabled</gradient>";
    public String disabledStatus = "<gradient:#FB5E5E:#FF2F2F>Disabled</gradient>";

    @Comment({ "", "# Items in gui format", "# Flags are in file 'protection.yml'" })
    public FlagItem flagItem = new FlagItem();
    @Comment({ "", "# Additional items in gui" })
    public Map<String, ConfigItem> items = new ImmutableMap.Builder<String, ConfigItem>()
        .build();

    public static class FlagItem extends OkaeriConfig {

        private String material = "{FLAG_MATERIAL}";
        private String name = "<gradient:#FBA600:#FFF500>{FLAG_NAME}</gradient>";
        private List<String> lore = Arrays.asList(
            "&7{FLAG_DESCRIPTION}",
            "",
            "&7Status: {STATUS}"
        );

        public FlagItem() {

        }

        public FlagItem(String material, String name, List<String> lore) {
            this.material = material;
            this.name = name;
            this.lore = lore;
        }

        public ItemStack getItemStack(MiniMessage miniMessage) {

            List<String> lore = new ArrayList<>();
            List<Component> loreComponent = new ArrayList<>();

            if (this.lore != null && !this.lore.isEmpty()) {
                lore = this.lore;
            }

            for (String line : lore) {
                loreComponent.add(miniMessage.deserialize(line).decoration(TextDecoration.ITALIC, false));
            }

            return ItemBuilder.from(Material.valueOf(material))
                .name(miniMessage.deserialize(name).decoration(TextDecoration.ITALIC, false))
                .lore(loreComponent)
                .build();
        }

        public FlagItem replaceFlagVariables(Material flagMaterial, String flagName, String flagDescription, boolean status, String enabledStatus, String disabledStatus) {

            String statusMessage = enabledStatus;
            if (!status) {
                statusMessage = disabledStatus;
            }

            FlagItem flagItem = new FlagItem(this.material, this.name, this.lore);
            flagItem.material = flagItem.material.replace("{FLAG_MATERIAL}", flagMaterial.name());
            flagItem.name = flagItem.name.replace("{FLAG_NAME}", flagName);
            flagItem.name = flagItem.name.replace("{FLAG_DESCRIPTION}", flagDescription);
            flagItem.name = flagItem.name.replace("{STATUS}", statusMessage);

            List<String> lore = new ArrayList<>();
            for (String line : flagItem.lore) {
                lore.add(line.replace("{FLAG_NAME}", flagName).replace("{FLAG_DESCRIPTION}", flagDescription).replace("{STATUS}", statusMessage));
            }
            flagItem.lore = lore;

            return flagItem;
        }

        public String getMaterial() {
            return this.material;
        }

        public String getName() {
            return this.name;
        }

        public List<String> getLore() {
            return this.lore;
        }
    }
}
