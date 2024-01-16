package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageOpenHandler extends PageHandler {

    void onOpen(InventoryOpenEvent event);

}
