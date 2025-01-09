package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.guis.impl.GuiRift;
import me.mehdidev.rift.handlers.User;

@BaseCommand(name = "rift", description = "The main command of this plugin.", aliases = {"rft"})
public class CommandRift extends AbstractCommand {

    @Override
    public void run(User user, String[] args) {
        user.openGUI(new GuiRift(user));
    }
}
