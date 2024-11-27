package tsp.invlib.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class ButtonBuilder {

    private ItemStack item;
    private Consumer<InventoryClickEvent> clickAction;

    @NotNull
    public ButtonBuilder item(@NotNull ItemStack item) {
        this.item = item;
        return this;
    }

    @NotNull
    public ButtonBuilder onClick(@NotNull Consumer<@NotNull InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    @NotNull
    public Button build() {
        return new SimpleButton(item, clickAction);
    }

}