
package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.areas.RiftTasks;
import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.handlers.User;

@BaseCommand(name = "completetask", description = "Give yourself a custom item.", aliases = {"complete"})
public class CommandCompleteTask extends AbstractCommand {

    @Override
    public String getUsage() {
        return "<TASK_ID>";
    }

    @Override
    public void run(User user, String[] args) {
        if (args.length > 0) {
            RiftTasks task = RiftTasks.valueOf(args[0]);
            user.completeTask(task);
            user.ding();
            user.debug("§7Forced task §a" + task.getName() + " §7To be completed!");
        } else {
            sendUsage(user);
        }
    }

}
