package tsp.invlib.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class ButtonBuilder {

    private ItemStack item;
    private Consumer<InventoryClickEvent> clickAction;

    public ButtonBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }

    public ButtonBuilder onClick(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    public Button build() {
        return new SimpleButton(item, clickAction);
    }

}