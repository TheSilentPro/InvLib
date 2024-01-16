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

    private final HashMap<Integer, Page> pages;

    public SimpleGUI(Map<Integer, Page> pages) {
        this.pages = new HashMap<>(pages);
    }

    @Override
    public void setPage(int index, Page page) {
        this.pages.put(index, page);
    }

    @Override
    public Optional<Page> getPage(int index) {
        return Optional.ofNullable(this.pages.get(index));
    }

    @Override
    public Map<Integer, Page> getPages() {
        return Collections.unmodifiableMap(pages);
    }

    @Override
    public void open(Player player, int page) {
        Page p = pages.get(page);
        if (p == null) {
            throw new IllegalArgumentException("Page " + page + " does not exist!");
        }

        p.open(player);
    }

}
