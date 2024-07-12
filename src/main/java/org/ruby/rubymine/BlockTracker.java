package org.ruby.rubymine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class BlockTracker {
	private final HashMap<UUID, Integer> blockCounts = new HashMap<>();
	private final HashMap<UUID, Long> startTimes = new HashMap<>();
	private final JavaPlugin plugin;

	public BlockTracker(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void startTracking(Player player) {
		UUID playerId = player.getUniqueId();
		blockCounts.put(playerId, 0);
		startTimes.put(playerId, System.currentTimeMillis());
	}

	public boolean isTracking(Player player) {
		return startTimes.containsKey(player.getUniqueId());
	}

	public void incrementBlockCount(Player player) {
		UUID playerId = player.getUniqueId();
		if (!isTracking(player)) {
			startTracking(player);
		}
		blockCounts.put(playerId, blockCounts.getOrDefault(playerId, 0) + 1);
	}

	public double getBlocksPerMinute(Player player) {
		UUID playerId = player.getUniqueId();
		int blockCount = blockCounts.getOrDefault(playerId, 0);
		long startTime = startTimes.getOrDefault(playerId, System.currentTimeMillis());
		long currentTime = System.currentTimeMillis();
		double minutes = (currentTime - startTime) / 60000.0;
		return minutes > 0 ? blockCount / minutes : 0;
	}

	public void startMessageTask() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (UUID playerId : blockCounts.keySet()) {
					Player player = plugin.getServer().getPlayer(playerId);
					if (player != null && blockCounts.get(playerId) > 0) {
						double blocksPerMinute = getBlocksPerMinute(player);
						FlyInWorlds.sendMessage(player, Component.text("You are breaking " + String.format("%.2f", blocksPerMinute) + " blocks per minute.", NamedTextColor.GREEN));
					}
				}
			}
		}.runTaskTimer(plugin, 0L, 200L); // Run every 10 seconds
	}

	public void onPlayerDisconnect(Player player) {
		UUID playerId = player.getUniqueId();
		blockCounts.remove(playerId);
		startTimes.remove(playerId);
	}
}
