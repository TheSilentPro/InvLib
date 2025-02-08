package tsp.invlib.page.handler.context;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class PageOpenContext extends PageContext<InventoryOpenEvent> {

    public PageOpenContext(InventoryOpenEvent event, @NotNull Page page) {
        super(event, page);
    }

}