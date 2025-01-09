package me.mehdidev.rift.areas;

import me.mehdidev.rift.handlers.ItemUtils;
import me.mehdidev.rift.handlers.User;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum RiftTasks {
    HELP_BUGHUNTER("Help out Bughunter", "A detailed placeholder lmao...", ItemUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHBzOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZjYmU3MGMyZDFjNjhmZmFiZDJmYmJiZTNmZmYxNzQ2ZjQyMjI5Mzc4NjU2MDQ3NmVjZGZlYzVhYTI5M2Q3ZmEifX19")),
    TALK_TO_JAQUELLE("Talk to Sad Jacquelle", "Checkout why Jacquelle is sad.", ItemUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHBzOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhlYmQzZjk2NmFlZGY3NDc2MTgzZGZkYTk2NmUzM2M5YmZjNDllYTc0ZTEwZDk5YmU4OGUzZTFkM2QwYzI5ODUifX19")),
    TALK_TO_POHRTAL("Talk to Pohrtal", "Help Pohrtal gather his scattered eyes.", ItemUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHBzOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVlZTg1OGZjZjg1YzkxNDgwZWU5YTkxMjkxZDQ1MmEzYWVjNzY3ZjVmOWMwYzk3MGY3ZTRlOWMzODViOWRkMCJ9fX0")),
    SKIP_TASKS("Skip the... §crift??", "What is happening here?", ItemUtils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMwODk0OTMxYTUxMDM4N2U0ZjYxMzgyOGYwMjM5Y2E0ZDkwODUzNDk5NjM3ZjYwNzlkMzkzMjdmMTQ2ZjBlOSJ9fX0"));

    private String name;
    private String description;
    private ItemStack icon;
    RiftTasks(String name, String description, ItemStack icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon(User user) {
        ItemStack stack = icon;
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        List<String> loreList = new ArrayList<>();
        ItemUtils.splitStringLength(getDescription(), 38).forEach(line -> {
            loreList.add(ChatColor.GRAY + line);
        });
        loreList.add("");
        loreList.add(user.completedTask(name()) ? ChatColor.GREEN + "Completed!" : ChatColor.RED + "Not Completed!");
        meta.setLore(loreList);
        stack.setItemMeta(meta);
        return stack;
    }

    public String getDescription() {
        return description;
    }

}
