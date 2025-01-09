package me.mehdidev.rift.guis;

import me.mehdidev.rift.guis.impl.GuiItemBrowser;
import me.mehdidev.rift.guis.impl.GuiRift;
import me.mehdidev.rift.guis.impl.GuiRiftGuideDetailed;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum RiftGuis {
    RIFT_GUIDE("Rift Guide", GuiRift.class),
    RIFT_GUIDE_DETAILED("Rift Guide - Details", GuiRiftGuideDetailed.class),
    ITEM_BROWSER("Item Browser",GuiItemBrowser.class);

    public static ConcurrentHashMap<UUID, AbstractGui> lastOpenedInvetories = new ConcurrentHashMap<>();

    private String title;
    private Class<?>  clazz;

    RiftGuis(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public static AbstractGui getFromInventory(UUID playerUUID) {
        if (lastOpenedInvetories.containsKey(playerUUID)) {
            if (lastOpenedInvetories.get(playerUUID) == null) return null;
            return lastOpenedInvetories.get(playerUUID);
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getClazz() {
        return clazz;
    }

}
