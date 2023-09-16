package com.eternalcode.plots.notgood.gui;

import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotExtendConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigItem;
import com.eternalcode.plots.notgood.feature.extend.PlotExtend;
import com.eternalcode.plots.notgood.hook.VaultProvider;
import com.eternalcode.plots.notgood.plot.old.PlotManager;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.text.Formatter;

import java.util.List;

public class PlotExtendInventory {

    private final VaultProvider vaultProvider;
    private final InventoryActions inventoryActions;
    private final Plot plot;
    private final PluginConfiguration pluginConfiguration;
    private final PlotManager plotManager;
    private final PlotExtendConfiguration plotExtendConfig;
    private final MiniMessage miniMessage;

    public PlotExtendInventory(VaultProvider vaultProvider, InventoryActions inventoryActions, Plot plot, PluginConfiguration pluginConfiguration, PlotManager plotManager, PlotExtendConfiguration plotExtendConfiguration, MiniMessage miniMessage) {
        this.vaultProvider = vaultProvider;
        this.inventoryActions = inventoryActions;
        this.plot = plot;
        this.pluginConfiguration = pluginConfiguration;
        this.plotManager = plotManager;
        this.plotExtendConfig = plotExtendConfiguration;
        this.miniMessage = miniMessage;
    }

    public Gui createInventory() {
        String title = this.plotExtendConfig.title;

        Formatter titleFormat = new Formatter()
            .register("{PLOT_NAME}", this.plot.getName());

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(titleFormat.format(title)))
            .rows(this.plotExtendConfig.rows)
            .disableAllInteractions()
            .create();

        List<GuiItem> fillerItems = this.plotExtendConfig.filler.getFiller();
        if (fillerItems != null && !fillerItems.isEmpty()) {
            for (GuiItem fillerItem : fillerItems) {
                gui.getFiller().fill(fillerItem);
            }
        }

        List<GuiItem> borderItems = this.plotExtendConfig.border.getBorder();
        if (borderItems != null && !borderItems.isEmpty()) {
            for (GuiItem borderItem : borderItems) {
                gui.getFiller().fillBorder(borderItem);
            }
        }

        PlotExtend plotExtend = PlotExtend.create(this.plot, this.vaultProvider, this.plotManager, this.pluginConfiguration);

        Formatter itemFormat = new Formatter()
            .register("{NEW_SIZE}", () -> plotExtend.getNewSize(plot))
            .register("{OLD_SIZE}", () -> this.plot.getRegion().getSize())
            .register("{PRIZE}", () -> plotExtend.getCostsService().getCosts());

        for (ConfigItem configItem : this.plotExtendConfig.items.values()) {
            configItem = ConfigItem.replacePlotVariables(configItem, plot);

            ItemStack itemStack = configItem.getItemStack(this.miniMessage);
            GuiItem guiItem = new GuiItem(itemStack);

            this.inventoryActions.setupAction(configItem.getAction(), guiItem, this.plot);

            gui.setItem(configItem.getSlot(), guiItem);
        }

        ConfigItem configItem = ConfigItem.replacePlotVariables(this.plotExtendConfig.extendItem, this.plot);
        configItem.name = itemFormat.format(configItem.name);
        configItem.lore = configItem.lore.stream()
            .map(itemFormat::format)
            .toList();

        ItemStack itemStack = configItem.getItemStack(this.miniMessage);
        GuiItem guiItem = new GuiItem(itemStack);

        this.inventoryActions.setupAction(this.plotExtendConfig.extendItem.action, guiItem, this.plot);
        gui.setItem(this.plotExtendConfig.extendItem.getSlot(), guiItem);

        return gui;
    }

    public void openInventory(Player player) {
        Gui gui = this.createInventory();
        gui.open(player);
    }
}
