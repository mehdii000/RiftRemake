package me.mehdidev.rift.items.core;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.LIGHT_PURPLE),
    DIVINE(ChatColor.AQUA),
    SPECIAL(ChatColor.RED);

    private ChatColor rarityColor;
    Rarity(ChatColor rarityColor) {
        this.rarityColor = rarityColor;
    }

    public ChatColor getRarityColor() {
        return rarityColor;
    }
}
