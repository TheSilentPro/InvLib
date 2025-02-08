package tsp.invlib.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.animation.Animatable;
import tsp.invlib.page.Page;
import tsp.invlib.page.handler.context.ButtonClickContext;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents an interactive inventory item (button) that can handle events.
 * <p>
 * A Button is used within a GUI to represent a clickable item that can react to user interactions.
 * It supports dynamic rendering and can be animated through the {@link Animatable} interface.
 * </p>
 *
 * @author TheSilentPro
 */
public interface Button extends Animatable {

    /**
     * Retrieves the {@link ItemStack} associated with this button.
     *
     * @return an {@link Optional} containing the {@link ItemStack} if it is set,
     *         or an empty {@link Optional} if no item is associated.
     */
    Optional<ItemStack> getItem();

    /**
     * Retrieves the context handler responsible for processing click events on this button.
     *
     * @return a {@link Consumer} that accepts a {@link ButtonClickContext} to handle click events.
     */
    Consumer<ButtonClickContext> getContextHandler();

    /**
     * Checks whether this button is dynamic.
     * <p>
     * Dynamic buttons are rendered each time the page is opened, allowing for changes on each view.
     * Non-dynamic buttons are rendered only once during {@link Page#render()}.
     * </p>
     *
     * @return {@code true} if the button is dynamic, {@code false} otherwise.
     */
    boolean isDynamic();

    /**
     * Sets whether this button should be dynamic.
     * <p>
     * When set to {@code true}, the button will be rendered each time the page is opened.
     * When {@code false}, the button is rendered only during the initial {@link Page#render()}.
     * </p>
     *
     * @param b {@code true} to make the button dynamic, {@code false} to make it static.
     */
    void setDynamic(boolean b);

    /**
     * Sets the {@link ItemStack} for this button.
     *
     * @param item the {@link ItemStack} to associate with this button.
     */
    void setItem(ItemStack item);

    /**
     * Updates the {@link ItemStack} associated with this button using the provided consumer.
     * <p>
     * The consumer receives the current {@link ItemStack} and applies modifications to it.
     * If the item is modified, a new button instance reflecting the update is returned.
     * </p>
     *
     * @param item a {@link Consumer} that applies modifications to the current {@link ItemStack}.
     * @return the updated {@link Button} instance.
     */
    Button updateItem(Consumer<ItemStack> item);

    /**
     * Handles a click event on this button using the provided {@link ButtonClickContext}.
     *
     * @param ctx the context containing event details and the button instance.
     */
    void onClick(ButtonClickContext ctx);

    /**
     * Called when the button is loaded onto a {@link Page} for rendering.
     * <p>
     * For non-dynamic buttons, this method is invoked during {@link Page#render()}.
     * For dynamic buttons, it is invoked during {@link Page#open(Player, boolean)}.
     * </p>
     *
     * @param player the player viewing the page. This parameter may be {@code null} if the button is not dynamic.
     * @param page   the page on which the button is being rendered.
     * @return a {@link Button} instance representing the rendered button.
     *         If the item is modified during rendering, a new button instance with the updated item is returned.
     */
    @NotNull
    Button onRender(@Nullable Player player, Page page);

    /**
     * Performs an animation tick for this button.
     * <p>
     * The default implementation does nothing.
     * </p>
     *
     * @param view the {@link InventoryView} where the button is displayed.
     * @param page the {@link Page} that contains this button.
     */
    @Override
    default void tick(InventoryView view, Page page) {}

}