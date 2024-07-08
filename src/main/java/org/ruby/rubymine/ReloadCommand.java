package org.ruby.rubymine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ReloadCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("flyinworldsreload")) {
            plugin.reloadConfig();
            sender.sendMessage(Component.text("Configuration reloaded.", NamedTextColor.GREEN));
            // Add any additional reload logic here
            return true;
        }
        return false;
    }
}