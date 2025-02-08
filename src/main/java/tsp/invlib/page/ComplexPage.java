package tsp.invlib.page;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.page.registry.PageRegistry;

/**
 * @author TheSilentPro (Silent)
 */
public class ComplexPage extends AbstractPage {

    private final InventoryType type;
    private final String title;

    public ComplexPage(PageRegistry registry, InventoryType type, String title) {
        super(registry);
        this.type = type;
        this.title = title;
    }

    public ComplexPage(InventoryType type, String title) {
        this(null, type, title);
    }

    protected ComplexPage(ComplexPage copy) {
        super(copy);
        this.type = copy.getType();
        this.title = copy.getTitle();
    }

    @Override
    public @NotNull Inventory createInventory() {
        return Bukkit.createInventory(null, this.type, this.title);
    }

    public InventoryType getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return this.type.getDefaultSize();
    }

    @Override
    public Page copy() {
        return new ComplexPage(this);
    }

}