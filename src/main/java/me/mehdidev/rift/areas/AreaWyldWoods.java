package me.mehdidev.rift.areas;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class AreaWyldWoods implements RiftArea {

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public String getDescription() {
        return "Home of the " + getColor() + "Argofays" + ChatColor.GRAY + ", No one knows how they got there, but they do like visits from strangers.";
    }

    @Override
    public List<RiftTasks> getTasks() {
        return Arrays.asList(
                RiftTasks.HELP_BUGHUNTER,
                RiftTasks.TALK_TO_JAQUELLE,
                RiftTasks.TALK_TO_POHRTAL,
                RiftTasks.SKIP_TASKS
        );
    }

}
