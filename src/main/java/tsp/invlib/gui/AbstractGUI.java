package tsp.invlib.gui;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.button.Button;
import tsp.invlib.gui.registry.GUIRegistry;
import tsp.invlib.page.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public abstract class AbstractGUI<T> implements GUI<T> {

    private final GUIRegistry<T> guiRegistry;
    private final NamespacedKey key;
    private final Map<T, Page> pages;

    public AbstractGUI(GUIRegistry<T> guiRegistry, NamespacedKey key, Map<T, Page> pages) {
        this.guiRegistry = guiRegistry;
        this.key = key;
        this.pages = pages;
    }

    public AbstractGUI(GUIRegistry<T> guiRegistry, NamespacedKey key) {
        this(guiRegistry, key, new HashMap<>());
    }

    @Override
    public void populate(int rows, boolean overwriteExisting, int[] ignoredSlots, @NotNull Button... buttons) {
        if (buttons.length == 0) {
            throw new IllegalArgumentException("Buttons must not be empty!");
        }

        int maxSlotsPerPage = rows * 9;
        int buttonIndex = 0;

        for (Map.Entry<T, Page> entry : this.pages.entrySet()) {
            Page page = entry.getValue();

            for (int i = 0; i < page.getSize(); i++) {
                if (i >= maxSlotsPerPage) {
                    break; // Respect the row limit
                }
                if (buttonIndex >= buttons.length) {
                    return; // All buttons placed
                }
                if (isIgnored(i, ignoredSlots)) {
                    continue; // Skip ignored slot
                }
                if (!overwriteExisting && page.getButton(i).isPresent()) {
                    continue; // Skip if slot already has a button and we don't want to overwrite
                }
                page.setButton(i, buttons[buttonIndex++]);
            }
        }
    }

    private boolean isIgnored(int index, int... ignored) {
        for (int ignoredSlot : ignored) {
            if (ignoredSlot == index) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void open(Player player, T index) {
        this.pages.get(index).open(player);
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public Optional<Page> getPage(T index) {
        return Optional.ofNullable(this.pages.get(index));
    }

    @Override
    public void updatePage(T index, Consumer<Page> page) {
        this.pages.compute(index, (i, p) -> {
            page.accept(p);
            return p;
        });
    }

    @Override
    public void setPage(T index, Page page) {
        this.pages.put(index, page);
    }

    @Override
    public Map<T, Page> getPages() {
        return this.pages;
    }

    public GUIRegistry<T> getGuiRegistry() {
        return guiRegistry;
    }

}