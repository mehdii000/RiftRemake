package me.mehdidev.rift.listeners;

import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.RiftGuis;
import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!AbstractGui.isInRiftGui(User.getUser(event.getWhoClicked().getUniqueId()), event.getInventory())) return;
        AbstractGui gui = RiftGuis.getFromInventory(event.getWhoClicked().getUniqueId());
        if (gui == null) return;
        int position = event.getRawSlot();
        int x = position % 9;
        int y = position / 9;
        if (gui.getSlot(x, y).hasClickConsumerThenRun());
        gui.onClick(x, y, event);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (AbstractGui.isInRiftGui(User.getUser(event.getPlayer().getUniqueId()), event.getInventory())) {
            AbstractGui gui = RiftGuis.getFromInventory(event.getPlayer().getUniqueId());
            User.getUser(event.getPlayer().getUniqueId()).debug(ChatColor.GRAY + "Opened Inventory " + ChatColor.AQUA + RiftGuis.lastOpenedInvetories.get(event.getPlayer().getUniqueId()).getGuiType().name());
        } else {
            RiftGuis.lastOpenedInvetories.remove(event.getPlayer().getUniqueId());
        }
    }

}
