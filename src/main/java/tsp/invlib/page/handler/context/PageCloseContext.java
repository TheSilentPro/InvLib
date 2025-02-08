package tsp.invlib.page.handler.context;

import org.bukkit.event.inventory.InventoryCloseEvent;
import tsp.invlib.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class PageCloseContext extends PageContext<InventoryCloseEvent> {

    public PageCloseContext(InventoryCloseEvent event, Page page) {
        super(event, page);
    }

}