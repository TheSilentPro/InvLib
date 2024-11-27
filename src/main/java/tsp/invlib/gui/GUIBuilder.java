package tsp.invlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.gui.page.Page;

import java.util.ArrayList;

/**
 * @author TheSilentPro (Silent)
 */
public class GUIBuilder {

    private final ArrayList<Page> pages = new ArrayList<>();

    public GUIBuilder page(int index, @NotNull Page page) {
        this.pages.set(index, page);
        return this;
    }

    public GUIBuilder page(@NotNull Page... pages) {
        for (Page page : pages) {
            this.pages.add(this.pages.size(), page);
        }
        return this;
    }

    public GUI build() {
        return new SimpleGUI(pages);
    }

    public InventoryView open(@NotNull Player player, int page) {
        return build().open(player, page);
    }

    public InventoryView open(@NotNull Player player) {
        return open(player, 0);
    }

}