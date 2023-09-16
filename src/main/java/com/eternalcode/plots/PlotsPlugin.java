package com.eternalcode.plots;

import com.eternalcode.plots.adventure.LegacyColorProcessor;
import com.eternalcode.plots.command.TestPlotCommand;
import com.eternalcode.plots.plot.PlotService;
import com.eternalcode.plots.region.RegionService;
import com.eternalcode.plots.scheduler.BukkitSchedulerImpl;
import com.eternalcode.plots.scheduler.Scheduler;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PlotsPlugin extends JavaPlugin {

    private BukkitAudiences audienceProvider;
    private MiniMessage miniMessage;
    private Scheduler scheduler;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

        this.scheduler = new BukkitSchedulerImpl(this);


        RegionService regionService = new RegionService(() -> 20, null);
        PlotService plotService = new PlotService(regionService, () -> Duration.ofHours(1), null);

        this.liteCommands = LiteBukkitFactory.builder(server, "eternalplots")
            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("this command only for players"))
            .commandInstance(
                new TestPlotCommand(plotService)
            )
            .register();

/*
        Stream.of(
            new TestingPlotController(plotService)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
*/

        new Metrics(this, 19792);

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Successfully loaded EternalPlots in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
