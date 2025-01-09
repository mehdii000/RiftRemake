package me.mehdidev.rift.listeners;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = User.getUser(event.getPlayer().getUniqueId());
        user.load();
        user.send(ChatColor.LIGHT_PURPLE + "Welcome to a world of infinite creations!");
        Rift.getRift().riftStats.updateStats(user);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        User.getUser(event.getPlayer().getUniqueId()).updateStats();
    }

    @EventHandler
    public void onPlayerSwitchWorlds(PlayerChangedWorldEvent event) {
        User user = User.getUser(event.getPlayer().getUniqueId());
        user.save();
        user.load();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        User user = User.getUser(event.getPlayer().getUniqueId());
        Rift.getRift().riftStats.detachUserStats(user);
        user.save();
    }

}
