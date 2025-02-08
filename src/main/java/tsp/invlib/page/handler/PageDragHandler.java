package tsp.invlib.page.handler;

import org.bukkit.event.inventory.InventoryDragEvent;
import tsp.invlib.page.handler.context.PageDragContext;

/**
 * @see InventoryDragEvent
 * @author TheSilentPro (Silent)
 */
public interface PageDragHandler extends PageHandler {

    void onDrag(PageDragContext ctx);

}
