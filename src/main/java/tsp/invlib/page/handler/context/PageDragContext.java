package tsp.invlib.page.handler.context;

import org.bukkit.event.inventory.InventoryDragEvent;
import tsp.invlib.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class PageDragContext extends PageContext<InventoryDragEvent> {

    public PageDragContext(InventoryDragEvent event, Page page) {
        super(event, page);
    }

}