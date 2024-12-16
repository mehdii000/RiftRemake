package me.mehdidev.rift;

import me.mehdidev.rift.commands.CommandItem;
import me.mehdidev.rift.commands.CommandRegistry;
import me.mehdidev.rift.commands.CommandRift;
import me.mehdidev.rift.items.core.ItemListener;
import me.mehdidev.rift.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Rift extends JavaPlugin {

    /*
    * TODO:
    * A Custom commands system [DONE]
    * Complete the gui system [KINDA DONE]
    * Implement a custom items system with abilities and nbt data manipulation [NOT DONE]
    * Implement a custom entity system to create cool monsters with custom abilities. [NOT DONE]
    * Make a custom npc's system (This is splitted into two parts) [NOT DONE]
    *  - First: a system that turns a zombie for example into an npc using nms to give the illusion of fighting a player entity [NOT DONE]
    *  - Second: an interactble npc system done mostly in NMS, similair to citizens, (why not just use citizens you say) cause i like the pain. [NOT DONE]
    * Simple stats system (HP/Mana/) [NOT DONE]
    * implement a world instancing system with the rift map (hypixel dont sue me) [NOT DONE]
    * VAMPIRE SLAYER? [OH BOY]
    * */


    private static Rift rift;

    private final CommandRegistry commandRegistry = new CommandRegistry();
    public CommandMap commandMap;

    public static Rift getRift() {
        return rift;
    }

    @Override
    public void onEnable() {
        rift = this;
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
    }

    private void handleListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    private void handleCommands() {
        commandRegistry.registerCommand(new CommandRift());
        commandRegistry.registerCommand(new CommandItem());
        commandRegistry.handleMapping();
    }

}
