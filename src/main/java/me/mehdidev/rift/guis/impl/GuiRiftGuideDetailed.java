package me.mehdidev.rift.guis.impl;

import me.mehdidev.rift.areas.RiftTasks;
import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.GuiParams;
import me.mehdidev.rift.handlers.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

@GuiParams(id = "RIFT_GUIDE_DETAILED", height = 5)
public class GuiRiftGuideDetailed extends AbstractGui {

    private List<RiftTasks> tasks;
    public GuiRiftGuideDetailed(User viewer, List<RiftTasks> tasks) {
        super(viewer);
        this.tasks = tasks;
    }


    @Override
    public void build() {
        defaultBackground();
        getSlot(WIDTH / 2, getHeight()-1).fill(Material.ARROW, "§aBack").onClick(slot -> getViewer().openGUI(new GuiRift(getViewer())));
        for (int i = 0; i < Math.min(6, tasks.size()); i++) {
            getSlot(1+i, 1).fill(tasks.get(i).getIcon(getViewer()));
        }
    }

    @Override
    public void onClick(int x, int y, InventoryClickEvent event) {
        event.setCancelled(true);
    }

}
