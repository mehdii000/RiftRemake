package me.mehdidev.rift.guis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class GuiSlot {

    private final int x;
    private final int y;
    private final AbstractGui parentGui;
    private boolean locked;
    private Consumer<GuiSlot> clickConsumer = null;

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

    public GuiSlot fill(Material material, String name) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        getParentGui().getInventory().setItem(getSlot(), stack);
        return this;
    }

    public GuiSlot fill(ItemStack stack) {
        getParentGui().getInventory().setItem(getSlot(), stack);
        return this;
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

    public void onClick(Consumer<GuiSlot> consumer) {
        this.clickConsumer = consumer;
    }

    public Material getType() {
        return getItemStack().getType();
    }

    public void colorify(short i) {
        ItemStack stack = getItemStack();
        stack.setDurability(i);
        getParentGui().getInventory().setItem(getSlot(), stack);
    }

    public int getSlot() {
        return (9*getY())+getX();
    }

    public ItemStack getItemStack() {
        return getParentGui().getInventory().getItem(getSlot());
    }

    public boolean hasClickConsumerThenRun() {
        if (clickConsumer == null) return false;
        clickConsumer.accept(this);
        return true;
    }
}
