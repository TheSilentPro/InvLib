package tsp.invlib.page;

import tsp.invlib.gui.GUI;
import tsp.invlib.page.controllable.PaginatedControllable;
import tsp.invlib.page.registry.PageRegistry;

/**
* @author TheSilentPro (Silent)
*/
public class PaginatedSimplePage extends SimplePage implements PaginatedControllable {

    private final GUI<Integer> gui;
    private final int backSlot;
    private final int currentSlot;
    private final int nextSlot;

    public PaginatedSimplePage(PageRegistry registry, GUI<Integer> gui, String title, int rows, int backSlot, int currentSlot, int nextSlot) {
        super(registry, title, rows);
        this.gui = gui;
        this.backSlot = backSlot;
        this.currentSlot = currentSlot;
        this.nextSlot = nextSlot;
    }

    public PaginatedSimplePage(GUI<Integer> gui, String title, int rows, int backSlot, int currentSlot, int nextSlot) {
        this(PageRegistry.INSTANCE, gui, title, rows, backSlot, currentSlot, nextSlot);
    }

    protected PaginatedSimplePage(PaginatedSimplePage copy) {
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
        return new PaginatedSimplePage(this);
    }

}