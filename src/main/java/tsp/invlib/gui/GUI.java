package tsp.invlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import tsp.invlib.gui.page.Page;

import java.util.List;
import java.util.Optional;

/**
 * Represents a GUI that can hold multiple {@link Page Pages}.
 *
 * @author TheSilentPro (Silent)
 */
public interface GUI {

    /**
     * Set a page at a specific index.
     * Index meaning the page number.
     *
     * @param index The index.
     * @param page The page.
     */
    void setPage(int index, Page page);

    /**
     * Add a {@link Page} to this gui.
     *
     * @param page The page.
     */
    void addPage(Page page);

    /**
     * Get a {@link Page} at a specific index.
     *
     * @param index The page number.
     * @return The page if present.
     */
    Optional<Page> getPage(int index);

    /**
     * Get the {@link List} of {@link Page Pages} this gui has.
     *
     * @return The list of pages.
     */
    List<Page> getPages();

    /**
     * Get the current page this gui is on.
     *
     * @return The current page.
     */
    int getCurrentPage();

    /**
     * Get the next page for this gui.
     *
     * @return The next page.
     */
    int getNextPage();

    /**
     * Get the previous page for this gui.
     *
     * @return The previous page.
     */
    int getPreviousPage();

    /**
     * Set the current page for this gui.
     *
     * @param currentPage The current page.
     */
    void setCurrentPage(int currentPage);

    /**
     * Open this gui for a specific {@link Player} at a specific {@link Page}.
     *
     * @param player The player.
     * @param page The page number. Starting from 0.
     */
    InventoryView open(Player player, int page);

    /**
     * Open this gui for a specific {@link Player} at the starting page.
     *
     * @param player The player.
     */
    default void open(Player player) {
        open(player, 0);
    }

}