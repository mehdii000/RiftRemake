package me.mehdidev.rift.handlers;

import me.mehdidev.rift.Rift;
import me.mehdidev.rift.areas.RiftTasks;
import me.mehdidev.rift.guis.AbstractGui;
import me.mehdidev.rift.guis.RiftGuis;
import me.mehdidev.rift.stats.RiftStat;
import me.mehdidev.rift.stats.StatModifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class User
{
    private static final String PLUGIN_PREFIX = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Rift" + ChatColor.GRAY + "] ";

    private static final Map<UUID, User> USER_CACHE = new HashMap<>();
    private static final Rift plugin = Rift.getRift();
    private static final File USER_FOLDER = new File(plugin.getDataFolder(), "./users");

    private UUID uuid;
    private final Config config;

    private List<String> completedTasks;
    private List<StatModifier> activeStatModifiers;

    private User(UUID uuid)
    {
        this.uuid = uuid;
        this.completedTasks = new ArrayList<>();
        this.activeStatModifiers = new ArrayList<>();

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
        this.completedTasks = config.getStringList("completedTasks");
        this.activeStatModifiers = ConfigUtils.stringListToList(config.getStringList("activeStatModifiers"), StatModifier.class);
    }

    public void save()
    {
        config.set("uuid", uuid.toString());
        config.set("completedTasks", completedTasks);
        config.set("activeStatModifiers", ConfigUtils.listToStringList(activeStatModifiers));
        config.save();
    }

    public void openGUI(AbstractGui guiToOpen) {
        Player player = Bukkit.getPlayer(uuid);
        RiftGuis.lastOpenedInvetories.put(player.getUniqueId(), guiToOpen);
        guiToOpen.build();
        player.openInventory(guiToOpen.getInventory());

    }

    public void send(String message)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        player.sendMessage(message);
    }

    public void completeTask(RiftTasks task) {
        if (completedTasks.contains(task.name())) return;
        completedTasks.add(task.name());
    }

    public boolean completedTask(String taskID) {
        return completedTasks.contains(taskID);
    }

    public void addStatModifier(StatModifier modifer) {
        this.activeStatModifiers.add(modifer);
    }

    public void removeStatModifiersWithReason(RiftStat modifierType, StatModifier.ModificationReason reason) {
        for (int i = activeStatModifiers.size() - 1; i >= 0; i--) {
            if (activeStatModifiers.get(i).getReason().name().equalsIgnoreCase(reason.name()) &&
                    modifierType.name().equalsIgnoreCase(activeStatModifiers.get(i).getModifiedStat().name())) {
                activeStatModifiers.remove(i);
            }
        }
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

    public List<StatModifier> getActiveStatModifiers() {
        return activeStatModifiers;
    }

    public void updateStats() {
        Rift.getRift().riftStats.updateStats(this);
    }

}