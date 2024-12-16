package me.mehdidev.rift.commands;

import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends Command {

    /*This is just a placeholder, doesn't actually register under this command name*/
    protected AbstractCommand() {
        super("placeholder");
    }

    @Override
    public String getName() {
        return getClass().getAnnotation(BaseCommand.class).name();
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList(getClass().getAnnotation(BaseCommand.class).aliases());
    }

    public abstract void run(User user, String[] args);

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            this.run(User.getUser(((Player) sender).getUniqueId()), args);
        } else {
            sender.sendMessage("You are not a player bro.");
        }
        return true;
    }

    public void sendUsage(User user) {
        user.send(ChatColor.GRAY + "Usage: /" + ChatColor.AQUA + getName() + ChatColor.GRAY + " " + getUsage());
    }

}