package tsp.invlib.page;

import org.bukkit.event.inventory.InventoryType;
import tsp.invlib.gui.GUI;
import tsp.invlib.page.controllable.PaginatedControllable;
import tsp.invlib.page.registry.PageRegistry;

/**
 * @author TheSilentPro (Silent)
 */
public class PaginatedComplexPage extends ComplexPage implements PaginatedControllable {

    private final GUI<Integer> gui;
    private final int backSlot;
    private final int currentSlot;
    private final int nextSlot;

    public PaginatedComplexPage(PageRegistry registry, GUI<Integer> gui, InventoryType type, String title, int backSlot, int currentSlot, int nextSlot) {
        super(registry, type, title);
        this.gui = gui;
        this.backSlot = backSlot;
        this.currentSlot = currentSlot;
        this.nextSlot = nextSlot;
    }

    public PaginatedComplexPage(GUI<Integer> gui, InventoryType type, String title, int backSlot, int currentSlot, int nextSlot) {
        this(null, gui, type, title, backSlot, currentSlot, nextSlot);
    }

    protected PaginatedComplexPage(PaginatedComplexPage copy) {
        super(copy);
        this.gui = copy.getGui();
        this.backSlot = copy.getBackSlot();
        this.currentSlot = copy.getCurrentSlot();
        this.nextSlot = copy.getNextSlot();
    }

    @Override
    public GUI<Integer> getGui() {
        return this.gui;
    }

    @Override
    public int getBackSlot() {
        return this.backSlot;
    }

    @Override
    public int getCurrentSlot() {
        return this.currentSlot;
    }

    @Override
    public int getNextSlot() {
        return this.nextSlot;
    }

    @Override
    public Page copy() {
        return new PaginatedComplexPage(this);
    }

}