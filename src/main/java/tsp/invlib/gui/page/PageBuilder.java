package tsp.invlib.gui.page;

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
    private String name = "Page";
    private final HashMap<Integer, Button> buttons = new HashMap<>();
    private final ArrayList<PageHandler> handlers = new ArrayList<>();
    private boolean includeControls = false;

    public PageBuilder(GUI gui) {
        this.gui = gui;
    }

    public PageBuilder() {
        this(null);
    }

    public PageBuilder gui(GUI gui) {
        this.gui = gui;
        return this;
    }

    public PageBuilder rows(int rows) {
        this.rows = rows;
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
        this.buttons.put(this.buttons.size() - 1, button);
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