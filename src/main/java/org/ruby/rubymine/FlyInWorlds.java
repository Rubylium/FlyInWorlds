package org.ruby.rubymine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.regex.Pattern;

public final class FlyInWorlds extends JavaPlugin implements Listener {

	private final Pattern worldNamePattern = Pattern.compile("^(spawn|beta_mine)$");
	private final BlockTracker blockTracker = new BlockTracker(this);

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Objects.requireNonNull(this.getCommand("flyinworldsreload")).setExecutor(new ReloadCommand(this));
		Objects.requireNonNull(this.getCommand("pmine")).setExecutor(new MineCommand());
		Bukkit.getPluginManager().registerEvents(new BlockPlaceAndBreakModule(blockTracker), this);
		getServer().getPluginManager().registerEvents(new PlayerDisconnectListener(blockTracker), this);
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		String worldName = event.getPlayer().getWorld().getName();
		if (worldNamePattern.matcher(worldName).matches()) {
			event.getPlayer().setAllowFlight(true);
			sendMessage(event.getPlayer(), Component.text("Flying enabled! You are now in " + worldName + ".", NamedTextColor.GREEN));

		} else {
			event.getPlayer().setAllowFlight(false);
			sendMessage(event.getPlayer(), Component.text("Flying disabled! You are now in " + worldName + ".", NamedTextColor.RED));
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player player) {
			String worldName = player.getWorld().getName();
			if (event.getCause() == EntityDamageEvent.DamageCause.FALL && worldNamePattern.matcher(worldName).matches()) {
				event.setCancelled(true);
				sendMessage(player, Component.text("You are in " + worldName + ", fall damage is disabled.", NamedTextColor.GREEN));
			}
		}
	}

	public static void sendMessage(Player player, Component message) {
	    Component prefix = Component.text("RUBY", NamedTextColor.RED).decoration(TextDecoration.BOLD, true)
	            .append(Component.text("PRISON", NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true))
	            .append(Component.text(" >> ", NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false));
	    // Reset bold for the message component
	    Component formattedMessage = message.decoration(TextDecoration.BOLD, false);
	    player.sendMessage(prefix.append(formattedMessage));
	}
}
