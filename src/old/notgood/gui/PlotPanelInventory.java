package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotPanelConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.eternalcode.plots.notgood.VariablesUtils;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.List;

public class PlotPanelInventory {

    private final InventoryActions inventoryActions;
    private final PlotPanelConfiguration plotPanelConfig;
    private final MiniMessage miniMessage;

    public PlotPanelInventory(InventoryActions inventoryActions, PlotPanelConfiguration plotPanelConfig, MiniMessage miniMessage) {
        this.inventoryActions = inventoryActions;
        this.plotPanelConfig = plotPanelConfig;
        this.miniMessage = miniMessage;
    }

    public Gui createInventory(Plot plot) {
        String title = this.plotPanelConfig.title;

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(VariablesUtils.parsePlotVars(plot, title)))
            .rows(this.plotPanelConfig.rows)
            .disableAllInteractions()
            .create();

        List<GuiItem> fillerItems = this.plotPanelConfig.filler.getFiller();
        if (fillerItems != null && !fillerItems.isEmpty()) {
            for (GuiItem fillerItem : fillerItems) {
                gui.getFiller().fill(fillerItem);
            }
        }

        List<GuiItem> borderItems = this.plotPanelConfig.border.getBorder();
        if (borderItems != null && !borderItems.isEmpty()) {
            for (GuiItem borderItem : borderItems) {
                gui.getFiller().fillBorder(borderItem);
            }
        }

        /* Items */
        for (ConfigItem configItem : this.plotPanelConfig.items.values()) {

            configItem = ConfigItem.replacePlotVariables(configItem, plot);
            GuiItem guiItem = new GuiItem(configItem.getItemStack(this.miniMessage));

            this.inventoryActions.setupAction(configItem.getAction(), guiItem, plot);

            gui.setItem(configItem.getSlot(), guiItem);
        }

        return gui;
    }

    public void open(Player player, Plot plot) {
        this.createInventory(plot).open(player);
    }
}
