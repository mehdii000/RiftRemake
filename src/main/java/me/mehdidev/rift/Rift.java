package me.mehdidev.rift;

import me.mehdidev.rift.commands.CommandRegistry;
import me.mehdidev.rift.commands.impl.*;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.items.core.ItemListener;
import me.mehdidev.rift.listeners.InventoryListener;
import me.mehdidev.rift.listeners.PlayerListener;
import me.mehdidev.rift.listeners.WorldListener;
import me.mehdidev.rift.stats.RiftStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Rift extends JavaPlugin {

    /*
    * TODO:
    * A Custom commands system [DONE]
    * Complete the gui system [KINDA DONE]
    * * Simple stats system (HP/Mana/) [KINDA DONE]
    * Implement a custom items system with abilities and nbt data manipulation [DONE]
    * Implement a custom entity system to create cool monsters with custom abilities. [NOT DONE]
    * Make a custom npc's system (This is splitted into two parts) [NOT DONE]
    *  - First: a system that turns a zombie for example into an npc using nms to give the illusion of fighting a player entity [NOT DONE]
    *  - Second: an interactble npc system done mostly in NMS, similair to citizens, (why not just use citizens you say) cause i like the pain. [NOT DONE]
    * VAMPIRE SLAYER? [OH BOY]
    *
    *
    * IMPLEMENT NPCS
    * IMPORTANT: MAKE A SIMPLE VAMPIRE SLAYER GUI
    * */


    private static Rift rift;

    private final CommandRegistry commandRegistry = new CommandRegistry();
    public CommandMap commandMap;
    public RiftStats riftStats;

    public static Rift getRift() {
        return rift;
    }

    @Override
    public void onEnable() {
        rift = this;
        riftStats = new RiftStats();
        try
        {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        }
        catch (IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }

        handleListeners();
        handleCommands();

        for (Player online : Bukkit.getOnlinePlayers()) {
            riftStats.updateStats(User.getUser(online.getUniqueId()));
            User.getUser(online.getUniqueId()).load();
            online.sendMessage(ChatColor.GREEN + "Updated internal rift stats!");
        }
    }

    @Override
    public void onDisable() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            riftStats.detachUserStats(User.getUser(online.getUniqueId()));
            User.getUser(online.getUniqueId()).save();
        }
    }

    private void handleListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    private void handleCommands() {
        commandRegistry.registerCommand(new CommandRift());
        commandRegistry.registerCommand(new CommandStat());
        commandRegistry.registerCommand(new CommandAddStatMod());
        commandRegistry.registerCommand(new CommandRemoveStatMod());
        commandRegistry.registerCommand(new CommandItem());
        commandRegistry.registerCommand(new CommandCompleteTask());
        commandRegistry.handleMapping();
    }

}
