package tsp.invlib.gui;

import org.bukkit.entity.Player;
import tsp.invlib.gui.page.Page;

import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public interface GUI {

    void setPage(int index, Page page);

    void addPage(Page page);

    Optional<Page> getPage(int index);

    List<Page> getPages();

    int getCurrentPage();

    int getNextPage();

    int getPreviousPage();

    void setCurrentPage(int currentPage);

    void open(Player player, int page);

    default void open(Player player) {
        open(player, 0);
    }

}