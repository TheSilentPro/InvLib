package tsp.invlib.gui;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
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