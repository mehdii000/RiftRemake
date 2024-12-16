package me.mehdidev.rift.handlers;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.RiftGuis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User
{
    private static final String PLUGIN_PREFIX = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Rift" + ChatColor.GRAY + "] ";

    private static final Map<UUID, User> USER_CACHE = new HashMap<>();
    private static final Rift plugin = Rift.getRift();
    private static final File USER_FOLDER = new File(plugin.getDataFolder(), "./users");

    private UUID uuid;
    private final Config config;

    private User(UUID uuid)
    {
        this.uuid = uuid;

        if (!USER_FOLDER.exists()) USER_FOLDER.mkdirs();
        String path = uuid.toString() + ".yml";
        File configFile = new File(USER_FOLDER, path);
        boolean save = false;
        try
        {
            if (!configFile.exists())
            {
                save = true;
                configFile.createNewFile();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        this.config = new Config(USER_FOLDER, path);
        USER_CACHE.put(uuid, this);
        if (save) save();
        load();
    }

    public void load()
    {
        this.uuid = UUID.fromString(config.getString("uuid"));
    }

    public void save()
    {
        config.set("uuid", uuid.toString());
        config.save();
    }

    public void openGUI(RiftGuis guiToOpen) {
        Player player = Bukkit.getPlayer(uuid);
        AbstractGui PREVIOUS = RiftGuis.getFromInventory(uuid);
        if (PREVIOUS != null && PREVIOUS.isCachable() && PREVIOUS.getGuiType().name().equals(guiToOpen.name())) {
            debug(ChatColor.GRAY + "found cached inventory: " + ChatColor.YELLOW + guiToOpen.name());
            RiftGuis.lastOpenedInvetories.put(player.getUniqueId(), PREVIOUS);
            player.openInventory(PREVIOUS.getInventory());
        } else if (PREVIOUS == null || !PREVIOUS.isCachable()) {
            debug(ChatColor.GRAY + "creating inventory: " + ChatColor.AQUA + guiToOpen.name());
            guiToOpen.openForPlayer(player);
        }
    }

    public void send(String message)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        player.sendMessage(message);
    }

    public static User getUser(UUID uuid)
    {
        if (uuid == null) return null;
        if (USER_CACHE.containsKey(uuid)) return USER_CACHE.get(uuid);
        return new User(uuid);
    }

    public static Collection<User> getCachedUsers()
    {
        return USER_CACHE.values();
    }

    public void debug(String s) {
        send(PLUGIN_PREFIX + s);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public void ding() {
        getPlayer().playSound(getPlayer().getLocation(), Sound.ORB_PICKUP, 2f, 1f);
    }
}