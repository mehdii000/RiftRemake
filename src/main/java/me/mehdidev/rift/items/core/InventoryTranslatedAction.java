package me.mehdidev.rift.items.core;

import org.bukkit.event.inventory.InventoryAction;

public enum InventoryTranslatedAction {	

	RIGHT_CLICK(InventoryAction.PICKUP_HALF),
	LEFT_CLICK(InventoryAction.PLACE_ONE),
	MIDDLE_CLICK(InventoryAction.CLONE_STACK),
	Q_CLICK(InventoryAction.DROP_ALL_SLOT);

	private InventoryAction actionType;

	InventoryTranslatedAction(InventoryAction actionType) {
		this.actionType = actionType;
	}
	
	public InventoryAction toAction() {
		return actionType;
	}
	
}
