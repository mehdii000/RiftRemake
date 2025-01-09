
package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.handlers.ConfigUtils;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.stats.RiftStat;
import me.mehdidev.rift.stats.StatModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaseCommand(name = "removestatmodifer", description = "Adds a stat modifier to yourself", aliases = {"removestat", "removestatmod"})
public class CommandRemoveStatMod extends AbstractCommand {

    @Override
    public String getUsage() {
        return "<STAT_TYPE> <MODIFICATION_REASON>";
    }

    @Override
    public void run(User user, String[] args) {
        if (args.length > 0) {
            user.removeStatModifiersWithReason(RiftStat.valueOf(args[0]), StatModifier.ModificationReason.valueOf(args[1]));
        } else {
            sendUsage(user);
        }
    }

    @Override
    public Map<Integer, List<String>> mapArgumentToCompletion() {
        Map<Integer, List<String>> argsMap = new HashMap<>();
        argsMap.put(1, ConfigUtils.getEnumNames(RiftStat.class));
        argsMap.put(2, ConfigUtils.getEnumNames(StatModifier.ModificationReason.class));
        return argsMap;
    }

}
