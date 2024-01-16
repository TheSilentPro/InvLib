package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageCloseHandler extends PageHandler {

    void onClose(InventoryCloseEvent event);

}