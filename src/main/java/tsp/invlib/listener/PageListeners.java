package tsp.invlib.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import tsp.invlib.gui.page.Page;
import tsp.invlib.handler.PageClickHandler;
import tsp.invlib.handler.PageCloseHandler;
import tsp.invlib.handler.PageOpenHandler;

/**
 * Listener class for all {@link Page} related {@link Listener Listeners}.
 *
 * @author TheSilentPro (Silent)
 */
public class PageListeners implements Listener {

    @EventHandler
    public void onPageClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof Page page) {
            page.getHandlers(PageClickHandler.class).forEach(handler -> handler.onClick(event));
            if (!event.isCancelled()) {
                page.getButton(event.getRawSlot()).ifPresent(button -> button.onClick(event));
            }
        }
    }

    @EventHandler
    public void onPageOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof Page page) {
            page.getHandlers(PageOpenHandler.class).forEach(handler -> handler.onOpen(event));
        }
    }

    @EventHandler
    public void onPageClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Page page) {
            page.getHandlers(PageCloseHandler.class).forEach(handler -> handler.onClose(event));
        }
    }

}