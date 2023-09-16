package com.eternalcode.plots.notgood.configuration.implementation.command;

import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class CommandsConfiguration extends OkaeriConfig {

    @Comment({ "DO NOT REMOVE ANY COMMAND! ONLY EDIT!", "", "Main name command available in variable {PLOT_CMD}" })
    public String commandUsage = "&c{USAGE}";

    public ConfigCommand plotCommand = new ConfigCommand(true, "plot", Arrays.asList("p", "plots"));

    @Comment()
    public Map<String, ConfigCommand> subcommands = new ImmutableMap.Builder<String, ConfigCommand>()

        .put("panel", new ConfigCommand(true, "panel", new ArrayList<>()))
        .put("border", new ConfigCommand(true, "border", new ArrayList<>()))
        .put("invite", new ConfigCommand(true, "invite", new ArrayList<>()))
        .put("kick", new ConfigCommand(true, "kick", new ArrayList<>()))
        .put("join", new ConfigCommand(true, "join", new ArrayList<>()))
        .put("reject", new ConfigCommand(true, "reject", new ArrayList<>()))
        .put("delete", new ConfigCommand(true, "delete", new ArrayList<>()))

        .build();
}
