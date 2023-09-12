package com.eternalcode.plots.gui;

import com.eternalcode.plots.configuration.ConfigurationManager;
import com.eternalcode.plots.configuration.implementations.LanguageConfiguration;
import com.eternalcode.plots.configuration.implementations.PluginConfiguration;
import com.eternalcode.plots.configuration.implementations.ProtectionConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.PlotExtendConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.PlotFlagsConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.PlotMenuConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.PlotPanelConfiguration;
import com.eternalcode.plots.configuration.implementations.gui.PlotPlayersConfiguration;
import com.eternalcode.plots.hook.VaultProvider;
import com.eternalcode.plots.plot.Plot;
import com.eternalcode.plots.plot.PlotManager;
import com.eternalcode.plots.plot.protection.ProtectionManager;
import com.eternalcode.plots.user.User;
import com.eternalcode.plots.user.UserManager;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class GuiFactory {

    private final VaultProvider vaultProvider;
    private final PlotMenuConfiguration plotSelectorConfig;
    private final ProtectionConfiguration protectionConfig;
    private final PlotFlagsConfiguration plotFlagsConfig;
    private final PlotPanelConfiguration plotPanelConfig;
    private final LanguageConfiguration languageConfig;
    private final PluginConfiguration pluginConfiguration;
    private final PlotExtendConfiguration plotExtendConfig;
    private final ProtectionManager protectionManager;
    private final PlotPlayersConfiguration plotPlayersConfiguration;
    private final GuiActions guiActions;
    private final UserManager userManager;
    private final PlotManager plotManager;
    private final MiniMessage miniMessage;

    public GuiFactory(VaultProvider vaultProvider, ConfigurationManager configurationManager, PlotMenuConfiguration plotSelectorConfig, ProtectionConfiguration protectionConfig, PlotFlagsConfiguration plotFlagsConfig, PlotPanelConfiguration plotPanelConfig, LanguageConfiguration languageConfig, PluginConfiguration pluginConfiguration, PlotExtendConfiguration plotExtendConfig, ProtectionManager protectionManager, PlotPlayersConfiguration plotPlayersConfiguration, UserManager userManager, PlotManager plotManager, MiniMessage miniMessage) {
        this.vaultProvider = vaultProvider;
        this.plotSelectorConfig = plotSelectorConfig;
        this.protectionConfig = protectionConfig;
        this.plotFlagsConfig = plotFlagsConfig;
        this.plotPanelConfig = plotPanelConfig;
        this.languageConfig = languageConfig;
        this.pluginConfiguration = pluginConfiguration;
        this.plotExtendConfig = plotExtendConfig;
        this.protectionManager = protectionManager;
        this.plotPlayersConfiguration = plotPlayersConfiguration;
        this.userManager = userManager;
        this.plotManager = plotManager;
        this.miniMessage = miniMessage;
        this.guiActions = new GuiActions(this, this.vaultProvider, this.plotManager, userManager, configurationManager, plugin);
    }

    public GuiProvider createSelectorGui(User user) {
        return new PlotMenuGui(this.guiActions, this.plotSelectorConfig, this.plotManager, this.miniMessage, user);
    }

    public GuiProvider createPlotPanelGui(Plot plot) {
        return new PlotPanelGui(this.guiActions, this.plotPanelConfig, this.miniMessage, plot);
    }

    public GuiProvider createPlotFlagsGui(Plot plot) {
        return new PlotPanelFlagsGui(this.guiActions, this.protectionConfig, this.plotFlagsConfig, this.protectionManager, this.miniMessage, this, plot);
    }

    public GuiProvider createPlotExtendGui(Plot plot) {
        return new PlotPanelExtendGui(this.vaultProvider, this.guiActions, plot, this.pluginConfiguration, this.plotManager, this.plotExtendConfig, this.miniMessage);
    }

    public GuiProvider createPlotPlayersGui(Plot plot) {
        return new PlotPanelPlayersGui(this.guiActions, this.plotManager, this.plotPlayersConfiguration, this.miniMessage, plot, this.userManager, this.languageConfig);
    }
}