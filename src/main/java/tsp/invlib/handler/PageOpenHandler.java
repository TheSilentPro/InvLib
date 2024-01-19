package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageOpenHandler extends PageHandler {

    /**
     * Fired when a {@link tsp.invlib.gui.page.Page} is opened.
     *
     * @param event The event trigger.
     */
    void onOpen(InventoryOpenEvent event);

}
