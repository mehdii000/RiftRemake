package me.mehdidev.rift.guis;

import me.mehdidev.rift.handlers.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public abstract class AbstractGui {

    /*
     * This class is extended from a gui object which also uses a @GuiParams annotation that is referenced here.
     * Example: RiftGui extends AbstractGui and must use @GuiParams annotation to set values that
     * are constants such as TITLE and HEIGHT. The width doesn't exist since Minecraft inventories only allow
     * a fixed horizontal size of 9 (0-8).
     */

    public final int WIDTH = 8;

    private String title;
    private int height;
    private Inventory inventory;
    private final Map<SlotKey, GuiSlot> slotMap = new HashMap<>();
    private User viewer;

    public AbstractGui(User viewer) {
        this.viewer = viewer;
        GuiParams guiParams = getClass().getAnnotation(GuiParams.class);
        if (guiParams == null) {
            throw new IllegalStateException("GuiParams annotation is missing for class: " + getClass().getName());
        }
        this.title = getGuiType().getTitle();
        this.height = guiParams.height();
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0.");
        }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < height; y++) {
                slotMap.put(new SlotKey(x, y), new GuiSlot(x, y, this));
            }
        }
        this.inventory = Bukkit.createInventory(null, 9 * height, title);
        RiftGuis.lastOpenedInvetories.put(viewer.getUuid(), this);
    }

    public static boolean isInRiftGui(User user, Inventory inventory) {
        return RiftGuis.lastOpenedInvetories.get(user.getUuid()).getInventory().equals(inventory);
    }

    public abstract void build();
    public abstract void onClick(int x, int y, InventoryClickEvent event);

    public GuiSlot getSlot(int x, int y) {
        if (x < 0 || x >= 9) {
            throw new IllegalArgumentException("X coordinate must be between 0 and 8.");
        }

        if (y < 0 || y >= height) {
            throw new IllegalArgumentException("Y coordinate must be between 0 and " + (height - 1) + ".");
        }

        SlotKey key = new SlotKey(x, y);
        GuiSlot slot = slotMap.get(key);

        if (slot == null) {
            throw new NoSuchElementException("No slot found for coordinates (" + x + ", " + y + ").");
        }

        return slot;
    }

    public List<GuiSlot> getSlots(int start_x, int start_y, int end_x, int end_y) {
        if (start_x < 0 || start_x >= 9 || end_x < 0 || end_x >= 9 || start_x > end_x) {
            throw new IllegalArgumentException("X coordinates must be between 0 and 8, and start_x should be <= end_x.");
        }
        if (start_y < 0 || start_y >= height || end_y < 0 || end_y >= height || start_y > end_y) {
            throw new IllegalArgumentException("Y coordinates must be between 0 and " + (height - 1) + ", and start_y should be <= end_y.");
        }

        List<GuiSlot> slotsInRange = new ArrayList<>();
        for (int x = start_x; x <= end_x; x++) {
            for (int y = start_y; y <= end_y; y++) {
                slotsInRange.add(getSlot(x, y));
            }
        }
        return slotsInRange;
    }

    public User getViewer() {
        return viewer;
    }

    public List<GuiSlot> getSlotsBorders(int start_x, int start_y, int end_x, int end_y) {
        if (start_x < 0 || start_x >= 9 || end_x < 0 || end_x >= 9 || start_x > end_x) {
            throw new IllegalArgumentException("X coordinates must be between 0 and 8, and start_x should be <= end_x.");
        }
        if (start_y < 0 || start_y >= height || end_y < 0 || end_y >= height || start_y > end_y) {
            throw new IllegalArgumentException("Y coordinates must be between 0 and " + (height - 1) + ", and start_y should be <= end_y.");
        }
        List<GuiSlot> slotsInRange = new ArrayList<>();
        for (int x = start_x+1; x <= end_x-1; x++) {
            slotsInRange.add(getSlot(x, 0));
            slotsInRange.add(getSlot(x, height-1));
        }

        for (int y = start_y; y <= end_y; y++) {
            slotsInRange.add(getSlot(0, y));
            slotsInRange.add(getSlot(8, y));
        }

        return slotsInRange;
    }

    public void defaultBackground() {
        getSlots(0, 0, 8, getHeight()-1).forEach(guiSlot -> {
            guiSlot.fill(Material.STAINED_GLASS_PANE, "§1");
        });
    }

    public String getTitle() {
        return title;
    }

    public int getHeight() {
        return height;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public RiftGuis getGuiType() {
        return RiftGuis.valueOf(getClass().getAnnotation(GuiParams.class).id());
    }

    private static class SlotKey {
        private final int x;
        private final int y;

        public SlotKey(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SlotKey slotKey = (SlotKey) o;
            return x == slotKey.x && y == slotKey.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
