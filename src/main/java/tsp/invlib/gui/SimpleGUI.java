package tsp.invlib.gui;

import org.bukkit.entity.Player;
import tsp.invlib.gui.page.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public class SimpleGUI implements GUI, Serializable {

    @Serial
    private static final long serialVersionUID = 4823345690315125419L;

    private final ArrayList<Page> pages;

    private int currentPage;

    public SimpleGUI(List<Page> pages) {
        this.pages = new ArrayList<>(pages);
    }

    public SimpleGUI() {
        this.pages = new ArrayList<>();
    }

    @Override
    public void setPage(int index, Page page) {
        throw new UnsupportedOperationException("Not implemented");
        //this.pages.put(index, page);
    }

    @Override
    public void addPage(Page page) {
        if (page.getGui() == null) {
            page.setGui(this);
        }
        this.pages.add(page);
        //this.pages.put(this.pages.keySet().size() == 0 ? 0 : this.pages.keySet().size() + 1, page);
    }

    @Override
    public Optional<Page> getPage(int index) {
        return Optional.ofNullable(this.pages.get(index));
    }

    @Override
    public List<Page> getPages() {
        return Collections.unmodifiableList(pages);
    }

    // 0 based
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return Math.min(getCurrentPage() + 1, this.pages.size());
    }

    @Override
    public int getPreviousPage() {
        return Math.max(getCurrentPage() - 1, 0);
    }

    @Override
    public void open(Player player, int page) {
        Page p = pages.get(page);
        if (p == null) {
            throw new IllegalArgumentException("Page " + page + " does not exist!");
        }

        setCurrentPage(page);
        p.open(player);
    }

}
