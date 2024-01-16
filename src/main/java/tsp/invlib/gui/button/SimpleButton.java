package tsp.invlib.gui.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class SimpleButton implements Button, Serializable {

    @Serial
    private static final long serialVersionUID = -4090406724315902075L;

    private final ItemStack item;
    private final Consumer<InventoryClickEvent> clickAction;

    public SimpleButton(ItemStack item, @Nullable Consumer<InventoryClickEvent> clickAction) {
        this.item = item;
        this.clickAction = clickAction;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        getClickAction().ifPresent(a -> a.accept(event));
    }

    @Override
    public Optional<Consumer<InventoryClickEvent>> getClickAction() {
        return Optional.ofNullable(clickAction);
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

}