package me.mehdidev.rift.handlers;

import org.bukkit.Bukkit;

public class DebugHandler {

    public static void log(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage("[Rift@Debug] " + message);
    }

}
