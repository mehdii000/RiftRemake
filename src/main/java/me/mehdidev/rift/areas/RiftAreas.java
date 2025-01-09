package me.mehdidev.rift.areas;

import me.mehdidev.rift.handlers.ItemUtils;
import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum RiftAreas {

    WYLD_WOODS(ChatColor.GREEN + "Wyld Woods", Material.LEAVES, AreaWyldWoods.class),
    ;

    private String name;
    private Material icon;
    private Class<?> clazz;
    RiftAreas(String name, Material icon, Class<?> clazz) {
        this.name = name;
        this.icon = icon;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public Class<?> getClazz() {
        return clazz;
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

    public ItemStack createStack(short durability, User user) {
        ItemStack stack = new ItemStack(getIcon(), 1, durability);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(getName());
        RiftArea riftArea = (RiftArea) getGenericInstance();
        assert riftArea != null;

        List<String> loreList = new ArrayList<>();

        ItemUtils.splitStringLength(riftArea.getDescription(), 38).forEach(line -> {
            loreList.add(ChatColor.GRAY + line);
        });

        loreList.add("");
        loreList.add(ChatColor.GRAY + "Things to do:");

        // Sorting tasks (Completed Tasks first)
        List<RiftTasks> sortedTasks = new ArrayList<>(riftArea.getTasks());
        sortedTasks.sort((task1, task2) -> {
            boolean task1Completed = user.completedTask(task1.name());
            boolean task2Completed = user.completedTask(task2.name());
            return Boolean.compare(!task1Completed, !task2Completed); // Sort completed first
        });

        // Display up to 7 tasks
        for (int i = 0; i < Math.min(sortedTasks.size(), 7); i++) {
            RiftTasks task = sortedTasks.get(i);
            if (user.completedTask(task.name())) {
                loreList.add(ChatColor.GREEN + "✔ " + task.getName()); // Completed task
            } else {
                loreList.add(ChatColor.LIGHT_PURPLE + "⇨ " + ChatColor.WHITE + task.getName()); // Pending task
            }
        }

        loreList.add("");
        loreList.add(ChatColor.YELLOW + "Click for details!");

        meta.setLore(loreList);
        stack.setItemMeta(meta);
        return stack;
    }

}
