package me.mehdidev.rift.items.impl.swords;

import me.mehdidev.rift.handlers.ItemUtils;
import me.mehdidev.rift.items.core.*;
import org.bukkit.ChatColor;

public class DetectiveScanner implements SkullStatistics, Ability {

    @Override
    public String getDisplayName() {
        return "Detective Scanner";
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.TOOL;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public String getAbilityName() {
        return "Detect!";
    }

    @Override
    public String getAbilityDescription() {
        return "Investigate a clue or abnormality.";
    }

    @Override
    public int getAbilityCooldownTicks() {
        return 4*20;
    }

    @Override
    public int getManaCost() {
        return 10;
    }

    @Override
    public String getURL() {
        return "";
    }
}
