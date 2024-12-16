package me.mehdidev.rift.items.impl.swords;

import me.mehdidev.rift.items.core.*;
import org.bukkit.ChatColor;

public class WyldSword implements SwordStatistic {

    @Override
    public String getDisplayName() {
        return "Wyld Sword";
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.WEAPON;
    }

    @Override
    public SpecificItemType getSpecificType() {
        return SpecificItemType.SWORD;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public String[] getLore() {
        return LoreBuilder.build(
                ChatColor.DARK_GRAY + "Could become anything.",
                ChatColor.DARK_GRAY + "It's a wyldcard!"
        );
    }

    @Override
    public int getRiftDamage() {
        return 2;
    }
}
