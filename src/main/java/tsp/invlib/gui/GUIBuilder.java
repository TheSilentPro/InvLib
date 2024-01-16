package tsp.invlib.gui;

import org.bukkit.entity.Player;
import tsp.invlib.gui.page.Page;

import java.util.HashMap;

/**
 * @author TheSilentPro (Silent)
 */
public class GUIBuilder {

    private final HashMap<Integer, Page> pages = new HashMap<>();

    public GUIBuilder page(int index, Page page) {
        this.pages.put(index, page);
        return this;
    }

    public GUIBuilder page(Page... pages) {
        for (Page page : pages) {
            this.pages.put(this.pages.size(), page);
        }
        return this;
    }

    public GUI build() {
        return new SimpleGUI(pages);
    }

    public void open(Player player, int page) {
        build().open(player, page);
    }

}