package me.mehdidev.rift.areas;

import org.bukkit.ChatColor;

import java.util.List;

public interface RiftArea {

    ChatColor getColor();
    String getDescription();
    List<RiftTasks> getTasks();

}
