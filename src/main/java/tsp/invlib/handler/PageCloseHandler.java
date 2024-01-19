package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageCloseHandler extends PageHandler {

    /**
     * Fired when a {@link tsp.invlib.gui.page.Page} is closed.
     *
     * @param event The event trigger.
     */
    void onClose(InventoryCloseEvent event);

}