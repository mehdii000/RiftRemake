package me.mehdidev.rift.guis.impl;

import me.mehdidev.rift.areas.RiftArea;
import me.mehdidev.rift.areas.RiftAreas;
import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.GuiParams;
import me.mehdidev.rift.guis.GuiSlot;
import me.mehdidev.rift.handlers.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

@GuiParams(id = "RIFT_GUIDE", height = 5)
public class GuiRift extends AbstractGui {

    public GuiRift(User viewer) {
        super(viewer);
    }

    @Override
    public void build() {
        defaultBackground();
        getSlot(WIDTH / 2, getHeight()-1).fill(Material.BARRIER, "§cClose");
        getSlot(1, 1).fill(RiftAreas.WYLD_WOODS.createStack((short) 3, getViewer())).onClick(slot -> getViewer().openGUI(new GuiRiftGuideDetailed(getViewer(), ((RiftArea) RiftAreas.WYLD_WOODS.getGenericInstance()).getTasks() )));
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        GuiSlot slot = getSlot(x, y);

        event.setCancelled(slot.isLocked());
    }

}
