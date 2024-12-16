package me.mehdidev.rift.items.impl.misc;

import me.mehdidev.rift.handlers.User;
import me.mehdidev.rift.items.core.*;
import org.bukkit.ChatColor;

public class MaddoxBadphone implements SkullStatistics, Ability {

    @Override
    public String getDisplayName() {
        return "Maddox Badphone";
    }

    @Override
    public GenericItemType getType() {
        return GenericItemType.ITEM;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public String getAbilityName() {
        return "Wasssaaaaa?";
    }

    @Override
    public String getAbilityDescription() {
        return "§7Instantly calls §5Maddox§7!";
    }

    @Override
    public void onAbilityUse(User user, SItem sItem) {
        user.send(ChatColor.YELLOW + "RING!");
        user.ding();
    }

    @Override
    public String getURL() {
        return "ewogICJ0aW1lc3RhbXAiIDogMTY3Njk2Njc2NDgwNywKICAicHJvZmlsZUlkIiA6ICI2ZTIyNjYxZmNlMTI0MGE0YWE4OTA0NDA0NTFiYjBiNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJncnZleWFyZCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lMDQwMTI0ZjIyYTg0Mjk2NmJlYjU1YzllNjk4ZmUzYTJjOGI3MTMxOTBjMjZlMDNkMzdmNDA3NGJkNTAzMDMxIgogICAgfQogIH0KfQ";
    }

}
