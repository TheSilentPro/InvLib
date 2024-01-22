package tsp.invlib.gui.page;

import org.bukkit.Bukkit;
import tsp.invlib.InvLib;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.handler.PageClickHandler;
import tsp.invlib.handler.PageCloseHandler;
import tsp.invlib.handler.PageHandler;
import tsp.invlib.handler.PageOpenHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author TheSilentPro (Silent)
 */
public class PageBuilder {

    private GUI gui;
    private int rows = 1;
    private int size;
    private String name = "Page";
    private HashMap<Integer, Button> buttons = new HashMap<>();
    private ArrayList<PageHandler> handlers = new ArrayList<>();
    private boolean includeControls = false;

    public PageBuilder(Page copy) {
        if (copy != null) {
            this.gui = copy.getGui();
            this.rows = copy.getSize() / 9;
            this.size = copy.getSize();
            this.name = copy.getName();
            this.buttons = new HashMap<>(copy.getButtons());
            this.handlers = new ArrayList<>(copy.getHandlers());
        }
    }

    public PageBuilder(GUI gui) {
        this.gui = gui;
    }

    public PageBuilder() {
        this((GUI) null);
    }

    public PageBuilder gui(GUI gui) {
        this.gui = gui;
        return this;
    }

    public PageBuilder rows(int rows) {
        this.rows = rows;
        this.size = rows * 9;
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

    public PageBuilder fill(Button button) {
        Bukkit.broadcastMessage("fill called! Size: " + size);
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

    public PageBuilder includeControlButtons() {
        this.includeControls = true;
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

    public PageBuilder preventClick() {
        return onClick(e -> e.setCancelled(true));
    }

    public PageBuilder preventClose() {
        return onClose(e -> InvLib.getPlugin().getServer().getScheduler().runTaskLater(InvLib.getPlugin(), () -> e.getPlayer().openInventory(e.getInventory()), 1L));
    }

    public Page build() {
        return new SimplePage(gui, rows, name, buttons, includeControls, handlers);
    }

}