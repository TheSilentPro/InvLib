package tsp.invlib.page;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.button.Button;
import tsp.invlib.page.handler.PageHandler;
import tsp.invlib.page.handler.PageOpenHandler;
import tsp.invlib.page.handler.context.PageOpenContext;
import tsp.invlib.page.registry.PageRegistry;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractPage implements Page {

    private final Map<Integer, Button> buttons;
    private final List<PageHandler> handlers;
    private PageRegistry registry;
    private Inventory inventory;
    private boolean rendered;
    private long lastTick;

    public AbstractPage(@Nullable PageRegistry registry) {
        this.buttons = new HashMap<>();
        this.handlers = new ArrayList<>();
        this.registry = registry;
        this.inventory = null;
        this.rendered = false;
        this.lastTick = -1;
    }

    protected AbstractPage(Page copy) {
        this(copy.getRegistry());
        this.buttons.putAll(copy.getButtons());
        this.handlers.addAll(copy.getHandlers());
        this.inventory = copy.getInventory();
        this.rendered = copy.isRendered();
        this.lastTick = copy.getLastTick();
    }

    public AbstractPage() {
        this((PageRegistry) null);
    }

    @NotNull
    public abstract Inventory createInventory();

    @Override
    public Optional<Button> getButton(int index) {
        return Optional.ofNullable(this.buttons.get(index));
    }

    @Override
    public void updateButton(int index, Consumer<Button> button) {
        this.buttons.compute(index, (i, b) -> {
            button.accept(b);
            return b;
        });
    }

    @Override
    public void setButton(int index, Button button) {
        this.buttons.put(index, button);
    }

    @Override
    public void addButton(Button button) {
        this.buttons.put(this.buttons.size(), button);
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return Collections.unmodifiableMap(this.buttons);
    }

    @Override
    public void render() {
        for (Map.Entry<Integer, Button> entry : this.buttons.entrySet()) {
            Button button = entry.getValue();
            // Dynamic buttons are only set upon opening.
            if (button.isDynamic()) {
                continue;
            }

            button.onRender(null, this);
            button.getItem().ifPresent(item -> this.inventory.setItem(entry.getKey(), item));
        }
        this.rendered = true;
    }

    @Override
    public void reRender() {
        deRender();
        render();
    }

    @Override
    public void deRender() {
        inventory().clear();
        rendered = false;
    }

    @Override
    public boolean isRendered() {
        return rendered;
    }

    @Override
    public InventoryView open(Player player, boolean render) {
        // Renders the page contents if not already rendered
        if (render && !rendered) {
            reRender();
        }

        // If the page is dynamic, then a copy is shown to the player.
        Inventory effective = isDynamic() ? createInventory() : inventory();
        Page effectivePage = isDynamic() ? this.copy() : this;

        if (isDynamic()) {
            for (Map.Entry<Integer, Button> buttonEntry : effectivePage.getButtons().entrySet()) {
                Button button = buttonEntry.getValue();
                button.onRender(player, effectivePage).getItem().ifPresent(item -> effective.setItem(buttonEntry.getKey(), item));
            }
        }

        InventoryView view = player.openInventory(effective);
        if (view == null) {
            throw new IllegalStateException("Could not create inventory view while opening the page: ");
        }

        // Trigger an open event
        InventoryOpenEvent openEvent = new InventoryOpenEvent(view);
        Bukkit.getPluginManager().callEvent(openEvent);
        if (openEvent.isCancelled()) {
            player.closeInventory(); // Event cancelled, close the inventory
            return view;
        }

        // Instead of listening for an open event, just call open handlers here.
        getHandlers(PageOpenHandler.class).forEach(handler -> handler.onOpen(new PageOpenContext(openEvent, isDynamic() ? effectivePage : this)));

        if (this.registry != null) {
            this.registry.register(view, isDynamic() ? effectivePage : this); // If the page is dynamic, a copy is registered for the viewer.
        }

        return view;
    }

    @Override
    public void addHandler(PageHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void removeHandler(PageHandler handler) {
        this.handlers.remove(handler);
    }

    @Override
    public List<PageHandler> getHandlers() {
        return this.handlers;
    }

    @Override
    public void setRegistry(PageRegistry registry) {
        this.registry = registry;
    }

    @Override
    public PageRegistry getRegistry() {
        return registry;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Inventory inventory() {
        if (inventory == null) {
            this.inventory = createInventory();
        }
        return this.inventory;
    }

    public long getLastTick() {
        return lastTick;
    }

    public void setLastTick(long lastTick) {
        this.lastTick = lastTick;
    }

}