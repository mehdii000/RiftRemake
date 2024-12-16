package me.mehdidev.rift.items.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.google.common.util.concurrent.AtomicDouble;

public interface MaterialFunction
{
    default void onInteraction(PlayerInteractEvent e) {}
    default void onInventoryClick(SItem instance, InventoryClickEvent e) {}
    default void onInventoryMove(SItem instance, InventoryMoveItemEvent e) {}
    default void onDamage(Entity damaged, Player damager, AtomicDouble damage, SItem item) {}
    default void onKill(Entity damaged, Player damager, SItem item) {}
    default boolean whileHolding(Player holding, SItem sItem) { return false; }
    default void onInstanceUpdate(SItem instance) {}
	default void onPickup(PlayerPickupItemEvent e) {}
	default void onItemDrop(SItem sItem, PlayerDropItemEvent e) {}
	
}