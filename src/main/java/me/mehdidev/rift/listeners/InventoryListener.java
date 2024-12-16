package me.mehdidev.rift.listeners;

import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.RiftGuis;
import me.mehdidev.rift.handlers.DebugHandler;
import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        AbstractGui gui = RiftGuis.getFromInventory(event.getWhoClicked().getUniqueId());
        assert gui != null;
        if (!event.getInventory().equals(gui.getInventory())) return;
        int position = event.getRawSlot();
        int x = position % 9;
        int y = position / 9;
        gui.onClick(x, y, event);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        AbstractGui gui = RiftGuis.getFromInventory(event.getPlayer().getUniqueId());
        if (gui == null) return;
        User.getUser(event.getPlayer().getUniqueId()).debug(ChatColor.GRAY + "Opened Inventory " + ChatColor.AQUA + RiftGuis.lastOpenedInvetories.get(event.getPlayer().getUniqueId()).getGuiType().name());
        gui.addViewer(User.getUser(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        AbstractGui gui = RiftGuis.getFromInventory(event.getPlayer().getUniqueId());
        if (gui == null || gui.isCachable()) return;
        User.getUser(event.getPlayer().getUniqueId()).debug(ChatColor.GRAY + "Closed Inventory " + ChatColor.AQUA + RiftGuis.lastOpenedInvetories.get(event.getPlayer().getUniqueId()).getGuiType().name());
        gui.removeViewer(User.getUser(event.getPlayer().getUniqueId()));
    }

}
