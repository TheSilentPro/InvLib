package tsp.invlib.gui.page;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.gui.button.control.ControlButton;
import tsp.invlib.handler.PageHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public class SimplePage implements Page, Serializable {

    @Serial
    private static final long serialVersionUID = -2303582995747170968L;

    private @Nonnull GUI gui;
    private @Nullable GUI parentGui;
    private final int size;
    private int limit;
    private @Nonnull final String name;
    private @Nonnull final HashMap<Integer, Button> buttons;
    private @Nonnull final ArrayList<PageHandler> handlers;
    private @Nonnull final Inventory inventory;
    private boolean includeControls;

    private ControlButton controlBack;
    private ControlButton controlCurrent;
    private ControlButton controlNext;

    public SimplePage(
            @Nonnull GUI gui,
            @Nullable GUI parentGui,
            int rows,
            int limit,
            @Nonnull String name,
            @Nonnull Map<Integer, Button> buttons,
            boolean includeControls,
            @Nonnull List<PageHandler> handlers,
            ControlButton controlBack,
            ControlButton controlCurrent,
            ControlButton controlNext
    ) {
        this.gui = gui;
        this.parentGui = parentGui;
        this.size = rows * 9;
        this.limit = limit != -1 ? limit : size;
        this.name = Preconditions.checkNotNull(name, "Name must not be null!");
        this.buttons = new HashMap<>(Preconditions.checkNotNull(buttons, "Buttons map must not be null!"));
        this.handlers = new ArrayList<>(Preconditions.checkNotNull(handlers, "Handlers list must not be null!"));
        this.inventory = Bukkit.createInventory(this, size, name);
        this.includeControls = includeControls;
        this.controlBack = controlBack;
        this.controlCurrent = controlCurrent;
        this.controlNext = controlNext;
    }

    @Nonnull
    @Override
    public GUI getGui() {
        return gui;
    }

    @Override
    public void setGui(@Nonnull GUI gui) {
        this.gui = gui;
    }

    @Nullable
    @Override
    public GUI getParentGui() {
        return parentGui;
    }

    @Override
    public void setParentGui(@Nullable GUI parentGui) {
        this.parentGui = parentGui;
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

    @Nonnull
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

    public void setIncludeControls(boolean includeControls) {
        this.includeControls = includeControls;
    }

    public boolean shouldIncludeControls() {
        return includeControls;
    }

    public void setControlBack(ControlButton controlBack) {
        this.controlBack = controlBack;
    }

    public void setControlCurrent(ControlButton controlCurrent) {
        this.controlCurrent = controlCurrent;
    }

    public void setControlNext(ControlButton controlNext) {
        this.controlNext = controlNext;
    }

    public ControlButton getControlBack() {
        return controlBack;
    }

    public ControlButton getControlCurrent() {
        return controlCurrent;
    }

    public ControlButton getControlNext() {
        return controlNext;
    }

    @Override
    public void reRender() {
        // Add controls dynamically based on current page
        if (shouldIncludeControls()) {
            // Back button
            if (getGui().getPreviousPage() >= 0 && getGui().getCurrentPage() > 0) {
                setButton(getControlBack().getSlot(), getControlBack());
            }

            // Current page button
            setButton(getControlCurrent().getSlot(), getControlCurrent());

            // Next button
            if (getGui().getCurrentPage() < getGui().getPages().size() - 1) {
                setButton(getControlNext().getSlot(), getControlNext());
            }
        }

        // Update inventory
        getInventory().clear();
        for (Map.Entry<Integer, Button> entry : getButtons().entrySet()) {
            Button button = entry.getValue();
            if (button != null) {
                getInventory().setItem(entry.getKey(), button.getItem());
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

    protected static class ItemBuilder {

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