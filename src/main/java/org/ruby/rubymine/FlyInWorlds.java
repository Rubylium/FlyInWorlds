package org.ruby.rubymine;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public final class FlyInWorlds extends JavaPlugin implements Listener {

    private final Pattern worldNamePattern = Pattern.compile("spawn-.*"); // Replace with your regex

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        String worldName = event.getPlayer().getWorld().getName();
        if (worldNamePattern.matcher(worldName).matches()) {
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Flying enabled! You are now in " + worldName + ".");
        } else {
            event.getPlayer().setAllowFlight(false);
            event.getPlayer().sendMessage(ChatColor.RED + "Flying disabled! You have left " + worldName + ".");
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String worldName = player.getWorld().getName();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && worldNamePattern.matcher(worldName).matches()) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.YELLOW + "Fall damage prevented in " + worldName + ".");
            }
        }
    }
}
