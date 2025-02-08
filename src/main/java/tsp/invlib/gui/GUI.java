package tsp.invlib.gui;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import tsp.invlib.gui.registry.GUIRegistry;
import tsp.invlib.page.Page;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents a graphical user interface (GUI) that manages pages identified by an index of type {@code T}.
 * <p>
 * This interface defines methods for opening the GUI for players, retrieving and updating pages,
 * and accessing the unique key and registry associated with this GUI.
 * </p>
 *
 * @param <T> the type used to index and identify pages in the GUI
 *
 * @author TheSilentPro
 */
public interface GUI<T> {

    /**
     * Opens the GUI for the specified player and displays the page associated with the given index.
     *
     * @param player the player for whom the GUI is to be opened
     * @param index  the index identifying the page to open
     */
    void open(Player player, T index);

    /**
     * Gets the unique {@link NamespacedKey} associated with this GUI.
     *
     * @return the unique key for this GUI
     */
    NamespacedKey getKey();

    /**
     * Retrieves the page associated with the specified index.
     *
     * @param index the index identifying the page
     * @return an {@link Optional} containing the page if it exists, or an empty {@link Optional} otherwise
     */
    Optional<Page> getPage(T index);

    /**
     * Updates the page associated with the specified index by applying the given consumer.
     *
     * @param index the index identifying the page to update
     * @param page  a {@link Consumer} that applies updates to the page
     */
    void updatePage(T index, Consumer<Page> page);

    /**
     * Sets the page for the specified index.
     *
     * @param index the index identifying where to set the page
     * @param page  the page to set
     */
    void setPage(T index, Page page);

    /**
     * Retrieves a map of all pages in this GUI.
     *
     * @return a map where keys are indices of type {@code T} and values are the corresponding pages
     */
    Map<T, Page> getPages();

    /**
     * Retrieves the {@link GUIRegistry} associated with this GUI.
     *
     * @return the GUI registry
     */
    GUIRegistry<T> getGuiRegistry();
}