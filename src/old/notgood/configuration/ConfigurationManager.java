package com.eternalcode.plots.notgood.configuration;

import com.eternalcode.plots.notgood.configuration.implementation.BlocksConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.MessageConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.PluginConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.command.CommandsConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotExtendConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotFlagsConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotMenuConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotPanelConfiguration;
import com.eternalcode.plots.notgood.configuration.implementation.gui.PlotPlayersConfiguration;
import com.eternalcode.plots.notgood.configuration.serializer.ConfigExtendSerializer;
import com.eternalcode.plots.notgood.configuration.serializer.ConfigItemSerializer;
import com.eternalcode.plots.notgood.configuration.serializer.PlotSectionSerializer;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.postprocessor.SectionSeparator;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;

import java.io.File;

// TODO: Refactor configuration, and configuration files, for less use on inner classes in configuration classes.
public class ConfigurationManager {

    private final PluginConfiguration pluginConfiguration;
    private final PlotPanelConfiguration plotPanelConfiguration;
    private final PlotMenuConfiguration plotMenuConfiguration;
    private final BlocksConfiguration blocksConfiguration;
    private final MessageConfiguration messageConfiguration;
    private final ProtectionConfiguration protectionConfiguration;
    private final PlotFlagsConfiguration plotFlagsConfiguration;
    private final CommandsConfiguration commandsConfiguration;
    private final PlotExtendConfiguration plotExtendConfiguration;
    private final PlotPlayersConfiguration plotPlayersConfiguration;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfiguration = (PluginConfiguration) loadAndRender(PluginConfiguration.class, dataFolder, "config.yml");
        this.blocksConfiguration = (BlocksConfiguration) loadAndRender(BlocksConfiguration.class, dataFolder, "blocks.yml");
        this.protectionConfiguration = (ProtectionConfiguration) loadAndRender(ProtectionConfiguration.class, dataFolder, "protection.yml");
        this.commandsConfiguration = (CommandsConfiguration) loadAndRender(CommandsConfiguration.class, dataFolder, "commands.yml");
        this.plotPanelConfiguration = (PlotPanelConfiguration) loadAndRender(PlotPanelConfiguration.class, dataFolder, "gui/plot-panel.yml");
        this.plotMenuConfiguration = (PlotMenuConfiguration) loadAndRender(PlotMenuConfiguration.class, dataFolder, "gui/plot-menu.yml");
        this.plotFlagsConfiguration = (PlotFlagsConfiguration) loadAndRender(PlotFlagsConfiguration.class, dataFolder, "gui/plot-flags.yml");
        this.plotExtendConfiguration = (PlotExtendConfiguration) loadAndRender(PlotExtendConfiguration.class, dataFolder, "gui/plot-extend.yml");
        this.plotPlayersConfiguration = (PlotPlayersConfiguration) loadAndRender(PlotPlayersConfiguration.class, dataFolder, "gui/plot-players.yml");
        this.messageConfiguration = (MessageConfiguration) loadAndRender(MessageConfiguration.class, dataFolder, "lang/messages.yml");
    }

    public <T extends OkaeriConfig> OkaeriConfig loadAndRender(Class<T> config, File dataFolder, String filePath) {

        SerdesRegistry serdesRegistry = new SerdesRegistry();
        serdesRegistry.register(new ConfigItemSerializer());
        serdesRegistry.register(new PlotSectionSerializer());
        serdesRegistry.register(new ConfigExtendSerializer());

        return ConfigManager.create(config)
            .withConfigurer(new YamlSnakeYamlConfigurer("# ", SectionSeparator.NEW_LINE))
            .withSerdesPack(serdesRegistry.allSerdes())
            .withBindFile(dataFolder + "/" + filePath)
            .saveDefaults()
            .load(true);
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public PlotPanelConfiguration getPlotPanelConfiguration() {
        return this.plotPanelConfiguration;
    }

    public PlotMenuConfiguration getPlotMenuConfiguration() {
        return this.plotMenuConfiguration;
    }

    public BlocksConfiguration getBlocksConfiguration() {
        return this.blocksConfiguration;
    }

    public MessageConfiguration getLanguageConfiguration() {
        return this.messageConfiguration;
    }

    public ProtectionConfiguration getProtectionConfiguration() {
        return this.protectionConfiguration;
    }

    public PlotFlagsConfiguration getPlotFlagsConfiguration() {
        return this.plotFlagsConfiguration;
    }

    public CommandsConfiguration getCommandsConfiguration() {
        return this.commandsConfiguration;
    }

    public PlotExtendConfiguration getPlotExtendConfiguration() {
        return this.plotExtendConfiguration;
    }

    public PlotPlayersConfiguration getPlotPlayersConfiguration() {
        return this.plotPlayersConfiguration;
    }
}
