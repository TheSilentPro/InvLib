package tsp.invlib.handler;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageClickHandler extends PageHandler {

    void onClick(InventoryClickEvent event);

}