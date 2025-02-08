package tsp.invlib.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.invlib.page.controllable.Controllable;
import tsp.invlib.page.handler.ButtonClickHandler;
import tsp.invlib.page.handler.PageClickHandler;
import tsp.invlib.page.handler.PageCloseHandler;
import tsp.invlib.page.handler.PageDragHandler;
import tsp.invlib.page.handler.context.ButtonClickContext;
import tsp.invlib.page.handler.context.PageClickContext;
import tsp.invlib.page.handler.context.PageCloseContext;
import tsp.invlib.page.handler.context.PageDragContext;
import tsp.invlib.page.registry.PageRegistry;

/**
 * Holds all listeners for handling pages.
 *
 * @author TheSilentPro (Silent)
 */
public class PageListeners implements Listener {

    private final PageRegistry registry;

    public PageListeners(PageRegistry registry) {
        this.registry = registry;
    }

    public PageListeners() {
        this.registry = PageRegistry.INSTANCE;
    }

    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPageClick(InventoryClickEvent event) {
        registry.get(event.getView()).ifPresent(page -> {
            page.getHandlers(PageClickHandler.class).forEach(handler -> handler.onClick(new PageClickContext(event, page)));
            page.getButton(event.getRawSlot()).ifPresent(button -> {
                button.onClick(new ButtonClickContext(event, page, button));
                page.getHandlers(ButtonClickHandler.class).forEach(handler -> handler.onClick(new ButtonClickContext(event, page, button)));
            });

            if (page instanceof Controllable<?> controllable) {
                if (!(event.getWhoClicked() instanceof Player player)) {
                    return;
                }

                if (event.getRawSlot() == controllable.getBackSlot()) {
                    controllable.onBack(player, page);
                } else if (event.getRawSlot() == controllable.getCurrentSlot()) {
                    controllable.onCurrent(player, page);
                } else if (event.getRawSlot() == controllable.getNextSlot()) {
                    controllable.onNext(player, page);
                }
            }
        });
    }

    @EventHandler
    public void onPageDrag(InventoryDragEvent event) {
        registry.get(event.getView()).ifPresent(page -> {
            page.getHandlers(PageDragHandler.class).forEach(handler -> handler.onDrag(new PageDragContext(event, page)));
        });
    }

    /* This is implemented within the Page#open method itself.
    @EventHandler
    public void onPageOpen(InventoryOpenEvent event) {
        registry.get(event.getView()).ifPresent(page -> {
            page.getHandlers(PageOpenHandler.class).forEach(handler -> handler.onOpen(event, page));
        });
    }
     */

    @EventHandler
    public void onPageClose(InventoryCloseEvent event) {
        registry.get(event.getView()).ifPresent(page -> {
            page.getHandlers(PageCloseHandler.class).forEach(handler -> handler.onClose(new PageCloseContext(event, page)));
            registry.remove(event.getView());
        });
    }

}