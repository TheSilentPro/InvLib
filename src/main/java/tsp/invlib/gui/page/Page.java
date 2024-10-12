package tsp.invlib.gui.page;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.handler.PageHandler;

import java.util.*;

/**
 * Represents a single page inside a {@link GUI}.
 *
 * @author TheSilentPro (Silent)
 */
public interface Page extends InventoryHolder {

    static PageBuilder newBuilder() {
        return new PageBuilder();
    }

    static PageBuilder newBuilder(Page copy) {
        return new PageBuilder(copy);
    }

    /**
     * The GUI instance that this page belongs to.
     *
     * @return The gui instance.
     */
    GUI getGui();

    /**
     * Sets the GUI instance that this page belongs to.
     *
     * @param gui The gui instance.
     */
    void setGui(GUI gui);

    /**
     * The size of this page.
     * Must be a multiple of 9!
     *
     * @return The page size.
     */
    int getSize();

    void setLimit(int limit);

    int getLimit();

    /**
     * The name of this page.
     *
     * @return The page name.
     */
    String getName();

    /**
     * Sets a {@link Button} at a specific slot in this page.
     *
     * @param slot The slot, starting from 0.
     * @param button The {@link Button}.
     */
    void setButton(int slot, Button button);

    /**
     * Get a {@link Button} at a specific slot in this page.
     *
     * @param slot The slot, starting from 0.
     * @return The {@link Button} if present.
     */
    Optional<Button> getButton(int slot);

    /**
     * Get a {@link Map} of {@link Button buttons} and their slots.
     *
     * @return The map of buttons. Format: Slot, Button
     */
    Map<Integer, Button> getButtons();

    /**
     * Renders this page.
     */
    void reRender();

    /**
     * Open this page for a specific {@link Player}.
     *
     * @param player The player to open it for.
     */
    default InventoryView open(Player player) {
        reRender();
        return player.openInventory(getInventory());
    }

    /**
     * Get a {@link List} of {@link PageHandler PageHandlers} for this page.
     *
     * @return The page handlers.
     */
    default List<PageHandler> getHandlers() {
        return Collections.emptyList();
    }

    /**
     * Get a {@link List} of {@link PageHandler PageHandlers} belonging to a specific type.
     *
     * @param clazz The class.
     * @return The page handlers.
     * @param <T> The type of handlers.
     */
    default <T extends PageHandler> List<T> getHandlers(Class<? extends T> clazz) {
        List<T> result = new ArrayList<>();

        for (PageHandler handler : getHandlers()) {
            if (clazz.isInstance(handler)) {
                //noinspection unchecked
                result.add((T) handler);
            }
        }

        return result;
    }

}
