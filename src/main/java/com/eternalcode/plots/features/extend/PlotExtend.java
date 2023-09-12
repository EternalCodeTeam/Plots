package com.eternalcode.plots.features.extend;

import com.eternalcode.plots.configuration.implementations.PluginConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.models.ConfigExtend;
import com.eternalcode.plots.hook.VaultProvider;
import com.eternalcode.plots.plot.Plot;
import com.eternalcode.plots.plot.PlotManager;

public class PlotExtend {

    private final PlotManager plotManager;
    private final PluginConfiguration config;
    private final CostsService costsService;
    private final ConfigExtend configExtend;

    private PlotExtend(ConfigExtend configExtend, PlotManager plotManager, PluginConfiguration pluginConfiguration, CostsService costsService) {
        this.plotManager = plotManager;
        this.config = pluginConfiguration;
        this.costsService = costsService;
        this.configExtend = configExtend;
    }

    public static PlotExtend create(Plot plot, VaultProvider vaultProvider, PlotManager plotManager, PluginConfiguration pluginConfiguration) {

        ConfigExtend configExtend = ConfigExtend.getConfigExtend(plot, pluginConfiguration);

        CostsService vault = new CostsVaultService(vaultProvider, pluginConfiguration, configExtend);

        if (configExtend == null) {
            return null;
        }

        if (configExtend.getType() == ExtendType.VAULT) {
            return new PlotExtend(configExtend, plotManager, pluginConfiguration, vault);
        }

        CostsService item = new CostsItemService(configExtend, pluginConfiguration);

        if (configExtend.getType() == ExtendType.ITEM) {
            return new PlotExtend(configExtend, plotManager, pluginConfiguration, item);
        }

        CostsService both = new CostsBothService(pluginConfiguration.plot.extend.noBothMessage, item, vault);

        return new PlotExtend(configExtend, plotManager, pluginConfiguration, both);
    }

    public int getNewSize(Plot plot) {
        return plot.getRegion().getSize() + this.configExtend.blocks;
    }

    public CostsService getCostsService() {
        return this.costsService;
    }

    public boolean isLimit(int size) {
        return this.config.plot.maxSize < size;
    }

    public void extendPlot(Plot plot) {
        this.plotManager.setSize(plot, plot.getRegion().getSize() + this.configExtend.blocks);
        this.plotManager.setExtendLevel(plot, plot.getRegion().getExtendLevel() + 1);
    }

}
