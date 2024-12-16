package me.mehdidev.rift.items.core;

import java.util.List;

public interface MaterialStatistics
{
	String getDisplayName();
    default String[] getLore()
    {
        return null;
    }
    default List<String> getLoreInstanced(SItem instance) {
    	return null;
    }
    GenericItemType getType();
    default SpecificItemType getSpecificType()
    {
        return SpecificItemType.NONE;
    }
    default boolean isStackable()
    {
        return true;
    }
    default boolean displayRarity()
    {
        return true;
    }
    default Rarity getRarity() { return Rarity.COMMON; }
    default long getPriceMotes()
    {
        return 0;
    }
    default boolean loadData(SItem instance) { return false; }
    default void load(SItem sItem) {}
    
}