package me.mehdidev.rift.items.core;

public enum SpecificItemType
{
    SWORD(false),
    STAFF(false),
    HELMET(false),
    CHESTPLATE(false),
    LEGGINGS(false),
    BOOTS(false),
    BOW(false),
    ACCESSORY(false),
    AXE(false),
    PICKAXE(false),
    SHOVEL(false),
    HOE(false),
    SHEARS(false),
    NONE,
    ROD(false),
    POTION(false),
    PET(false),
    FOOD(true);

    private final boolean stackable;

    SpecificItemType(boolean stackable)
    {
        this.stackable = stackable;
    }

    SpecificItemType()
    {
        this(true);
    }

    public String getName()
    {
        return name().replaceAll("_", " ");
    }

	public boolean isStackable() {
		return stackable;
	}
}