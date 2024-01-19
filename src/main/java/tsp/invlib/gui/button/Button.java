package tsp.invlib.gui.button;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents a "button" inside a {@link tsp.invlib.gui.page.Page}.
 * A button is essentially an item that fires an {@link InventoryClickEvent} when clicked.
 *
 * @author TheSilentPro (Silent)
 */
public interface Button {

    /**
     * Default AIR item.
     */
    ItemStack AIR = new ItemStack(Material.AIR);

    /**
     * The item represented by this button.
     * This can be air, but <bold>NOT</bold> null!
     *
     * @return The item.
     */
    default ItemStack getItem() {
        return AIR;
    }

    /**
     * The action to run when the button is clicked.
     *
     * @return The click action.
     */
    default Optional<Consumer<InventoryClickEvent>> getClickAction() {
        return Optional.empty();
    }

    /**
     * Called when the {@link Button} is clicked.
     *
     * @param event The event that triggered this button.
     */
    void onClick(InventoryClickEvent event);

}