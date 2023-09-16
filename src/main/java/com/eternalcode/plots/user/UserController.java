package com.eternalcode.plots.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserController implements Listener {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = this.userService.findOrCreate(player.getUniqueId());
        user.updateName(player.getName());
    }

}
