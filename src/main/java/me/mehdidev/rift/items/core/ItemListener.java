package me.mehdidev.rift.items.core;

import me.mehdidev.rift.handlers.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if (!SItem.isSpecItem(e.getItem())) return;
        if (e.getItem().getType().equals(Material.SKULL_ITEM)) {
        	e.setCancelled(true);
        }
        
        SItem sItem = SItem.find(e.getItem());
        if (sItem == null) return;
        sItem.update();
        Action action = e.getAction();
        Player player = e.getPlayer();
        Ability ability = sItem.getType().getAbility();
        if (ability != null)
        {
            AbilityActivation activation = ability.getAbilityActivation();
            if (activation == AbilityActivation.LEFT_CLICK || activation == AbilityActivation.RIGHT_CLICK)
            {
                if (activation == AbilityActivation.LEFT_CLICK ?
                        action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK :
                        action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                {
                    ItemUtils.useAbility(player, sItem);
                }
            } else if (activation == AbilityActivation.SHIFT_RIGHT_CLICK) {
            	if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && player.isSneaking()) {
            		ItemUtils.useAbility(player, sItem);
            	}
            }
        }
        MaterialFunction function = sItem.getType().getFunction();
        if (function != null)
            function.onInteraction(e);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onItemClick(InventoryClickEvent e)
    {
        ItemStack stack = e.getCurrentItem();
        if (stack == null) return;
        SItem sItem = SItem.find(stack);
        if (sItem == null) return;
        if (sItem.getType().getFunction() == null) return;
        sItem.getType().getFunction().onInventoryClick(sItem, e);
    }
    
    @EventHandler
    public void onItemMove(InventoryMoveItemEvent e) {
    	ItemStack stack = e.getItem();
        if (stack == null) return;
        SItem sItem = SItem.find(stack);
        if (sItem == null) return;
        if (sItem.getType().getFunction() == null) return;
        sItem.getType().getFunction().onInventoryMove(sItem, e);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e)
    {
    	if (SItem.isSpecItem(e.getItem().getItemStack())) {
            SItem.find(e.getItem().getItemStack()).getType().getFunction().onPickup(e);
    	}
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
    	if (!SItem.isSpecItem(e.getItemDrop().getItemStack())) return;
        SItem sItem = SItem.find(e.getItemDrop().getItemStack());
        sItem.getType().getFunction().onItemDrop(sItem, e);
    }
    
    private static boolean similar(ItemStack is, ItemStack is1)
    {
        if (is == null && is1 == null) return true;
        if (is != null && is1 == null) return false;
        if (is == null) return false;
        return is.isSimilar(is1);
    }

}