
package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.handlers.ConfigUtils;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.stats.DoubleStatValue;
import me.mehdidev.rift.stats.RiftStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaseCommand(name = "stat", description = "Give yourself a custom item.", aliases = {"stats", "getstats", "getstat"})
public class CommandStat extends AbstractCommand {

    @Override
    public String getUsage() {
        return "<STAT_ID>>";
    }

    @Override
    public void run(User user, String[] args) {
        if (args.length > 0) {
            DoubleStatValue statValue = Rift.getRift().riftStats.getStat(user, RiftStat.valueOf(args[0]));
            user.send("§7Your " + statValue.getType().getColor() + statValue.getType().getSymbol() + " " + statValue.getType().getName() + " §7Has value of §a" + statValue.getValue());
        } else {
            sendUsage(user);
        }
    }

    @Override
    public Map<Integer, List<String>> mapArgumentToCompletion() {
        Map<Integer, List<String>> argsMap = new HashMap<>();
        argsMap.put(1, ConfigUtils.getEnumNames(RiftStat.class));
        return argsMap;
    }

}
