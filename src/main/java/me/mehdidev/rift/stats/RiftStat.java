package me.mehdidev.rift.stats;

import org.bukkit.ChatColor;

public enum RiftStat {
    RIFT_TIME(400, "Rift Time", "ф", ChatColor.GREEN, "Allows you to stay in the Rift Dimension for longer."),
    SPEED(20, "Speed", "✦", ChatColor.WHITE, "Indicates the movement speed of the player."),
    MANA(100, "Intelligence", "✎", ChatColor.AQUA, "Indicates the amount of ✎ Mana the player has."),
    MANA_REGEN(0, "Mana Regen", "⚡", ChatColor.AQUA, "Indicates how much faster the player's ✎ Mana regenerates."),
    RIFT_DAMAGE(10, "Rift Damage", "❁", ChatColor.DARK_PURPLE, "Indicates exactly how much damage players deal to Mobs."),
    HEARTS(10, "Hearts", "❤", ChatColor.RED, "Indicates how many hearts a player has. Functions only in the ⏣ Stillgore Château.")
    ;

    private final double baseValue;
    private final String name;
    private final String symbol;
    private final ChatColor color;
    private final String description;
    RiftStat(double baseValue, String name, String symbol, ChatColor color, String description) {
        this.baseValue = baseValue;
        this.name = name;
        this.symbol = symbol;
        this.color = color;
        this.description = description;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

}
