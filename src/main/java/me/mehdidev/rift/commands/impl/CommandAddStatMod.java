
package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.handlers.ConfigUtils;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.stats.DoubleStatValue;
import me.mehdidev.rift.stats.RiftStat;
import me.mehdidev.rift.stats.StatModifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaseCommand(name = "addstatmodifer", description = "Adds a stat modifier to yourself", aliases = {"addstat", "addstatmod"})
public class CommandAddStatMod extends AbstractCommand {

    @Override
    public String getUsage() {
        return "<STAT_TYPE> <ADD/MULTIPLY/OVERRIDE> <VALUE>";
    }

    @Override
    public void run(User user, String[] args) {
        if (args.length > 0) {
            StatModifier modifer = new StatModifier(RiftStat.valueOf(args[0]), StatModifier.ModificationType.valueOf(args[1]), Double.parseDouble(args[2]));
            user.addStatModifier(modifer);
            DoubleStatValue statValue = Rift.getRift().riftStats.getStat(user, RiftStat.valueOf(args[0]));
            user.send("§7Your " + statValue.getType().getColor() + statValue.getType().getSymbol() + " " + statValue.getType().getName() + " §7Has value of §a" + statValue.getBaseValue() + " + §e" + (statValue.getValue() - statValue.getBaseValue()));
        } else {
            sendUsage(user);

        }
    }

    @Override
    public Map<Integer, List<String>> mapArgumentToCompletion() {
        Map<Integer, List<String>> argsMap = new HashMap<>();
        argsMap.put(1, ConfigUtils.getEnumNames(RiftStat.class));
        argsMap.put(2, Arrays.asList("ADD", "MULTIPLY", "OVERRIDE"));
        return argsMap;
    }
}
