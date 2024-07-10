package org.ruby.rubymine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MineCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player) {

			// Replace these values with your specific world name and coordinates
			String worldName = "beta_mine";
			double x = 180.8;
			double y = -6;
			double z = 35.0;

			World world = Bukkit.getWorld(worldName);
			if (world != null) {
				Location location = new Location(world, x, y, z);
				player.teleport(location);
				FlyInWorlds.sendMessage(player, Component.text("You have been teleported to the mine world.", NamedTextColor.GREEN));
			}
			return true;
		} else {
			sender.sendMessage("Only players can use this command.");
			return true;
		}
	}
}