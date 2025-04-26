package tsp.invlib.page;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.page.registry.PageRegistry;

/**
 * @author TheSilentPro (Silent)
 */
public class SimplePage extends AbstractPage {

    private final String title;
    private final int size;

    public SimplePage(PageRegistry registry, String title, int rows) {
        super(registry);
        this.title = title;
        this.size = rows * 9;
    }

    public SimplePage(String title, int rows) {
        this(PageRegistry.INSTANCE, title, rows);
    }

    protected SimplePage(SimplePage copy) {
        super(copy);
        this.title = copy.getTitle();
        this.size = copy.getSize();
    }

    @Override
    public @NotNull Inventory createInventory() {
        return Bukkit.createInventory(null, this.size, this.title);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public Page copy() {
        return new SimplePage(this);
    }

}