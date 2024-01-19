package tsp.invlib.gui;

import org.bukkit.entity.Player;
import tsp.invlib.gui.page.Page;

import java.util.ArrayList;

/**
 * @author TheSilentPro (Silent)
 */
public class GUIBuilder {

    private final ArrayList<Page> pages = new ArrayList<>();

    public GUIBuilder page(int index, Page page) {
        this.pages.set(index, page);
        return this;
    }

    public GUIBuilder page(Page... pages) {
        for (Page page : pages) {
            this.pages.add(this.pages.size(), page);
        }
        return this;
    }

    public GUI build() {
        return new SimpleGUI(pages);
    }

    public void open(Player player, int page) {
        build().open(player, page);
    }

    public void open(Player player) {
        open(player, 0);
    }

}