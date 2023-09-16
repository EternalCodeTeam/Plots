package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotFlagsConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.eternalcode.plots.notgood.plot.old.protection.FlagType;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;

import java.util.Collection;
import java.util.List;

public class PlotFlagInventory {

    private final InventoryActions inventoryActions;
    private final ProtectionConfiguration protectionConfig;
    private final PlotFlagsConfiguration plotFlagsConfig;
    private final ProtectionManager protectionManager;
    private final MiniMessage miniMessage;
    private final Plot plot;

    public PlotFlagInventory(InventoryActions inventoryActions, ProtectionConfiguration protectionConfig, PlotFlagsConfiguration plotFlagsConfig, ProtectionManager protectionManager, MiniMessage miniMessage, Plot plot) {
        this.inventoryActions = inventoryActions;
        this.protectionConfig = protectionConfig;
        this.plotFlagsConfig = plotFlagsConfig;
        this.protectionManager = protectionManager;
        this.miniMessage = miniMessage;
        this.plot = plot;
    }

    public Gui createInventory() {
        String title = this.plotFlagsConfig.title;

        Formatter titleFormat = new Formatter()
            .register("{PLOT_NAME}", this.plot.getName());

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(titleFormat.format(title)))
            .rows(this.plotFlagsConfig.rows)
            .disableAllInteractions()
            .create();

        List<GuiItem> fillerItems = this.plotFlagsConfig.filler.getFiller();
        if (fillerItems != null && !fillerItems.isEmpty()) {
            for (GuiItem fillerItem : fillerItems) {
                gui.getFiller().fill(fillerItem);
            }
        }

        List<GuiItem> borderItems = this.plotFlagsConfig.border.getBorder();
        if (borderItems != null && !borderItems.isEmpty()) {
            for (GuiItem borderItem : borderItems) {
                gui.getFiller().fillBorder(borderItem);
            }
        }

        for (ConfigItem configItem : this.plotFlagsConfig.items.values()) {
            configItem = ConfigItem.replacePlotVariables(configItem, this.plot);
            GuiItem guiItem = new GuiItem(configItem.getItemStack(this.miniMessage));

            this.inventoryActions.setupAction(configItem.getAction(), guiItem, this.plot);

            gui.setItem(configItem.getSlot(), guiItem);
        }

        for (ProtectionConfiguration.ConfigProtection flag : this.getConfigProtections(this.protectionConfig)) {

            if (!flag.isEditInGui()) {
                continue;
            }

            boolean status = isProtection(this.plot, flag.getType());

            PlotFlagsConfiguration.FlagItem flagItem = this.plotFlagsConfig.flagItem.replaceFlagVariables(
                flag.getGuiMaterial(),
                flag.getGuiName(),
                flag.getGuiDescription(),
                status,
                this.plotFlagsConfig.enabledStatus,
                this.plotFlagsConfig.disabledStatus
            );

            ItemStack itemStack = flagItem.getItemStack(this.miniMessage);
            GuiItem guiItem = new GuiItem(itemStack);

            guiItem.setAction(event -> {

                setStatus(this.plot, flag.getType(), !isProtection(this.plot, flag.getType()));
                this.openInventory((Player) event.getWhoClicked());

            });

            gui.addItem(guiItem);
        }


        return gui;
    }

    public void openInventory(Player player) {
        Gui gui = this.createInventory();
        gui.open(player);
    }

    private Collection<ProtectionConfiguration.ConfigProtection> getConfigProtections(ProtectionConfiguration flagsConfig) {
        return flagsConfig.getProtections().values();
    }

    private void setStatus(Plot plot, FlagType flagType, boolean status) {
        this.protectionManager.setProtection(plot, plot.getProtection(), flagType, status);
    }

    private boolean isProtection(Plot plot, FlagType flagType) {
        return this.protectionManager.isProtection(plot, flagType);
    }
}
