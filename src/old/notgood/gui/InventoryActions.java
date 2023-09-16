package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.ConfigurationManager;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigAction;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigExtend;
import com.eternalcode.plots.notgood.feature.extend.CostsService;
import com.eternalcode.plots.notgood.feature.extend.PlotExtend;
import com.eternalcode.plots.notgood.feature.name.PlotChangeName;
import com.eternalcode.plots.notgood.hook.VaultProvider;
import com.eternalcode.plots.good.notification.NotificationBroadcaster;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.good.position.PositionAdapter;
import com.eternalcode.plots.good.user.User;
import com.eternalcode.plots.good.user.UserManager;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import io.papermc.lib.PaperLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class InventoryActions {

    private final VaultProvider vaultProvider;
    private final PlotManager plotManager;
    private final UserManager userManager;
    private final ConfigurationManager configurationManager;
    private final Plugin plugin;
    private final NotificationBroadcaster notificationBroadcaster;

    private final PlotMembersInventory plotMembersInventory;
    private final PlotExtendInventory plotExtendInventory;
    private final PlotFlagInventory plotFlagInventory;
    private final PlotPanelInventory plotPanelInventory;

    public InventoryActions(VaultProvider vaultProvider, PlotManager plotManager, UserManager userManager, ConfigurationManager configurationManager, Plugin plugin, NotificationBroadcaster notificationBroadcaster, PlotMembersInventory plotMembersInventory, PlotExtendInventory plotExtendInventory, PlotFlagInventory plotFlagInventory, PlotPanelInventory plotPanelInventory) {
        this.vaultProvider = vaultProvider;
        this.plotManager = plotManager;
        this.userManager = userManager;
        this.configurationManager = configurationManager;
        this.plugin = plugin;
        this.notificationBroadcaster = notificationBroadcaster;
        this.plotMembersInventory = plotMembersInventory;
        this.plotExtendInventory = plotExtendInventory;
        this.plotFlagInventory = plotFlagInventory;
        this.plotPanelInventory = plotPanelInventory;
    }

    public void setupAction(ConfigAction action, GuiItem guiItem, Plot plot) {
        guiItem.setAction(getAction(action, plot));
    }

    GuiAction<InventoryClickEvent> getAction(ConfigAction action, Plot plot) {
        return switch (action) {

            /* All */
            case NONE -> null;
            case CLOSE -> this.closeGui();
            case KICK_PLAYER -> null;

            /* Plot Selector */
            case PREVIOUS_PAGE, NEXT_PAGE -> null;

            /* Menus */
            case PLOT_MENU -> this.openPlotPanel(plot);
            case NAME_MENU -> this.openChangeNamePanel(plot);
            case FLAGS_MENU -> this.openFlagsPanel(plot);
            case EXTEND_MENU -> this.openExtendPanel(plot);
            case PLAYERS_MENU -> this.openPlayersPanel(plot);

            /* Plot Functions */
            case TELEPORT_PLOT -> this.teleportPlot(plot);
            case EXTEND_PLOT -> this.extendPlot(plot);
        };
    }

    private GuiAction<InventoryClickEvent> openPlayersPanel(Plot plot) {
        return event -> {

            Player player = (Player) event.getWhoClicked();
            User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName());

            if (!plot.isOwner(user)) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Nie jesteś właścicielem działki");
                return;
            }

            this.plotMembersInventory.openInventory(player, plot);
        };
    }

    private GuiAction<InventoryClickEvent> extendPlot(Plot plot) {
        return event -> {

            ConfigExtend configExtend = ConfigExtend.getConfigExtend(plot, this.configurationManager.getPluginConfiguration());

            if (configExtend == null) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Osiągnąłeś już limit ulepszeń działki");
                return;
            }

            int newSize = plot.getRegion().getSize() + configExtend.blocks;
            Player player = (Player) event.getWhoClicked();

            PlotExtend plotExtend = PlotExtend.create(plot, this.vaultProvider, this.plotManager, this.configurationManager.getPluginConfiguration());

            if (plotExtend == null) {
                player.sendMessage(ChatColor.RED + "Limit wielkości działki został już osiągnięty");
                return;
            }

            CostsService costsService = plotExtend.getCostsService();

            if (plotExtend.isLimit(newSize)) {
                player.sendMessage(ChatColor.RED + "Limit wielkości działki został już osiągnięty");
                return;
            }

            if (!costsService.hasCosts(player)) {
                player.sendMessage(costsService.noCostsMessage());
                return;
            }

            costsService.removeCosts(player);
            plotExtend.extendPlot(plot);

            player.sendMessage(ChatColor.GREEN + "Powiększono działkę na " + newSize);

            PlotExtend plotExtendAfter = PlotExtend.create(plot, this.vaultProvider, this.plotManager, this.configurationManager.getPluginConfiguration());

            if (plotExtendAfter == null) {
                player.closeInventory();
                return;
            }

            this.plotExtendInventory.openInventory(player);
        };
    }

    private GuiAction<InventoryClickEvent> teleportPlot(Plot plot) {
        return event -> {

            Player player = (Player) event.getWhoClicked();

            PaperLib.teleportAsync(player, PositionAdapter.convert(plot.getRegion().getCenter()).add(
                0.5,
                1,
                0.5
            ));
        };
    }

    private GuiAction<InventoryClickEvent> openFlagsPanel(Plot plot) {
        return event -> {

            Player player = (Player) event.getWhoClicked();
            User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName());

            if (!plot.isOwner(user)) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Nie jesteś właścicielem działki");
                return;
            }

            this.plotFlagInventory.openInventory(player);
        };
    }

    private GuiAction<InventoryClickEvent> openExtendPanel(Plot plot) {
        return event -> {

            Player player = (Player) event.getWhoClicked();
            User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName());

            if (!plot.isOwner(user)) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Nie jesteś właścicielem działki");
                return;
            }

            if (ConfigExtend.getConfigExtend(plot, this.configurationManager.getPluginConfiguration()) == null) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Osiągnąłeś już limit ulepszeń działki");
                return;
            }

            this.plotExtendInventory.openInventory(player);
        };
    }

    private GuiAction<InventoryClickEvent> openChangeNamePanel(Plot plot) {
        return event -> {

            Player player = (Player) event.getWhoClicked();
            User user = this.userManager.findOrCreate(player.getUniqueId(), player.getName());

            if (!plot.isOwner(user)) {
                player.sendMessage(ChatColor.RED + "Nie jesteś właścicielem działki");
                return;
            }

            new PlotChangeName(plot, this.configurationManager.getLanguageConfiguration(), this.plotManager, this.plugin, this.notificationBroadcaster)
                .sendGui((Player) event.getWhoClicked());
        };
    }

    private GuiAction<InventoryClickEvent> openPlotPanel(Plot plot) {
        return event -> this.plotPanelInventory.open((Player) event.getWhoClicked(), plot);
    }

    private GuiAction<InventoryClickEvent> closeGui() {
        return event -> event.getWhoClicked().closeInventory();
    }
}
