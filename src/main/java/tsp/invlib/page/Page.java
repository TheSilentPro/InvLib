package tsp.invlib.page;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.animation.Animatable;
import tsp.invlib.button.Button;
import tsp.invlib.page.handler.*;
import tsp.invlib.page.handler.context.PageClickContext;
import tsp.invlib.page.handler.context.PageDragContext;
import tsp.invlib.page.registry.PageRegistry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a page that can contain buttons, handle events,
 * and support animations.
 * <p>
 * A page is associated with an inventory and can be rendered, re-rendered,
 * and have its buttons updated. It also provides event handlers
 * for inventory interactions.
 * </p>
 *
 * @author TheSilentPro
 */
public interface Page extends Animatable {

    // Buttons

    /**
     * Updates the button at the specified index by applying the given consumer.
     *
     * @param index  the index at which the button is located
     * @param button a consumer that applies updates to the button
     */
    void updateButton(int index, Consumer<Button> button);

    /**
     * Sets the button at the specified index.
     *
     * @param index  the index at which to set the button
     * @param button the button to set
     */
    void setButton(int index, Button button);

    /**
     * Sets the button at the given x and y coordinates (0-indexed).
     * <p>
     * The slot is calculated as {@code y * 9 + x}.
     * </p>
     *
     * @param x      the x coordinate (0-indexed, column)
     * @param y      the y coordinate (0-indexed, row)
     * @param button the button to set
     */
    default void setButtonAt(int x, int y, Button button) {
        setButton(y * 9 + x, button);
    }

    /**
     * Sets the button at the specified column and row (1-indexed).
     *
     * @param column the column (1-indexed)
     * @param row    the row (1-indexed)
     * @param button the button to set
     */
    default void setButton(int column, int row, Button button) {
        setButtonAt(column - 1, row - 1, button);
    }

    /**
     * Adds a button to the page. The button will be placed in the next available slot.
     *
     * @param button the button to add
     */
    void addButton(Button button);

    /**
     * Gets the button at the specified index.
     *
     * @param index the index of the button
     * @return an {@link Optional} containing the button if present, otherwise an empty {@link Optional}
     */
    Optional<Button> getButton(int index);

    /**
     * Gets a map of all buttons on this page with their corresponding slot indices.
     *
     * @return a map of slot indices to buttons
     */
    Map<Integer, Button> getButtons();

    // Rendering

    /**
     * Sets all buttons in their respective slots on the inventory.
     * <p>
     * Only the buttons present in the page are rendered; other slots are unaffected.
     * </p>
     */
    void render();

    /**
     * Clears the page by removing all buttons from the inventory.
     */
    void deRender();

    /**
     * De-renders and then re-renders the page.
     */
    void reRender();

    /**
     * Checks if the page has been rendered.
     *
     * @return {@code true} if the page is rendered, {@code false} otherwise
     */
    boolean isRendered();

    /**
     * Opens the page for the specified player.
     *
     * @param player the player for whom the page is to be opened
     * @param render if {@code true}, the page will be rendered before opening
     * @return the {@link InventoryView} representing the opened inventory
     */
    InventoryView open(Player player, boolean render);

    /**
     * Opens the page for the specified player and renders it.
     *
     * @param player the player for whom the page is to be opened
     * @return the {@link InventoryView} representing the opened inventory
     */
    default InventoryView open(Player player) {
        return open(player, true);
    }

    // Handlers

    /**
     * Adds a {@link PageHandler} to this page.
     *
     * @param handler the handler to add
     */
    void addHandler(PageHandler handler);

    /**
     * Removes the specified {@link PageHandler} from this page.
     *
     * @param handler the handler to remove
     */
    void removeHandler(PageHandler handler);

    /**
     * Retrieves a list of all {@link PageHandler} instances associated with this page.
     *
     * @return a list of page handlers
     */
    List<PageHandler> getHandlers();

    /**
     * Retrieves a list of {@link PageHandler} instances of a specific type.
     *
     * @param clazz the class of the handlers to retrieve
     * @param <T>   the type of the handlers
     * @return a list of handlers matching the specified type
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

    /**
     * Adds a {@link PageOpenHandler} that will be called when the page is opened.
     *
     * @param handler the open handler to add
     */
    default void onOpen(PageOpenHandler handler) {
        addHandler(handler);
    }

    /**
     * Adds a {@link PageCloseHandler} that will be called when the page is closed.
     *
     * @param handler the close handler to add
     */
    default void onClose(PageCloseHandler handler) {
        addHandler(handler);
    }

    /**
     * Adds a {@link PageClickHandler} that will be called when an inventory click event occurs on the page.
     *
     * @param handler the click handler to add
     */
    default void onClick(PageClickHandler handler) {
        addHandler(handler);
    }

    /**
     * Adds a {@link PageDragHandler} that will be called when an inventory drag event occurs on the page.
     *
     * @param handler the drag handler to add
     */
    default void onDrag(PageDragHandler handler) {
        addHandler(handler);
    }

    // Prevention handlers

    /**
     * Prevents click events for the specified {@link ClickType ClickTypes}.
     * <p>
     * If the click event matches any of the specified types, it will be cancelled.
     * </p>
     *
     * @param types the click types to prevent
     */
    default void preventClick(ClickType... types) {
        onClick(ctx -> {
            for (ClickType type : types) {
                if (type == ctx.event().getClick()){
                    ctx.event().setCancelled(true);
                    return;
                }
            }
        });
    }

    /**
     * Prevents all click events by cancelling them.
     */
    default void preventClick() {
        onClick(ctx -> ctx.event().setCancelled(true));
    }

    /**
     * Prevents page click events based on the provided predicate.
     * <p>
     * If the predicate returns {@code false} for a given click context, the event will be cancelled.
     * </p>
     *
     * @param predicate the predicate to test the click context
     */
    default void preventPageClick(Predicate<PageClickContext> predicate) {
        onClick(ctx -> {
            if (!predicate.test(ctx)) {
                ctx.event().setCancelled(true);
            }
        });
    }

    /**
     * Prevents page click events except for clicks on the allowed slots.
     * <p>
     * For clicks on the player inventory, the event is always allowed.
     * If {@code allowed} is {@code null}, then no slots are allowed.
     * </p>
     *
     * @param allowed an array of allowed raw slot indices; may be {@code null}
     */
    default void preventPageClick(int @Nullable ... allowed) {
        preventPageClick(ctx -> {
            //noinspection DataFlowIssue - Checked
            if (ctx.event().getClickedInventory() != null && ctx.event().getClickedInventory().getType() == InventoryType.PLAYER) {
                return true; // Allow clicks in the player inventory
            }

            if (allowed == null) {
                return false; // No slot is allowed, cancel the event
            }

            // Check if the raw slot is in the allowed array; if so, allow the click
            for (int i : allowed) {
                if (ctx.event().getRawSlot() == i) {
                    return true;
                }
            }

            return false; // Cancel if raw slot is not allowed
        });
    }

    /**
     * Prevents all page click events by cancelling them.
     */
    default void preventPageClick() {
        preventPageClick((int[]) null);
    }

    /**
     * Prevents interaction with the page based on the provided predicates.
     * <p>
     * Click and drag events will be cancelled if their respective predicates return {@code false}.
     * </p>
     *
     * @param clickPredicate the predicate to test click events
     * @param dragPredicate  the predicate to test drag events
     */
    default void preventInteraction(Predicate<PageClickContext> clickPredicate, Predicate<PageDragContext> dragPredicate) {
        onClick(ctx -> {
            if (!clickPredicate.test(ctx)) {
                ctx.event().setCancelled(true);
            }
        });
        onDrag(ctx -> {
            if (!dragPredicate.test(ctx)) {
                ctx.event().setCancelled(true);
            }
        });
    }

    /**
     * Prevents all interaction with the page by cancelling both click and drag events.
     */
    default void preventInteraction() {
        preventInteraction(ctx -> false, ctx -> false);
    }

    /**
     * Prevents interactions on the page's inventory, while allowing interactions
     * on the player's inventory (except double-clicking, which can allow items to be taken from the page).
     * <p>
     * Click and drag events are evaluated with the provided predicates.
     * </p>
     *
     * @param clickPredicate the predicate to test click events on the page inventory
     * @param dragPredicate  the predicate to test drag events on the page inventory
     */
    default void preventPageInteraction(Predicate<PageClickContext> clickPredicate, Predicate<PageDragContext> dragPredicate) {
        preventInteraction(ctx -> {
            //noinspection DataFlowIssue -- Checked
            if (
                    ctx.event().getClickedInventory() != null
                            && ctx.event().getClickedInventory().getType() == InventoryType.PLAYER
                            && ctx.event().getClick() != ClickType.DOUBLE_CLICK // Double click can allow items to be taken from page
            ) {
                return true; // Allow interaction in player inventory
            }

            return clickPredicate.test(ctx);
        }, ctx -> {
            // For drag events, if all raw slots are outside the page inventory, allow the drag event.
            if (ctx.event().getRawSlots().stream().allMatch(slot -> slot >= getSize())) {
                return true; // Allow drag events affecting only the player inventory
            }

            return dragPredicate.test(ctx);
        });
    }

    /**
     * Prevents all interactions on the page by cancelling both click and drag events.
     */
    default void preventPageInteraction() {
        preventPageInteraction(ctx -> false, ctx -> false);
    }

    /**
     * Prevents the closing of this page.
     * <p>
     * This method schedules a task to reopen the page one tick later,
     * as reopening the inventory in the same tick it is closed is not allowed.
     * </p>
     *
     * @param plugin the {@link JavaPlugin} instance scheduling the task
     * @return the {@link PageCloseHandler} that prevents the page from closing
     */
    default PageCloseHandler preventClose(JavaPlugin plugin) {
        PageCloseHandler closeHandler = ctx -> plugin.getServer().getScheduler().runTaskLater(plugin, () -> ctx.page().open((Player) ctx.event().getPlayer()), 1L);
        onClose(closeHandler);
        return closeHandler;
    }

    // Misc

    /**
     * Gets the size of the page's inventory.
     *
     * @return the size of the inventory
     */
    int getSize();

    /**
     * Sets the {@link PageRegistry} for this page.
     *
     * @param registry the page registry to set
     */
    void setRegistry(PageRegistry registry);

    /**
     * Gets the {@link PageRegistry} associated with this page.
     *
     * @return the page registry
     */
    PageRegistry getRegistry();

    /**
     * Gets the {@link Inventory} associated with this page.
     *
     * @return the inventory of the page
     */
    Inventory getInventory();

    /**
     * An alias for {@link #getInventory()}.
     *
     * @return the inventory of the page
     */
    Inventory inventory();

    /**
     * Creates and returns a copy of this page.
     *
     * @return a copy of the page
     */
    Page copy();

    /**
     * Indicates whether the page is dynamic.
     * <p>
     * When {@code true}, the page will reset on every opening, meaning any changes
     * made by players will not be persisted.
     * </p>
     *
     * @return {@code true} if the page is dynamic, {@code false} otherwise
     */
    default boolean isDynamic() {
        return false;
    }

    // Animation

    /**
     * Called on each tick for animation purposes.
     * <p>
     * The default implementation does nothing.
     * </p>
     *
     * @param view the {@link InventoryView} of the page
     * @param page the page instance
     */
    @Override
    default void tick(InventoryView view, Page page) {}
}