package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.implementation.MessageConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotPlayersConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigAction;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.eternalcode.plots.good.notification.NotificationBroadcaster;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.notgood.plot.recoded.member.PlotMember;
import com.eternalcode.plots.notgood.plot.recoded.member.PlotMemberService;
import com.eternalcode.plots.good.user.User;
import com.eternalcode.plots.good.user.UserManager;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;

import java.util.List;

public class PlotMembersInventory {

    private final InventoryActions inventoryActions;
    private final PlotManager plotManager;
    private final PlotPlayersConfiguration plotPlayersConfiguration;
    private final MiniMessage miniMessage;
    private final NotificationBroadcaster notificationBroadcaster;
    private final UserManager userManager;
    private final MessageConfiguration langConfig;
    private final PlotMemberService plotMemberService;

    public PlotMembersInventory(InventoryActions inventoryActions, PlotManager plotManager, PlotPlayersConfiguration plotPlayersConfiguration, MiniMessage miniMessage, NotificationBroadcaster notificationBroadcaster, UserManager userManager, MessageConfiguration langConfig, PlotMemberService plotMemberService) {
        this.inventoryActions = inventoryActions;
        this.plotManager = plotManager;
        this.plotPlayersConfiguration = plotPlayersConfiguration;
        this.miniMessage = miniMessage;
        this.notificationBroadcaster = notificationBroadcaster;
        this.userManager = userManager;
        this.langConfig = langConfig;
        this.plotMemberService = plotMemberService;
    }

    public Gui createInventory(Plot plot) {
        String title = this.plotPlayersConfiguration.title;

        Formatter titleFormat = new Formatter()
            .register("{PLOT_NAME}", plot.getName());

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(titleFormat.format(title)))
            .rows(this.plotPlayersConfiguration.rows)
            .disableAllInteractions()
            .create();

        List<GuiItem> borderItems = this.plotPlayersConfiguration.border.getBorder();

        if (borderItems != null && !borderItems.isEmpty()) {
            for (GuiItem borderItem : borderItems) {
                gui.getFiller().fillBorder(borderItem);
            }
        }

        for (ConfigItem configItem : this.plotPlayersConfiguration.items.values()) {

            configItem = ConfigItem.replacePlotVariables(configItem, plot);
            GuiItem guiItem = new GuiItem(configItem.getItemStack(this.miniMessage));

            this.inventoryActions.setupAction(configItem.getAction(), guiItem, plot);
            gui.setItem(configItem.getSlot(), guiItem);
        }

        PlotPlayersConfiguration.PlayerTemplate playerTemplate = this.plotPlayersConfiguration.playerTemplate;

        for (PlotMember plotMember : this.plotMemberService.getMembers()) {
            User memberUser = plotMember.getUser();

            Formatter formatter = new Formatter()
                .register("{PLAYER}", memberUser.name());

            List<Component> loreFormat = playerTemplate.itemLore.stream()
                .map(lore -> this.miniMessage.deserialize(formatter.format(lore)))
                .toList();

            ItemStack itemStack = ItemBuilder.skull()
                .owner(Bukkit.getOfflinePlayer(memberUser.uuid()))
                .name(this.miniMessage.deserialize(formatter.format(playerTemplate.itemName)))
                .lore(loreFormat)
                .build();

            GuiItem playerGui = getGuiItem(plotMember, itemStack, playerTemplate, plot);

            gui.addItem(playerGui);
        }

        List<GuiItem> fillerItems = this.plotPlayersConfiguration.filler.getFiller();

        if (fillerItems != null && !fillerItems.isEmpty()) {
            for (GuiItem fillerItem : fillerItems) {
                gui.getFiller().fill(fillerItem);
            }
        }

        return gui;
    }

    public void openInventory(Player player, Plot plot) {
        Gui gui = this.createInventory(plot);
        gui.open(player);
    }


    private GuiItem getGuiItem(PlotMember plotMember, ItemStack itemStack, PlotPlayersConfiguration.PlayerTemplate playerTemplate, Plot plot) {
        GuiItem playerGui = new GuiItem(itemStack);

        playerGui.setAction(event -> {

            if (event.getClick().isLeftClick() && playerTemplate.leftClickAction == ConfigAction.KICK_PLAYER) {
                kickPlayer((Player) event.getViewers().get(0), plot, plotMember);
            }

            if (event.getClick().isRightClick() && playerTemplate.rightClickAction == ConfigAction.KICK_PLAYER) {
                kickPlayer((Player) event.getViewers().get(0), plot, plotMember);
            }
        });
        return playerGui;
    }

    private void kickPlayer(Player viewer, Plot plot, PlotMember plotMember) {
        User userSender = this.userManager.findOrCreate(viewer.getUniqueId(), viewer.getName());

        Formatter formatter = new Formatter()
            .register("{PLAYER}", plotMember.getUser().getName())
            .register("{PLOT_NAME}", plot.getName());

        if (!plot.isOwner(userSender)) {
            this.notificationBroadcaster.sendMessage(viewer, formatter.format(this.langConfig.commands.notOwner));
            return;
        }

        if (!this.plotMemberService.isMember(plotMember)) {
            this.notificationBroadcaster.sendMessage(viewer, formatter.format(this.langConfig.commands.notMember));
            return;
        }

        if (plotMember.getUser().equals(userSender)) {
            this.notificationBroadcaster.sendMessage(viewer, formatter.format(this.langConfig.commands.kickOwner));
            return;
        }

        this.plotManager.removeMember(plot, plotMember.getUser());
        this.notificationBroadcaster.sendMessage(viewer, formatter.format(this.langConfig.commands.successKick));
    }
}