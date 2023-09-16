package com.eternalcode.plots.notgood.feature.name;

import com.eternalcode.plots.notgood.configuration.implementation.MessageConfiguration;
import com.eternalcode.plots.good.notification.NotificationBroadcaster;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import com.eternalcode.plots.good.util.TextUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import panda.utilities.text.Formatter;

import java.util.Collections;

public class PlotChangeName {

    private final Plot plot;
    private final MessageConfiguration lang;
    private final PlotManager plotManager;
    private final Plugin plugin;
    private final NotificationBroadcaster notificationBroadcaster;

    public PlotChangeName(Plot plot, MessageConfiguration lang, PlotManager plotManager, Plugin plugin, NotificationBroadcaster notificationBroadcaster) {
        this.plot = plot;
        this.lang = lang;
        this.plotManager = plotManager;
        this.plugin = plugin;
        this.notificationBroadcaster = notificationBroadcaster;
    }

    public void sendGui(Player player) {
        AnvilGUI.Builder gui = new AnvilGUI.Builder()
            .text(this.plot.getName())
            .title(this.lang.plotCreation.changeNameInventory.guiTitle)
            .itemLeft(new ItemStack(this.lang.plotCreation.changeNameInventory.guiItem))
            .plugin(this.plugin)

            .onClick((slot, stateSnapshot) -> {
                String inputText = stateSnapshot.getText();

                if (!TextUtil.isLetterOrDigit(inputText)) {
                    this.notificationBroadcaster.sendMessage(player, this.lang.plotCreation.changeNameInventory.illegalCharacters);
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                }

                if (inputText.length() <= 2 || inputText.length() >= 17) {
                    this.notificationBroadcaster.sendMessage(player, this.lang.plotCreation.changeNameInventory.nameTooLongOrShort);
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                }

                if (this.plotManager.isNameBusy(inputText)) {
                    this.notificationBroadcaster.sendMessage(player, this.lang.plotCreation.changeNameInventory.nameExists);
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                }

                this.plotManager.setName(this.plot, inputText);

                Formatter formatter = new Formatter()
                    .register("{NEW_NAME}", this.plot.getName());

                this.notificationBroadcaster.sendMessage(player, formatter.format(this.lang.plotCreation.changeNameInventory.nameChanged));

                return Collections.singletonList(AnvilGUI.ResponseAction.close());
            })

            .onClose(stateSnapshot -> player.sendMessage(this.lang.plotCreation.changeNameInventory.closed));

        gui.open(player);
    }

}
