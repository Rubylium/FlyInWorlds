package org.ruby.rubymine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.regex.Pattern;

public final class FlyInWorlds extends JavaPlugin implements Listener {

    private final Pattern worldNamePattern = Pattern.compile("spawn-.*");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        String worldName = event.getPlayer().getWorld().getName();
        if (worldNamePattern.matcher(worldName).matches()) {
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().sendMessage(Component.text("Flying enabled! You are now in " + worldName + ".", NamedTextColor.GREEN));
        } else {
            event.getPlayer().setAllowFlight(false);
            event.getPlayer().sendMessage(Component.text("Flying disabled! You have left " + worldName + ".", NamedTextColor.RED));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            String worldName = player.getWorld().getName();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && worldNamePattern.matcher(worldName).matches()) {
                event.setCancelled(true);
                player.sendMessage(Component.text("Fall damage prevented in " + worldName + ".", NamedTextColor.YELLOW));
            }
        }
    }
}