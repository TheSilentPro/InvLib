package tsp.invlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import tsp.invlib.gui.page.Page;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public class SimpleGUI implements GUI, Serializable {

    @Serial
    private static final long serialVersionUID = 4823345690315125419L;

    private final GUI parentGui;
    private final ArrayList<Page> pages;
    private int currentPage;

    public SimpleGUI(@Nullable GUI parentGui, @NotNull ArrayList<Page> pages) {
        this.pages = pages;
        this.parentGui = parentGui;
    }

    public SimpleGUI(List<Page> pages) {
        this(null, new ArrayList<>(pages));
    }

    public SimpleGUI() {
        this(new ArrayList<>());
    }

    @Nullable
    @Override
    public GUI getParentGui() {
        return parentGui;
    }

    /**
     * This method is currently not implemented and will throw an exception.
     *
     * @param index The index.
     * @param page The page.
     * @deprecated Not implemented yet.
     */
    @Deprecated
    @Override
    public void setPage(int index, Page page) {
        throw new UnsupportedOperationException("Not implemented! Use GUI#addPage(page); instead.");
        //this.pages.set(index, page);
    }

    @Override
    public SimpleGUI addPage(Page page) {
        if (page.getGui() == null) {
            page.setGui(this);
        }
        if (page.getParentGui() == null) {
            page.setParentGui(parentGui);
        }
        this.pages.add(page);
        return this;
    }

    @Override
    public Optional<Page> getPage(int index) {
        return Optional.ofNullable(this.pages.get(index));
    }

    @Override
    public List<Page> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return Math.min(getCurrentPage() + 1, this.pages.size() - 1);
    }

    public int getPreviousPage() {
        return Math.max(getCurrentPage() - 1, 0); // Corrected logic
    }

    @Override
    public InventoryView open(Player player, int page) {
        Page p = pages.get(page);
        if (p == null) {
            throw new IllegalArgumentException("Page " + page + " does not exist!");
        }

        setCurrentPage(page);
        return p.open(player);
    }

}
