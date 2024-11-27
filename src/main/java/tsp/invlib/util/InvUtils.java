package tsp.invlib.util;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author TheSilentPro (Silent)
 */
public final class InvUtils {

    private InvUtils() {
        throw new IllegalStateException("Utility class must not be initialized.");
    }

    public static void fill(@Nonnull Inventory inventory, @Nonnull ItemStack item) {
        Preconditions.checkNotNull(inventory, "Inventory must not be null!");
        Preconditions.checkNotNull(item, "Item must not be null!");
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
            }
        }
    }

    public static void fillRow(@Nonnull Inventory inventory, @Nonnull ItemStack item, int row) {
        Preconditions.checkNotNull(inventory, "Inventory must not be null!");
        Preconditions.checkNotNull(item, "Item must not be null!");
        int index = row * 9;
        for (int i = 0; i < 9; i++) {
            int slot = index + i;
            if (slot < inventory.getSize()) {
                setItem(inventory, slot, item);
            }
        }
    }

    public static void outline(@Nonnull Inventory inventory, @Nonnull ItemStack item) {
        Preconditions.checkNotNull(inventory, "Inventory must not be null!");
        Preconditions.checkNotNull(item, "Item must not be null!");

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

    public static void border(@Nonnull Inventory inventory, @Nonnull ItemStack item) {
        outline(inventory, item);
    }

    private static void setItem(Inventory inventory, int slot, ItemStack item) {
        if (item == null || item.getType().isItem()) {
            inventory.setItem(slot, item);
        }
    }

}