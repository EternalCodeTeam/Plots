package com.eternalcode.plots.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultProvider {

    private Economy economy;
    private final Plugin plugin;

    public VaultProvider(Plugin plugin) {
        this.plugin = plugin;
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }

        this.economy = rsp.getProvider();
    }

    public boolean hasEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        this.economy = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() throws Exception {
        if (!this.hasEconomy()) {
            throw new Exception("Vault is required for EternalPlots to works properly!");
        }

        return this.economy;
    }
}
