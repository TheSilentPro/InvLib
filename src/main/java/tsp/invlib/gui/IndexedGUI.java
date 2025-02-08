package tsp.invlib.gui;

import org.bukkit.NamespacedKey;
import tsp.invlib.gui.registry.GUIRegistry;
import tsp.invlib.page.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TheSilentPro (Silent)
 */
public class IndexedGUI extends AbstractGUI<String> {

    public static final GUIRegistry<String> INSTANCE = GUIRegistry.newRegistry();

    public IndexedGUI(GUIRegistry<String> guiRegistry, NamespacedKey key, Map<String, Page> pages) {
        super(guiRegistry, key, pages);
    }

    public IndexedGUI(NamespacedKey key, Map<String, Page> pages) {
        this(INSTANCE, key, pages);
    }

    public IndexedGUI(NamespacedKey key) {
        this(INSTANCE, key, new HashMap<>());
    }

}