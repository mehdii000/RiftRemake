package me.mehdidev.rift.commands;

import me.mehdidev.rift.guis.RiftGuis;
import me.mehdidev.rift.handlers.User;
import org.bukkit.entity.Player;

@BaseCommand(name = "rift", description = "The main command of this plugin.", aliases = {"rft"})
public class CommandRift extends AbstractCommand {

    @Override
    public void run(User user, String[] args) {
        user.openGUI(RiftGuis.RIFT_GUI);
    }
}
