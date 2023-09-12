package com.eternalcode.plots.features.extend;

import com.eternalcode.plots.configuration.implementations.PluginConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.models.ConfigExtend;
import com.eternalcode.plots.utils.ItemUtils;
import com.eternalcode.plots.adventure.LegacyUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CostsItemService implements CostsService {

    private final ConfigExtend configExtend;
    private final String lineOfNeededItem;
    private final String lineOfNeededItemDeclaimer;
    private final String noCostsMessage;

    public CostsItemService(ConfigExtend configExtend, PluginConfiguration pluginConfiguration) {
        this.configExtend = configExtend;
        this.noCostsMessage = pluginConfiguration.plot.extend.noItemsMessage;
        this.lineOfNeededItem = pluginConfiguration.plot.extend.itemFormat;
        this.lineOfNeededItemDeclaimer = pluginConfiguration.plot.extend.joinerFormat;
    }

    @Override
    public boolean hasCosts(Player player) {
        for (Map.Entry<Material, Integer> entry : this.configExtend.items.entrySet()) {
            double neededAmount = entry.getValue();
            double playerAmount = ItemUtils.getAmount(player.getInventory(), new ItemStack(entry.getKey()));

            if (playerAmount < neededAmount) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void removeCosts(Player player) {
        for (Map.Entry<Material, Integer> entry : this.configExtend.items.entrySet()) {
            ItemUtils.removeItems(player.getInventory(), entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getCosts() {
        List<String> items = new ArrayList<>();

        for (Map.Entry<Material, Integer> entry : this.configExtend.items.entrySet()) {

            String line = this.lineOfNeededItem
                .replace("{AMOUNT}", String.valueOf(entry.getValue()))
                .replace("{ITEM}", String.valueOf(entry.getKey()));

            items.add(line);
        }

        return String.join(this.lineOfNeededItemDeclaimer, items);
    }

    @Override
    public String noCostsMessage() {
        return LegacyUtils.color(this.noCostsMessage);
    }

}
