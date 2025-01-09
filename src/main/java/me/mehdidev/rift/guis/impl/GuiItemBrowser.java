package me.mehdidev.rift.guis.impl;

import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.GuiParams;
import me.mehdidev.rift.guis.GuiSlot;
import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.items.core.SItem;
import me.mehdidev.rift.items.core.SMaterial;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

@GuiParams(id = "ITEM_BROWSER", height = 6)
public class GuiItemBrowser extends AbstractGui {

    public GuiItemBrowser(User viewer) {
        super(viewer);
    }

    @Override
    public void build() {
        defaultBackground();

        getSlotsBorders(0, 0, 8, getHeight() - 1).forEach(guiSlot -> {
            guiSlot.fill(Material.STAINED_GLASS_PANE, "§1");
            guiSlot.colorify((short) 11);
        });

        int maxColumns = 7; // Maximum number of columns for items
        int startRow = 1;   // Starting row for items
        int startColumn = 1; // Starting column for items

        SMaterial[] materials = SMaterial.values();
        for (int i = 0; i < Math.min(materials.length, 7*4); i++) {
            int row = startRow + (i / maxColumns);
            int column = startColumn + (i % maxColumns);
            int slotIndex = column + (row * 9);
            if (row < getHeight() - 2) {
                getInventory().setItem(slotIndex, SItem.of(materials[i]).getStack());
            }
        }
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        User user = User.getUser(event.getWhoClicked().getUniqueId());
        event.setCancelled(true);
        GuiSlot slot = getSlot(x, y);
        if (SItem.isSpecItem(slot.getItemStack())) {
            user.getPlayer().getInventory().addItem( SItem.of( SItem.find(slot.getItemStack()).getType() ).getStack() );
            user.ding();
        }
    }

}
