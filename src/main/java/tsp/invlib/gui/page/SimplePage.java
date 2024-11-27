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

    public void setControlBack(ControlButton controlBack) {
        this.controlBack = controlBack;
    }

    public void setControlCurrent(ControlButton controlCurrent) {
        this.controlCurrent = controlCurrent;
    }

    public void setControlNext(ControlButton controlNext) {
        this.controlNext = controlNext;
    }

    public boolean shouldIncludeControls() {
        return includeControls;
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
        getInventory().clear();

        if (includeControls) {
            if (gui.getPreviousPage() >= 0 && gui.getCurrentPage() > 0) {
                if (controlBack != null) {
                    controlBack.setItem(new ItemBuilder(controlBack.getItem()).lore(ChatColor.GRAY + "Brings you back to page " + ChatColor.RED + (gui.getPreviousPage() + 1)).build());
                    setButton(controlBack.getSlot(), controlBack);
                }
            }
            if (controlCurrent != null) {
                controlCurrent.setItem(new ItemBuilder(controlCurrent.getItem())
                        .name(ChatColor.GRAY + "Page " + ChatColor.GOLD + (gui.getCurrentPage() + 1) + ChatColor.GRAY + "/" + ChatColor.RED + gui.getPages().size())
                        .lore(ChatColor.GRAY + "Click to go to the previous menu.")
                        .build()
                );
                setButton(controlCurrent.getSlot(), controlCurrent);
            }
            if (getGui().getCurrentPage() < getGui().getPages().size() - 1) {
                if (controlNext != null) {
                    controlNext.setItem(new ItemBuilder(controlNext.getItem()).lore(ChatColor.GRAY + "Brings you to page " + ChatColor.GREEN + (gui.getCurrentPage() + 2)).build());
                    setButton(controlNext.getSlot(), controlNext);
                }
            }
        }

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
        private List<String> lore = new ArrayList<>();

        public ItemBuilder(ItemStack source) {
            this.material = source.getType();
            //noinspection DataFlowIssue
            this.name = source.getItemMeta().getDisplayName();
            this.lore = source.getItemMeta().getLore() != null ? source.getItemMeta().getLore() : new ArrayList<>();
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

        public ItemBuilder lore(String line) {
            if (this.lore.isEmpty()) {
                this.lore.add(line);
            } else {
                this.lore.set(0, line);
            }
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