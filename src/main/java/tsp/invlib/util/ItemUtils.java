package tsp.invlib.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * @author TheSilentPro (Silent)
 */
// TODO: More utility methods...
public final class ItemUtils {

    private ItemUtils() {
        throw new IllegalStateException("Utility class must not be initialized.");
    }

    public static ItemStack createItem(Material material, int amount) {
        if (material == null || amount <= 0) {
            throw new IllegalArgumentException("Material cannot be null and amount must be greater than zero.");
        }
        return new ItemStack(material, amount);
    }

    public static ItemStack setDisplayName(ItemStack item, String displayName) {
        if (item == null || displayName == null) {
            throw new IllegalArgumentException("Item and display name cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack setLore(ItemStack item, List<String> lore) {
        if (item == null || lore == null) {
            throw new IllegalArgumentException("Item and lore cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack addEnchantments(ItemStack item, Map<Enchantment, Integer> enchantments) {
        if (item == null || enchantments == null) {
            throw new IllegalArgumentException("Item and enchantments cannot be null.");
        }
        enchantments.forEach(item::addUnsafeEnchantment);
        return item;
    }

    public static ItemStack removeEnchantment(ItemStack item, Enchantment enchantment) {
        if (item == null || enchantment == null) {
            throw new IllegalArgumentException("Item and enchantment cannot be null.");
        }
        item.removeEnchantment(enchantment);
        return item;
    }

    public static ItemStack setCustomModelData(ItemStack item, Integer data) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setCustomModelData(data);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack setDamage(ItemStack item, int damage) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (!(item.getItemMeta() instanceof Damageable meta)) {
            throw new IllegalArgumentException("Item is not damageable.");
        }
        meta.setDamage(damage);
        item.setItemMeta(meta);
        return item;
    }

    public static int getDamage(ItemStack item) {
        if (item == null || !(item.getItemMeta() instanceof Damageable meta)) {
            return 0;
        }
        return meta.getDamage();
    }

    public static ItemStack setUnbreakable(ItemStack item, boolean unbreakable) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(unbreakable);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Add item flags
    public static ItemStack addItemFlags(ItemStack item, Set<ItemFlag> flags) {
        if (item == null || flags == null) {
            throw new IllegalArgumentException("Item and flags cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(flags.toArray(new org.bukkit.inventory.ItemFlag[0]));
            item.setItemMeta(meta);
        }
        return item;
    }

    public static ItemStack removeItemFlags(ItemStack item, Set<org.bukkit.inventory.ItemFlag> flags) {
        if (item == null || flags == null) {
            throw new IllegalArgumentException("Item and flags cannot be null.");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.removeItemFlags(flags.toArray(new org.bukkit.inventory.ItemFlag[0]));
            item.setItemMeta(meta);
        }
        return item;
    }

    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR || item.getAmount() == 0;
    }

    public static ItemStack translateMeta(ItemMeta meta, @NotNull ItemStack item, UnaryOperator<String> translation) {
        if (meta == null) {
            return item;
        }

        meta.setItemName(translation.apply(meta.getItemName()));

        if (meta.hasLore() && meta.getLore() != null) {
            List<String> lore = meta.getLore();
            lore.replaceAll(translation);
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack translateMeta(@NotNull ItemStack item, UnaryOperator<String> translation) {
        return translateMeta(item.getItemMeta(), item, translation);
    }

    // Indexes are +1 since pages actually start from 0.
    public static ItemStack translateItem(@NotNull ItemStack item, int currentPage, int max) {
        return translateMeta(
                item, str -> str
                .replace("${{BACK}}", String.valueOf((currentPage - 1) + 1))
                .replace("${{CURRENT}}", String.valueOf((currentPage) + 1))
                .replace("${{NEXT}}", String.valueOf((currentPage + 1) + 1))
                .replace("${{MAX}}", String.valueOf(max)
                )
        );
    }

}
