
package me.mehdidev.rift.commands;

import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.items.core.SItem;
import me.mehdidev.rift.items.core.SMaterial;

@BaseCommand(name = "item", description = "Give yourself a custom item.")
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
            sendUsage(user);
        }
    }

}
