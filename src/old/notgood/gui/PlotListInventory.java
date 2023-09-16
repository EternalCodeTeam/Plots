package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotMenuConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigAction;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.good.user.User;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.util.SkullUtil;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PlotListInventory {

    private final InventoryActions inventoryActions;
    private final PlotMenuConfiguration plotSelectorConfig;
    private final PlotManager plotManager;
    private final MiniMessage miniMessage;
    private final User user;

    public PlotListInventory(InventoryActions inventoryActions, PlotMenuConfiguration plotSelectorConfig, PlotManager plotManager, MiniMessage miniMessage, User user) {
        this.inventoryActions = inventoryActions;
        this.plotSelectorConfig = plotSelectorConfig;
        this.plotManager = plotManager;
        this.miniMessage = miniMessage;
        this.user = user;
    }

    public Gui createInventory() {
        return createInventory(0);
    }

    public Gui createInventory(int page) {
        if (page < 0) page = 0;

        int plotsInGui = this.plotSelectorConfig.plotsPlaces.size();

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.plotSelectorConfig.title))
            .rows(this.plotSelectorConfig.rows)
            .disableAllInteractions()
            .create();

        List<GuiItem> fillerItems = this.plotSelectorConfig.filler.getFiller();
        if (fillerItems != null && !fillerItems.isEmpty()) {
            for (GuiItem fillerItem : fillerItems) {
                gui.getFiller().fill(fillerItem);
            }
        }

        List<GuiItem> borderItems = this.plotSelectorConfig.border.getBorder();
        if (borderItems != null && !borderItems.isEmpty()) {
            for (GuiItem borderItem : borderItems) {
                gui.getFiller().fillBorder(borderItem);
            }
        }

        Set<Plot> userPlots = this.plotManager.getPlots(user);

        /* Items */

        int finalPage = page;

        for (ConfigItem configItem : this.plotSelectorConfig.items.values()) {
            GuiItem guiItem = new GuiItem(configItem.getItemStack(this.miniMessage));

            if (configItem.getAction() == ConfigAction.PREVIOUS_PAGE) {
                guiItem.setAction(event -> {
                    if (userPlots.isEmpty()) {
                        return;
                    }
                    if ((finalPage - 1) * plotsInGui < userPlots.size()) {
                        createInventory(finalPage - 1).open(event.getWhoClicked());
                    }
                });
            }

            if (configItem.getAction() == ConfigAction.NEXT_PAGE) {
                guiItem.setAction(event -> {
                    if (userPlots.isEmpty()) {
                        return;
                    }
                    if ((finalPage + 1) * plotsInGui < userPlots.size()) {
                        createInventory(finalPage + 1).open(event.getWhoClicked());
                    }
                });
            }

            if (configItem.getAction() == ConfigAction.CLOSE) {
                guiItem.setAction(event -> event.getWhoClicked().closeInventory());
            }
            gui.setItem(configItem.getSlot(), guiItem);
        }

        if (userPlots.isEmpty()) {
            return gui;
        }

        /* Plot Section */

        List<Plot> plots = new ArrayList<>(userPlots);
        int i = (page * plotsInGui);

        for (PlotMenuConfiguration.PlotSection plotSection : this.plotSelectorConfig.plotsPlaces.values()) {

            if (i >= userPlots.size()) {
                break;
            }

            Plot plot = plots.get(i);

            for (ConfigItem configItem : Arrays.asList(plotSection.optionsItem, plotSection.plotItem, plotSection.teleportItem)) {

                if (configItem == null) {
                    continue;
                }

                ConfigItem item = new ConfigItem(configItem.getSlot(), configItem.getMaterial(), configItem.getName(), configItem.getLore(), configItem.getAction());

                /* Create ItemStack */

                item = ConfigItem.replacePlotVariables(item, plot);
                ItemStack itemStack = item.getItemStack(this.miniMessage);

                /* Head Texture */

                if (SkullUtil.isPlayerSkull(itemStack)) {
                    itemStack = ItemBuilder.skull(itemStack)
                        .owner(Bukkit.getOfflinePlayer(plot.getOwner().getUser().getUuid()))
                        .build();
                }

                /* Create GuiItem */

                GuiItem guiItem = ItemBuilder.from(itemStack)
                    .name(miniMessage.deserialize(item.getName()).decoration(TextDecoration.ITALIC, false))
                    .asGuiItem();

                /* Create Events */

                this.inventoryActions.setupAction(configItem.getAction(), guiItem, plot);

                /* Add to Gui */

                gui.setItem(item.getSlot(), guiItem);
            }

            i++; // get new plot
        }

        return gui;
    }

    public void open(Player player) {
        Gui gui = this.createInventory();
        gui.open(player);
    }
}
