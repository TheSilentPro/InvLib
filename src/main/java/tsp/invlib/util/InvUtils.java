package tsp.invlib.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

/**
 * @author TheSilentPro (Silent)
 */
public final class InvUtils {

    private InvUtils() {
        throw new IllegalStateException("Utility class must not be initialized.");
    }

    public static void fill(@NotNull Inventory inventory, @NotNull ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
            }
        }
    }

    public static void fillRow(@NotNull Inventory inventory, @NotNull ItemStack item, int row) {
        int index = row * 9;
        for (int i = 0; i < 9; i++) {
            int slot = index + i;
            if (slot < inventory.getSize()) {
                setItem(inventory, slot, item);
            }
        }
    }

    public static void outline(@NotNull Inventory inventory, @NotNull ItemStack item) {
        int rows = inventory.getSize() / 9;
        int cols = 9;

        // Fill top
        for (int i = 0; i < cols; i++) {
            setItem(inventory, i, item);
            setItem(inventory, rows * 9 - i - 1, item);
        }

        // Fill left and right borders (excluding corners)
        for (int i = 1; i < rows - 1; i++) {
            setItem(inventory, i * 9, item);
            setItem(inventory, i * 9 + cols - 1, item);
        }
    }

    public static void border(@NotNull Inventory inventory, @NotNull ItemStack item) {
        outline(inventory, item);
    }

    private static void setItem(Inventory inventory, int slot, ItemStack item) {
        if (item == null || item.getType().isItem()) {
            inventory.setItem(slot, item);
        }
    }

}