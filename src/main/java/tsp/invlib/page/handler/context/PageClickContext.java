package tsp.invlib.page.handler.context;

import org.bukkit.event.inventory.InventoryClickEvent;
import tsp.invlib.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class PageClickContext extends PageContext<InventoryClickEvent> {

    public PageClickContext(InventoryClickEvent event, Page page) {
        super(event, page);
    }

}