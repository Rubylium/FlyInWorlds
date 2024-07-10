package org.ruby.rubymine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectListener implements Listener {

    private final BlockTracker blockTracker;

    public PlayerDisconnectListener(BlockTracker blockTracker) {
        this.blockTracker = blockTracker;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        blockTracker.onPlayerDisconnect(event.getPlayer());
    }
}