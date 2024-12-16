package me.mehdidev.rift.items.core;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.mehdidev.rift.handlers.ItemUtils;
import me.mehdidev.rift.handlers.SerialNBTTagCompound;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class SItem implements Cloneable, ConfigurationSerializable
{
    private static final List<String> GLOBAL_NBT_TAGS = Arrays.asList("type", "origin");
    private static final List<String> GLOBAL_DATA_KEYS = Arrays.asList("type", "variant", "stack", "origin");

    private SMaterial type;
    private final short variant;
    private ItemStack stack;
    private ItemLore lore;
    private ItemOrigin origin;
    private Rarity rarity;
    private NBTTagCompound data;

    protected SItem(SMaterial type, short variant, ItemStack stack, ItemOrigin origin, NBTTagCompound data, boolean overwrite)
    {
        this.type = type;
        this.variant = variant;
        this.stack = stack;
        this.data = data;
        this.lore = new ItemLore(this);
        this.origin = origin;
        this.rarity = type.getStatistics().getRarity();

        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(getFullName());
        stack.setItemMeta(itemMeta);

        this.data.setInt("Unbreakable", 1);
        if (type.getStatistics().loadData(this)) {
        	type.getStatistics().loadData(this);
        }
        if (overwrite)
        {
            ItemMeta meta = this.stack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
            meta.setLore(lore.asBukkitLore());
            this.stack.setItemMeta(meta);
            update();
        }
    }

    public void toggleGlint(boolean enchant)
    {
        if (enchant)
        {
            if (stack.getItemMeta().hasEnchants())
                return;
            ItemMeta meta = stack.getItemMeta();
            meta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true);
            stack.setItemMeta(meta);
            return;
        }
        if (!stack.getItemMeta().hasEnchants())
            return;
        ItemMeta meta = stack.getItemMeta();
        meta.removeEnchant(org.bukkit.enchantments.Enchantment.DURABILITY);
        stack.setItemMeta(meta);
    }
    
    public void setType(Material mat, boolean instanceUpdate)
    {
        this.stack.setType(mat);
        update(instanceUpdate);
    }

    public void setAmount(int amount)
    {
        this.stack.setAmount(amount);
    }

    public void setOrigin(ItemOrigin origin)
    {
        this.origin = origin;
        update();
    }

    public String getFullName()
    {
        return getRarity().getRarityColor() + this.getType().getDisplayName(variant);
    }

    private Rarity getRarity() {
        return rarity;
    }

    public String getDataString(String key)
    {
        return data.getString(key);
    }

    public int getDataInt(String key)
    {
        return data.getInt(key);
    }

    public long getDataLong(String key)
    {
        return data.getLong(key);
    }
    
    public NBTTagCompound getDataCompound(String key)
    {
        return data.getCompound(key);
    }

    public void setDataString(String key, String value)
    {
        data.setString(key, value);
        update();
    }

    public void setDataInt(String key, int value)
    {
        data.setInt(key, value);
        update();
    }

    public void setDataDouble(String key, double value)
    {
        data.setDouble(key, value);
        update();
    }

    public void setDataFloat(String key, float value)
    {
        data.setFloat(key, value);
        update();
    }

    public void setDataLong(String key, long value)
    {
        data.setLong(key, value);
        update();
    }

    public void setDataBoolean(String key, boolean value)
    {
        data.setBoolean(key, value);
        update();
    }

    public void setDataCompound(String key, NBTTagCompound value)
    {
        data.set(key, value);
        update();
    }

    public void removeData(String key)
    {
        data.remove(key);
        update();
    }

    public boolean hasDataFor(String key)
    {
        return data.hasKey(key);
    }

    public void update(boolean instanceUpdate)
    {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.stack);
        if (nmsStack == null)
            return;
        NBTTagCompound compound = nmsStack.getTag() != null ? nmsStack.getTag() : new NBTTagCompound();
        compound.remove("type");
        compound.remove("variant");
        compound.remove("origin");
        
        for (String key : data.c())
        {
            compound.remove(key);
            if (!data.get(key).isEmpty())
                compound.set(key, data.get(key));
        }
        compound.remove("amount");
        compound.setString("type", type.name());
        if (variant != 0)
            compound.setShort("variant", variant);
        if (origin != ItemOrigin.UNKNOWN)
            compound.setString("origin", origin.name());
        if (!this.getType().getStatistics().isStackable() && !compound.hasKey("uuid"))
            compound.setString("uuid", UUID.randomUUID().toString());
        
        nmsStack.setTag(compound);
        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        ItemMeta meta = stack.getItemMeta();
        MaterialStatistics statistics = type.getStatistics();
        meta.setDisplayName(getFullName());
        meta.setLore(lore.asBukkitLore());
        stack.setItemMeta(meta);
        
        MaterialFunction function = type.getFunction();
        if (function != null && instanceUpdate)
            type.getFunction().onInstanceUpdate(this);
    }

    public void update()
    {
        update(true);
    }
    
    public static SItem of(SMaterial specMaterial, short variant, ItemOrigin origin)
    {
        ItemStack stack = new ItemStack(specMaterial.getCraftMaterial(), 1, variant);
        MaterialStatistics statistics = specMaterial.getStatistics();
        if (specMaterial.getCraftMaterial() == Material.SKULL_ITEM &&
                statistics instanceof SkullStatistics)
        {
            stack.setDurability((short) 3);
            stack = ItemUtils.getSkull(((SkullStatistics)statistics).getURL(), stack, specMaterial);
        }
        return new SItem(specMaterial, variant, stack, origin,
                specMaterial.getItemData() != null ? specMaterial.getItemData().getData() : new NBTTagCompound(),
                true);
    }

    public static SItem of(SMaterial specMaterial, ItemOrigin origin)
    {
        return of(specMaterial, specMaterial.getData(), origin);
    }

    public static SItem of(SMaterial specMaterial, short variant)
    {
        return of(specMaterial, variant, ItemOrigin.UNKNOWN);
    }

    public static SItem of(SMaterial specMaterial)
    {
        return of(specMaterial, specMaterial.getData());
    }

    public static SItem of(ItemStack stack, ItemOrigin origin)
    {
        if (stack == null)
            return null;
        SMaterial material = SMaterial.getSpecEquivalent(stack.getType(), stack.getDurability());
        if (material == null)
            return null;
        if (Item.getById(material.getCraftMaterial().getId()) == null)
            return null;
        SItem n = of(material, stack.getDurability(), origin);
        n.getStack().setAmount(stack.getAmount());
        return n;
    }

    public ItemStack getStack() {
		return stack;
	}

	public static SItem of(ItemStack stack)
    {
        return of(stack, ItemOrigin.UNKNOWN);
    }

    public static boolean isSpecItem(ItemStack stack)
    {
        if (stack == null) return false;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        if (nmsStack == null) return false;
        if (!nmsStack.hasTag()) return false;
        NBTTagCompound compound = nmsStack.getTag();
        return compound.hasKey("type");
    }

    @Override
    public SItem clone()
    {
        return new SItem(type, variant, stack.clone(), origin, data, true);
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof SItem)) return false;
        SItem item = (SItem) o;
        return type == item.type && variant == item.variant && stack.equals(item.stack) && origin == item.origin &&
                data.equals(item.data);
    }

    public NBTTagCompound toCompound()
    {
        NBTTagCompound compound = new NBTTagCompound();
        for (String key : data.c())
        {
            compound.remove(key);
            compound.set(key, data.get(key));
        }
        compound.setString("type", type.name());
        if (variant != 0)
            compound.setShort("variant", variant);
        compound.setInt("amount", stack.getAmount());
        if (origin != ItemOrigin.UNKNOWN)
            compound.setString("origin", origin.name());
        if (!this.getType().getStatistics().isStackable() && !compound.hasKey("uuid"))
            compound.setString("uuid", UUID.randomUUID().toString());
        return compound;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type.name());
        map.put("variant", variant);
        map.put("amount", stack.getAmount());
        map.put("origin", origin.name());
        for (String k : data.c())
        {
            if (k.equals("display"))
                continue;
            if (data.get(k) instanceof NBTTagCompound)
            {
                SerialNBTTagCompound serial = new SerialNBTTagCompound(data.getCompound(k));
                for (Map.Entry<String, Object> entry : serial.serialize().entrySet())
                    map.put(k + "." + entry.getKey(), entry.getValue());
                continue;
            }
            map.put(k, ItemUtils.getObjectFromCompound(data, k));
        }
        return map;
    }

    public static SItem deserialize(Map<String, Object> map)
    {
        NBTTagCompound data = new NBTTagCompound();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (GLOBAL_DATA_KEYS.contains(entry.getKey()))
                continue;
            String key = entry.getKey();
            String[] dir = entry.getKey().split("\\.");
            if (dir.length >= 2)
            {
                key = dir[dir.length - 1];
                NBTTagCompound track = data;
                for (int i = 0; i < dir.length - 1; i++)
                {
                    if (!track.hasKey(dir[i]))
                        track.set(dir[i], new NBTTagCompound());
                    track = track.getCompound(dir[i]);
                }
                track.set(key, ItemUtils.getBaseFromObject(entry.getValue()));
                continue;
            }
            data.set(key, ItemUtils.getBaseFromObject(entry.getValue()));
        }
        SMaterial material = SMaterial.getMaterial((String) map.get("type"));
        short variant = ((Integer) map.get("variant")).shortValue();
        return new SItem(material, variant, new ItemStack(material.getCraftMaterial(), (int) map.get("amount"), variant), ItemOrigin.valueOf((String) map.get("origin")),
                data, true);
    }

    @Override
    public String toString()
    {
        return "SItem{type=" + type.name() + ", variant=" + variant + ", stack=" + stack.toString() + ", origin=" +
                origin.name() + ", data=" + data.toString() + "}";
    }

    public static SItem find(ItemStack stack)
    {
        if (stack == null) return null;
        if (!isSpecItem(stack)) return null;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = nmsStack.getTag();
        if (compound == null) return null;
        return of(compound, stack);
    }

    public static SItem of(NBTTagCompound compound)
    {
        SMaterial type = SMaterial.getMaterial(compound.getString("type"));
        ItemStack stack = new ItemStack(type.getCraftMaterial(), compound.hasKey("amount") ? compound.getInt("amount") : 1, type.getData());
        MaterialStatistics statistics = type.getStatistics();
        if (type.getCraftMaterial() == Material.SKULL_ITEM &&
                statistics instanceof SkullStatistics)
        {
            stack.setDurability((short) 3);
        }
        short variant = compound.hasKey("variant") ? compound.getShort("variant") : 0;
        NBTTagCompound data = new NBTTagCompound();
        for (String key : compound.c())
        {
            if (GLOBAL_NBT_TAGS.contains(key))
                continue;
            data.set(key, compound.get(key));
        }
        return new SItem(type, variant, stack,
                compound.hasKey("origin") ? ItemOrigin.valueOf(compound.getString("origin")) : ItemOrigin.UNKNOWN,
                data,
                true);
    }

    private static SItem of(NBTTagCompound compound, ItemStack stack)
    {
        SMaterial type = SMaterial.getMaterial(compound.getString("type"));
        NBTTagCompound data = new NBTTagCompound();
        for (String key : compound.c())
        {
            if (GLOBAL_NBT_TAGS.contains(key))
                continue;
            data.set(key, compound.get(key));
        }
        return new SItem(type, compound.hasKey("variant") ? compound.getShort("variant") : 0, stack,
                compound.hasKey("origin") ? ItemOrigin.valueOf(compound.getString("origin")) : ItemOrigin.UNKNOWN, data, false);
    }

	public SMaterial getType() {
		return type;
	}

	public ItemLore getLore() {
		return lore;
	}

	public NBTTagCompound getData() {
		return data;
	}

	public short getVariant() {
		return variant;
	}

	public ItemOrigin getOrigin() {
		return origin;
	}

	public void setStack(ItemStack s) {
		this.stack = s;
	}

	public void setMaterial(Material m) {
		this.stack.setType(m);
		update();
	}
	
	public void setSkullTexture(String Url) {
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        GameProfile profile = new GameProfile(getType().getStatistics().isStackable() ? UUID.fromString( type.getStatistics() instanceof Stackable ? ((Stackable) type.getStatistics()).getUUIDModifier() : "" ) : UUID.randomUUID(), null);
        byte[] ed = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/%s\"}}}", Url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(ed)));
        Field f;
        try
        {
            f = meta.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(meta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {}
        stack.setItemMeta(meta);
	}
	
}