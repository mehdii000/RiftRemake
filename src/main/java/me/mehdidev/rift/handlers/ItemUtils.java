package me.mehdidev.rift.handlers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.mehdidev.rift.items.core.Ability;
import me.mehdidev.rift.items.core.AbilityActivation;
import me.mehdidev.rift.items.core.SItem;
import me.mehdidev.rift.items.core.SMaterial;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtils {
	
	public static Object getObjectFromCompound(NBTTagCompound compound, String key)
    {
        Object o;
        switch (compound.get(key).getTypeId())
        {
            case 1:
                o = compound.getByte(key);
                break;
            case 2:
                o = compound.getShort(key);
                break;
            case 3:
                o = compound.getInt(key);
                break;
            case 4:
                o = compound.getLong(key);
                break;
            case 5:
                o = compound.getFloat(key);
                break;
            case 6:
                o = compound.getDouble(key);
                break;
            case 7:
                o = compound.getByteArray(key);
                break;
            case 10:
                o = compound.getCompound(key);
                break;
            case 11:
                o = compound.getIntArray(key);
                break;
            default:
                o = compound.getString(key);
                break;
        }
        return o;
    }
	
	public static NBTBase getBaseFromObject(Object o)
    {
        if (o instanceof Byte)
            return new NBTTagByte((byte) o);
        else if (o instanceof Short)
            return new NBTTagShort((short) o);
        else if (o instanceof Integer)
            return new NBTTagInt((int) o);
        else if (o instanceof Long)
            return new NBTTagLong((long) o);
        else if (o instanceof Float)
            return new NBTTagFloat((float) o);
        else if (o instanceof Double)
            return new NBTTagDouble((double) o);
        else if (o instanceof String)
            return new NBTTagString((String) o);
        return null;
    }
	
	public static String getMaterialDisplayName(Material material, short variant)
    {
        if (variant != 0)
            return SMaterial.getSpecEquivalent(material, variant).getBaseName();
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(new ItemStack(material));
        if (nmsStack == null) return material.name();
        if (nmsStack.getItem() == null) return material.name();
        return nmsStack.getName();
    }

    public static ItemStack getSkull(String texture, ItemStack stack, SMaterial material)
    {
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field f;
        try
        {
            f = meta.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(meta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {}
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getSkull(String texture) {
        ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        return getSkull(texture, stack, null);
    }

	public static List<String> splitByWordAndLength(String string, int splitLength, String separator)
    {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\G" + separator + "*(.{1," + splitLength + "})(?=\\s|$)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find())
            result.add(matcher.group(1));
        return result;
    }

    public static List<String> splitStringLength(String input, int length) {
        List<String> result = new ArrayList<>();

        if (input == null || length <= 0) {
            throw new IllegalArgumentException("Input string cannot be null and length must be greater than 0.");
        }

        int start = 0;

        while (start < input.length()) {
            int end = Math.min(start + length, input.length());

            // Adjust 'end' to avoid breaking words
            if (end < input.length() && input.charAt(end) != ' ') {
                int lastSpace = input.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            // Extract substring and trim it to avoid unnecessary spaces
            String substring = input.substring(start, end).trim();
            if (!substring.isEmpty()) {
                result.add(substring);
            }

            // Move 'start' to the next word
            start = end + 1;
        }

        return result;
    }

	
	public static void useAbility(Player player, SItem sItem)
    {
        Ability ability = sItem.getType().getAbility();
        if (ability != null)
        {
            AbilityActivation activation = ability.getAbilityActivation();
            if (activation != AbilityActivation.NO_ACTIVATION)
            {
            	ability.onAbilityUse(User.getUser(player.getUniqueId()), sItem);
            }
        }
    }

}
