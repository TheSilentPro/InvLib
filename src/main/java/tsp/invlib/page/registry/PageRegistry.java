package tsp.invlib.page.registry;

import org.bukkit.inventory.InventoryView;
import tsp.invlib.page.Page;

import java.util.Map;
import java.util.Optional;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageRegistry {

    PageRegistry INSTANCE = newRegistry();

    static PageRegistryImpl newRegistry() {
        return new PageRegistryImpl();
    }

    void register(InventoryView view, Page page);

    Optional<Page> get(InventoryView view);

    void remove(InventoryView view);

    Map<InventoryView, Page> getPages();

}