package com.eternalcode.plots.notgood.listener.protection;

import com.eternalcode.plots.notgood.configuration.implementation.ProtectionConfiguration;
import com.eternalcode.plots.notgood.plot.old.protection.ProtectionManager;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.Iterator;

public class BlockFertilizeListener implements Listener {

    private final ProtectionManager protectionManager;
    private final ProtectionConfiguration config;

    public BlockFertilizeListener(ProtectionManager protectionManager, ProtectionConfiguration config) {
        this.protectionManager = protectionManager;
        this.config = config;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFertilize(BlockFertilizeEvent event) {
        if (!config.getGriefing().getFertilize().isProtection()) {
            return;
        }

        if (event.getPlayer() == null) {
            return;
        }

        if (protectionManager.hasBypass(event.getPlayer())) return;

        Iterator<BlockState> it = event.getBlocks().iterator();
        while (it.hasNext()) {
            BlockState blockState = it.next();
            if (this.protectionManager.isAllowed(event.getPlayer(), blockState.getLocation())) {
                continue;
            }
            it.remove();
        }
    }
}
