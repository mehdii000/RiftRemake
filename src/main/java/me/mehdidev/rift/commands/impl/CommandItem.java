
package me.mehdidev.rift.commands.impl;

import me.mehdidev.rift.commands.AbstractCommand;
import me.mehdidev.rift.commands.BaseCommand;
import me.mehdidev.rift.guis.impl.GuiItemBrowser;
import me.mehdidev.rift.handlers.ConfigUtils;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.items.core.SItem;
import me.mehdidev.rift.items.core.SMaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaseCommand(name = "item", description = "Give yourself a custom item.", getMaxArguments = 1)
public class CommandItem extends AbstractCommand {

    @Override
    public String getUsage() {
        return "<ITEM_ID> *<amount>";
    }

    @Override
    public void run(User user, String[] args) {
        if (args.length > 0) {
            SItem sItem = SItem.of(SMaterial.getMaterial(args[0]));
            user.getPlayer().getInventory().addItem(sItem.getStack());
        } else {
            user.openGUI(new GuiItemBrowser(user));
        }
    }

    @Override
    public Map<Integer, List<String>> mapArgumentToCompletion() {
        Map<Integer, List<String>> argsMap = new HashMap<>();
        argsMap.put(1, ConfigUtils.getEnumNames(SMaterial.class));
        return argsMap;
    }
}
