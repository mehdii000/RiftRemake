package me.mehdidev.rift.items.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.mehdidev.rift.handlers.ItemUtils;
import me.mehdidev.rift.items.impl.swords.DetectiveScanner;
import me.mehdidev.rift.items.impl.swords.WyldSword;
import org.bukkit.Material;

public enum SMaterial
{
	
	WYLD_SWORD(Material.WOOD_SWORD, WyldSword.class),
    DETECTIVE_SCANNER(Material.SKULL_ITEM, DetectiveScanner.class)
    ;
	
    private final Material craftMaterial;
    private final short data;	
    private final Class<?> clazz;
    private final boolean craft;
    private final String baseName;

    SMaterial(Material craftMaterial, short data, Class<?> clazz, boolean craft, String baseName)
    {
        this.craftMaterial = craftMaterial;
        this.data = data;
        this.clazz = clazz;
        this.craft = craft;
        this.baseName = baseName;
    }

    SMaterial(Material craftMaterial, short data, Class<?> clazz, boolean craft)
    {
        this(craftMaterial, data, clazz, craft, null);
    }

    SMaterial(Material craftMaterial, Class<?> clazz, boolean craft)
    {
        this(craftMaterial, (short) 0, clazz, craft);
    }

    SMaterial(Material craftMaterial, Class<?> clazz)
    {
        this(craftMaterial, clazz, false);
    }

    SMaterial(Material craftMaterial, Class<?> clazz, short data)
    {
        this(craftMaterial, data, clazz, false);
    }

    SMaterial(Material craftMaterial, short data, String baseName)
    {
        this(craftMaterial, data, null, true, baseName);
    }

    SMaterial(Material craftMaterial)
    {
        this(craftMaterial, null, true);
    }

    public static SMaterial getMaterial(String name)
    {
        try
        {
            return valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException ex)
        {
            return null;
        }
    }

    public static SMaterial getSpecEquivalent(Material material, short data)
    {
        if (material == Material.LOG || material == Material.LOG_2 || material == Material.LEAVES || material == Material.LEAVES_2)
            data %= 4;
        List<SMaterial> results = Arrays.stream(values())
                .filter((m) -> m.craft && m.getCraftMaterial() == material)
                .collect(Collectors.toList());
        for (SMaterial result : results)
        {
            if (result.data == data)
                return result;
        }
        if (results.isEmpty())
            return null;
        return results.get(0);
    }

    public MaterialFunction getFunction()
    {
        Object generic = getGenericInstance();
        if (generic instanceof MaterialFunction)
            return (MaterialFunction) generic;
        return null;
    }

    public MaterialStatistics getStatistics()
    {
        Object generic = getGenericInstance();
        if (generic instanceof MaterialStatistics)
            return (MaterialStatistics) generic;
        return null;
    }

    public String getDisplayName(short variant)
    {
        if (hasClass())
            return getStatistics().getDisplayName();
        return ItemUtils.getMaterialDisplayName(craftMaterial, variant);
    }

    public SkullStatistics getSkullStatistics()
    {
        MaterialStatistics statistics = getStatistics();
        if (!(statistics instanceof SkullStatistics)) return null;
        return (SkullStatistics) statistics;
    }

    public ItemData getItemData()
    {
        if (!hasClass()) return null;
        Object generic = getGenericInstance();
        if (generic instanceof ItemData)
            return (ItemData) generic;
        return null;
    }

    public Object getGenericInstance()
    {
        if (clazz == null) return null;
        try
        {
            return clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
        }
        return null;
    }

    public boolean hasClass()
    {
        return clazz != null;
    }

	public String getBaseName() {
		return baseName;
	}

	public Material getCraftMaterial() {
		return craftMaterial;
	}

	public short getData() {
		return data;
	}

	public boolean isCraft() {
		return craft;
	}
	
	public Ability getAbility()
    {
        if (!hasClass()) return null;
        Object generic = getGenericInstance();
        if (generic instanceof Ability)
            return (Ability) generic;
        return null;
    }
	
}