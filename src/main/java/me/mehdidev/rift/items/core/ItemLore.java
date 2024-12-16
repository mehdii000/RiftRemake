package me.mehdidev.rift.items.core;

import java.util.ArrayList;
import java.util.List;

import me.mehdidev.rift.handlers.ItemUtils;
import org.bukkit.ChatColor;

public class ItemLore
{
    private SItem parent;
    public ItemLore(SItem parent)
    {
        this.parent = parent;
    }

    public List<String> asBukkitLore()
    {
        List<String> lore = new ArrayList<>();
        SMaterial material = parent.getType();
        MaterialStatistics statistics = material.getStatistics();
        Ability ability = material.getAbility();

        if (statistics instanceof SwordStatistic) {
            lore.add(ChatColor.GRAY + "Rift Damage: " + ChatColor.GREEN + "+" + ((SwordStatistic) statistics).getRiftDamage());
        }

        lore.add(ChatColor.GRAY + " ");
        if (ability != null)
        {
            StringBuilder abilityTitle = new StringBuilder()
                    .append(ChatColor.GOLD).append("Ability: ").append(ability.getAbilityName()).append(ChatColor.YELLOW).append(ChatColor.BOLD).append(" " + ability.getAbilityActivation().name().replace("_", " "));
            lore.add(abilityTitle.toString());
            for (String line : ItemUtils.splitByWordAndLength(ability.getAbilityDescription(), 30, "\\s"))
                lore.add(ChatColor.GRAY + line);
            if (ability.getAbilityCooldownTicks() != 0) lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + (ability.getAbilityCooldownTicks()/20) + "s");
            if (ability.getManaCost() != 0) lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.AQUA + ability.getManaCost());
            lore.add("");
        }
        
        String[] l = this.parent.getType().getStatistics().getLore();
        if (l != null)
        {
            for (String string : l)
                lore.add(ChatColor.GRAY + string);
            if (l.length != 0)
                lore.add("");
        }
        
        if (statistics.getLoreInstanced(this.parent) != null) {
        	for (String s : statistics.getLoreInstanced(this.parent)) {
        		lore.add(ChatColor.GRAY + s);
       		}
        }
        
        lore.add("" + statistics.getRarity().getRarityColor() + ChatColor.BOLD + statistics.getRarity().name() + " " + parent.getType().getStatistics().getSpecificType().name());
        return lore;
    }

    private boolean addPossiblePropertyInt(String name, double i, int r, String succeeding, boolean green, List<String> list)
    {
        i += r;
        if (i == 0) return false;
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.GRAY).append(name).append(": ")
                .append(green ? ChatColor.GREEN : ChatColor.RED)
                .append(i < 0 ? "" : "+").append(Math.round(i)).append(succeeding);
        list.add(builder.toString());
        return true;
    }

    private boolean addPossiblePropertyInt(String name, double i, String succeeding, boolean green, List<String> list)
    {
        return addPossiblePropertyInt(name, i, 0, succeeding, green, list);
    }

    private boolean addPossiblePropertyDouble(String name, double d, int r, String succeeding, boolean green, List<String> list)
    {
        d += r;
        if (d == 0.0) return false;
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.GRAY).append(name).append(": ")
                .append(green ? ChatColor.GREEN : ChatColor.RED)
                .append(d < 0.0 ? "" : "+").append(d).append(succeeding);
        list.add(builder.toString());
        return true;
    }
}