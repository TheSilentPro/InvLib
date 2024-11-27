package tsp.invlib.gui.button;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A simple {@link Button} implementation.
 *
 * @author TheSilentPro (Silent)
 */
public class SimpleButton implements Button, Serializable {

    @Serial
    private static final long serialVersionUID = -4090406724315902075L;

    private ItemStack item;
    private Consumer<InventoryClickEvent> clickAction;

    public SimpleButton(@NotNull ItemStack item, @Nullable Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
    }

    public SimpleButton(@NotNull ItemStack item) {
        this(item, null);
    }

    public SimpleButton(@NotNull Material material) {
        this(new ItemStack(material));
    }

    public SimpleButton setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public SimpleButton setClickAction(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
        return this;
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