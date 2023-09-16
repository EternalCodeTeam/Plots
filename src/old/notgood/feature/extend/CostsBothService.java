package com.eternalcode.plots.notgood.feature.extend;

import org.bukkit.entity.Player;

public class CostsBothService implements CostsService {

    private final String noCostsMessage;
    private final CostsService itemCosts;
    private final CostsService vaultCosts;

    public CostsBothService(String noCostsMessage, CostsService itemCosts, CostsService vaultCosts) {
        this.noCostsMessage = noCostsMessage;
        this.itemCosts = itemCosts;
        this.vaultCosts = vaultCosts;
    }

    @Override
    public boolean hasCosts(Player player) {
        if (!this.itemCosts.hasCosts(player)) {
            return false;
        }

        return this.vaultCosts.hasCosts(player);
    }

    @Override
    public void removeCosts(Player player) {
        this.itemCosts.removeCosts(player);
        this.vaultCosts.removeCosts(player);
    }

    @Override
    public String getCosts() {
        return this.itemCosts.getCosts() + ", " + this.vaultCosts.getCosts();
    }

    @Override
    public String noCostsMessage() {
        return this.noCostsMessage;
    }
}
