package me.mehdidev.rift.guis;

import me.mehdidev.rift.guis.impl.GuiRift;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum RiftGuis {
    RIFT_GUI("Rift Guide", GuiRift.class);

    public static ConcurrentHashMap<UUID, AbstractGui> lastOpenedInvetories = new ConcurrentHashMap<>();

    private String title;
    private Class<?>  clazz;

    RiftGuis(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public static AbstractGui getFromInventory(UUID playerUUID) {
        if (lastOpenedInvetories.containsKey(playerUUID)) {
            return lastOpenedInvetories.get(playerUUID);
        }
        return null;
    }

    public void openForPlayer(Player player) {
        try {
            AbstractGui gui = (AbstractGui) clazz.newInstance();
            lastOpenedInvetories.put(player.getUniqueId(), gui);
            player.openInventory(gui.getInventory());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}
