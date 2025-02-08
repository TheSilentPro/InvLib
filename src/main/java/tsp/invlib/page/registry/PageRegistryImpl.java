package tsp.invlib.page.registry;

import org.bukkit.inventory.InventoryView;
import tsp.invlib.page.Page;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author TheSilentPro (Silent)
 */
public class PageRegistryImpl implements PageRegistry {

    private final Map<InventoryView, Page> pages;

    public PageRegistryImpl() {
        this.pages = new HashMap<>();
    }

    @Override
    public void register(InventoryView view, Page page) {
        this.pages.put(view, page);
    }

    @Override
    public Optional<Page> get(InventoryView view) {
        return Optional.ofNullable(this.pages.get(view));
    }

    @Override
    public void remove(InventoryView view) {
        this.pages.remove(view);
    }

    @Override
    public Map<InventoryView, Page> getPages() {
        return Collections.unmodifiableMap(pages);
    }

}