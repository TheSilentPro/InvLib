package tsp.invlib.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheSilentPro (Silent)
 */
// TODO: More methods
public class ItemBuilder {

    private Material material;
    private int amount = 1;
    private String name;
    private String displayName;
    private List<String> lore = new ArrayList<>();
    private boolean glow;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            this.name = meta.getItemName();
            this.displayName = meta.getDisplayName();
            this.lore = meta.getLore();
            this.glow = meta.getEnchantmentGlintOverride();
        }
    }

    public ItemBuilder() {}

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder displayName(String name) {
        this.displayName = name;
        return this;
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        for (String line : lore) {
            lore(line);
        }
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder glow(boolean state) {
        this.glow = state;
        return this;
    }

    public ItemBuilder glow() {
        return glow(true);
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (name != null) {
                meta.setItemName(name);
            }
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            meta.setLore(lore);
            meta.setEnchantmentGlintOverride(glow);
            item.setItemMeta(meta);
        }
        return item;
    }

}