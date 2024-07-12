package org.ruby.rubymine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class BlockPlaceAndBreakModule implements Listener {

	private final List<String> restrictedWorlds = Arrays.asList("spawn"); // Add your restricted worlds here
	private final BlockTracker blockTracker;

	public BlockPlaceAndBreakModule(BlockTracker blockTracker) {
		this.blockTracker = blockTracker;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!player.isOp() && restrictedWorlds.contains(player.getWorld().getName())) {
			event.setCancelled(true);
			FlyInWorlds.sendMessage(player, Component.text("Block placing is disabled here.", NamedTextColor.RED));
		} else {
			blockTracker.incrementBlockCount(player);
			if (!blockTracker.isTracking(player)) {
				blockTracker.startTracking(player);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!player.isOp() && restrictedWorlds.contains(player.getWorld().getName())) {
			event.setCancelled(true);
			FlyInWorlds.sendMessage(player, Component.text("Block breaking is disabled here.", NamedTextColor.RED));
		} else {
			blockTracker.incrementBlockCount(player);
			if (!blockTracker.isTracking(player)) {
				blockTracker.startTracking(player);
			}
		}
	}
}
