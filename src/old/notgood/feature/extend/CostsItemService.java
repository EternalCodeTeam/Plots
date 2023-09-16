package com.eternalcode.plots.notgood.feature.extend;

import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.model.ConfigExtend;
import com.eternalcode.plots.good.util.ItemUtil;
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
            double playerAmount = ItemUtil.getAmount(player.getInventory(), new ItemStack(entry.getKey()));

            if (playerAmount < neededAmount) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void removeCosts(Player player) {
        for (Map.Entry<Material, Integer> entry : this.configExtend.items.entrySet()) {
            ItemUtil.removeItem(player, entry.getKey(), entry.getValue());
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
