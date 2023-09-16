package com.eternalcode.plots.notgood.configuration.implementation.command;

import eu.okaeri.configs.OkaeriConfig;

import java.util.Arrays;
import java.util.List;

public class ConfigCommand extends OkaeriConfig {

    public boolean enabled = true;
    public String name = "commandName";
    public List<String> aliases = Arrays.asList("commandAlias1", "commandAlias2");

    public ConfigCommand(boolean enabled, String name, List<String> aliases) {
        this.enabled = enabled;
        this.name = name;
        this.aliases = aliases;
    }

    public ConfigCommand() {

    }
}
