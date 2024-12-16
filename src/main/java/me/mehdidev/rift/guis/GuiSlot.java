package me.mehdidev.rift.guis;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class GuiSlot {

    private final int x;
    private final int y;
    private final AbstractGui parentGui;
    private boolean locked;
    private Consumer<GuiSlot> slotConsumer = null;

    public GuiSlot(int x, int y, AbstractGui parent) {
        this.x = x;
        this.y = y;
        this.parentGui = parent;
        this.locked = true;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void fill(Material material, String name) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        getParentGui().getInventory().setItem((9*getY())+getX(), stack);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public AbstractGui getParentGui() {
        return parentGui;
    }

    public void onClick(InventoryClickEvent event) {}

    public Material getType() {
        return getParentGui().getInventory().getItem((9*getY())+getX()).getType();
    }
}
