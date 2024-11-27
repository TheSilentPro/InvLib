package tsp.invlib.gui.page;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import tsp.invlib.InvLib;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.gui.button.control.ControlButton;
import tsp.invlib.gui.button.control.ControlType;
import tsp.invlib.handler.PageClickHandler;
import tsp.invlib.handler.PageCloseHandler;
import tsp.invlib.handler.PageHandler;
import tsp.invlib.handler.PageOpenHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class PageBuilder {

    private GUI gui;
    private GUI parentGui;
    private int rows = 1;
    private int size;
    private int limit;
    private String name = "Page";
    private HashMap<Integer, Button> buttons = new HashMap<>();
    private ArrayList<PageHandler> handlers = new ArrayList<>();
    private boolean includeControls = false;

    private ControlButton controlBack;
    private ControlButton controlCurrent;
    private ControlButton controlNext;

    public PageBuilder(Page copy) {
        if (copy != null) {
            this.gui = copy.getGui();
            this.rows = copy.getSize() / 9;
            this.size = copy.getSize();
            this.name = copy.getName();
            this.buttons = new HashMap<>(copy.getButtons());
            this.handlers = new ArrayList<>(copy.getHandlers());

            if (copy instanceof SimplePage simpleCopy) {
                this.includeControls = simpleCopy.shouldIncludeControls();
                this.controlBack = simpleCopy.getControlBack();
                this.controlCurrent = simpleCopy.getControlCurrent();
                this.controlNext = simpleCopy.getControlNext();
            }
        }
    }

    public PageBuilder(GUI gui) {
        this.gui = gui;
        this.rows = 6;
        this.size = rows * 9;
        this.name = "Menu";
        this.buttons = new HashMap<>();
        this.handlers = new ArrayList<>();
    }

    public PageBuilder() {}

    public PageBuilder gui(GUI gui) {
        this.gui = gui;
        return this;
    }

    public PageBuilder parentGui(GUI gui) {
        this.parentGui = gui;
        return this;
    }

    public PageBuilder rows(int rows) {
        this.rows = rows;
        this.size = rows * 9;
        return this;
    }

    public PageBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public PageBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PageBuilder button(int slot, Button button) {
        this.buttons.put(slot, button);
        return this;
    }

    public PageBuilder button(Button button) {
        return button(this.buttons.keySet().size() - 1, button);
    }

    public PageBuilder addDefaultControlButtons() {
        this.controlBack = new ControlButton(size - 6, new SimplePage.ItemBuilder()
                .material(Material.ARROW)
                .name(ChatColor.RED + "Back")
                .build(), gui, parentGui, ControlType.BACK);
        this.controlCurrent = new ControlButton(size - 5, new SimplePage.ItemBuilder()
                .material(Material.PAPER)
                .name(ChatColor.GRAY + "Page " + ChatColor.GOLD + (gui.getCurrentPage() + 1) + ChatColor.GRAY + "/" + ChatColor.RED + gui.getPages().size())
                .build(), gui, parentGui, ControlType.CURRENT);
        this.controlNext = new ControlButton(size - 4, new SimplePage.ItemBuilder()
                .material(Material.ARROW)
                .name(ChatColor.GREEN + "Next")
                .build(), gui, parentGui, ControlType.NEXT);
        return this;
    }

    public PageBuilder includeControlButtons(boolean defaults) {
        this.includeControls = true;
        if (defaults) {
            return addDefaultControlButtons();
        }
        return this;
    }

    public PageBuilder includeControlButtons() {
        return includeControlButtons(true);
    }

    public PageBuilder controlBack(Consumer<ControlButton> button) {
        button.accept(controlBack);
        return this;
    }

    public PageBuilder controlCurrent(Consumer<ControlButton> button) {
        button.accept(controlCurrent);
        return this;
    }

    public PageBuilder controlNext(Consumer<ControlButton> button) {
        button.accept(controlNext);
        return this;
    }

    public PageBuilder fill(Button button) {
        for (int i = 0; i < size; i++) {
            if (this.buttons.get(i) == null) {
                button(i, button);
            }
        }
        return this;
    }

    public PageBuilder fillRow(int row, Button button) {
        int index = row * 9;
        for (int i = 0; i < 9; i++) {
            int slot = index + i;
            if (slot < size) {
                if (this.buttons.get(i) == null) {
                    button(slot, button);
                }
            }
        }
        return this;
    }

    public PageBuilder outline(Button button) {
        int rows = size / 9;
        int cols = 9;

        // Fill top
        for (int i = 0; i < cols; i++) {
            if (this.buttons.get(i) == null) {
                button(i, button);
            }
            if (this.buttons.get(rows * 9 - i - 1) == null) {
                button(rows * 9 - i - 1, button);
            }
        }

        // Fill left and right borders (excluding corners)
        for (int i = 1; i < rows - 1; i++) {
            if (this.buttons.get(i) == null) {
                button(i * 9, button);
            }
            if (this.buttons.get(rows * 9 - i - 1) == null) {
                button(i * 9 + cols - 1, button);
            }
        }
        return this;
    }

    public PageBuilder handler(PageHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    public PageBuilder handler(PageHandler... handlers) {
        this.handlers.addAll(List.of(handlers));
        return this;
    }

    public PageBuilder onOpen(PageOpenHandler handler) {
        return handler(handler);
    }

    public PageBuilder onClose(PageCloseHandler handler) {
       return handler(handler);
    }

    public PageBuilder onClick(PageClickHandler handler) {
        return handler(handler);
    }

    public PageBuilder onControlClick(BiConsumer<ControlButton, InventoryClickEvent> event) {
        return onClick(e -> {
            if (buttons.get(e.getRawSlot()) instanceof ControlButton controlButton) {
                event.accept(controlButton, e);
            }
        });
    }

    public PageBuilder preventClick() {
        return onClick(e -> e.setCancelled(true));
    }

    public PageBuilder preventClose() {
        return onClose(e -> InvLib.getPlugin().getServer().getScheduler().runTaskLater(InvLib.getPlugin(), () -> e.getPlayer().openInventory(e.getInventory()), 1L));
    }

    public Page build() {
        Objects.requireNonNull(gui);
        Objects.requireNonNull(name);
        Objects.requireNonNull(buttons);
        Objects.requireNonNull(handlers);
        return new SimplePage(gui, parentGui, rows, limit, name, buttons, includeControls, handlers, controlBack, controlCurrent, controlNext);
    }

}