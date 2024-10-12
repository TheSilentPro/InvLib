package tsp.invlib.gui.page;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.gui.button.control.ControlButton;
import tsp.invlib.gui.button.control.ControlType;
import tsp.invlib.handler.PageHandler;

import javax.annotation.Nonnull;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public class SimplePage implements Page, Serializable {

    @Serial
    private static final long serialVersionUID = -2303582995747170968L;

    private GUI gui;
    private final int size;
    private int limit;
    private final String name;
    private final HashMap<Integer, Button> buttons;
    private final ArrayList<PageHandler> handlers;
    private final Inventory inventory;
    private final boolean includeControls;

    private ControlButton controlBack;
    private ControlButton controlCurrent;
    private ControlButton controlNext;

    public SimplePage(
            @Nonnull GUI gui,
            int rows,
            int limit,
            @Nonnull String name,
            @Nonnull Map<Integer, Button> buttons,
            boolean includeControls,
            @Nonnull List<PageHandler> handlers
    ) {
        this.gui = gui;
        this.size = rows * 9;
        this.limit = limit != -1 ? limit : size;
        this.name = Preconditions.checkNotNull(name, "Name must not be null!");
        this.buttons = new HashMap<>(Preconditions.checkNotNull(buttons, "Buttons map must not be null!"));
        this.handlers = new ArrayList<>(Preconditions.checkNotNull(handlers, "Handlers list must not be null!"));
        this.inventory = Bukkit.createInventory(this, size, name);
        this.includeControls = includeControls;
    }

    @Override
    public GUI getGui() {
        return gui;
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setButton(int slot, Button button) {
        this.buttons.put(slot, button);
    }

    @Override
    public Optional<Button> getButton(int slot) {
        return Optional.ofNullable(this.buttons.get(slot));
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return Collections.unmodifiableMap(buttons);
    }

    @Override
    public void reRender() {
        // Add controls
        if (includeControls) {
            // back
            if (controlBack == null && gui.getCurrentPage() - 1 > -1) {
                controlBack = new ControlButton(size - 6, new ItemBuilder()
                        .material(Material.ARROW)
                        .name(ChatColor.RED + "Back")
                        .lore(ChatColor.GRAY + "Brings you back to page " + ChatColor.RED + (gui.getPreviousPage() + 1)) // +1 for human number
                        .build(), this, ControlType.BACK);
            }
            if (controlBack != null) {
                setButton(controlBack.getSlot(), controlBack);
            }

            // current
            int totalPages = gui.getPages().size(); // Ensure this is correct
            if (controlCurrent == null) {
                controlCurrent = new ControlButton(size - 5, new ItemBuilder()
                        .material(Material.PAPER)
                        .name(ChatColor.GOLD + "Page " +
                                (gui.getCurrentPage() + 1) + // +1 for human-readable
                                ChatColor.GRAY + "/" +
                                ChatColor.RED + totalPages)
                        .build(), this, ControlType.CURRENT);
            }
            setButton(controlCurrent.getSlot(), controlCurrent);

            // next
            if (controlNext == null && gui.getCurrentPage() + 1 < totalPages) {  // Fix off-by-one
                controlNext = new ControlButton(size - 4, new ItemBuilder()
                        .material(Material.ARROW)
                        .name(ChatColor.GREEN + "Next")
                        .lore(ChatColor.GRAY + "Brings you to page " + ChatColor.GREEN + (gui.getNextPage() + 1)) // +1 for human-readable
                        .build(), this, ControlType.NEXT);
            }
            if (controlNext != null) {
                setButton(controlNext.getSlot(), controlNext);
            }
        }

        // Update inventory
        inventory.clear();
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            Button button = entry.getValue();
            if (button != null) {
                inventory.setItem(entry.getKey(), button.getItem());
            }
        }
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public List<PageHandler> getHandlers() {
        return handlers;
    }

    private static class ItemBuilder {

        private Material material;
        private String name;
        private final List<String> lore = new ArrayList<>();

        public ItemBuilder material(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder lore(String line) {
            this.lore.add(line);
            return this;
        }

        public ItemStack build() {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                throw new IllegalArgumentException("Item can not have meta!");
            }

            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

    }

}