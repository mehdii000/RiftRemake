package me.mehdidev.rift.guis.impl;

import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.GuiParams;
import me.mehdidev.rift.guis.GuiSlot;
import me.mehdidev.rift.handlers.MathHandler;
import me.mehdidev.rift.handlers.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

@GuiParams(id = "RIFT_GUI", height = 4, isCachable = true)
public class GuiRift extends AbstractGui {

    @Override
    public void build() {
        defaultBackground();
        getSlot(1, 1).fill(Material.GOLD_BLOCK, "§eTreasure!");
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        GuiSlot slot = getSlot(x, y);
        User user = User.getUser(event.getWhoClicked().getUniqueId());
        if (slot.getType()==Material.GOLD_BLOCK) {
            slot.fill(Material.STAINED_GLASS_PANE, "§1");
            getSlot(MathHandler.random(0, 8), MathHandler.random(0, getHeight()-1)).fill(Material.GOLD_BLOCK, "§eTreasure!");
        }
        event.setCancelled(slot.isLocked());
    }

}
