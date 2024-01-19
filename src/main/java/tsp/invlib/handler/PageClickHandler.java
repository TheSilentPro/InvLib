package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageClickHandler extends PageHandler {

    /**
     * Fired when a {@link tsp.invlib.gui.page.Page} is clicked.
     *
     * @param event The event trigger.
     */
    void onClick(InventoryClickEvent event);

}