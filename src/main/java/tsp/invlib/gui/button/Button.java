package tsp.invlib.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public interface Button {

    ItemStack getItem();

    default Optional<Consumer<InventoryClickEvent>> getClickAction() {
        return Optional.empty();
    }

    void onClick(InventoryClickEvent event);

}