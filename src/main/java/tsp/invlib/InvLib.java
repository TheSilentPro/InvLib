package tsp.invlib;

import org.bukkit.plugin.java.JavaPlugin;
import tsp.invlib.gui.GUIBuilder;
import tsp.invlib.gui.page.PageBuilder;
import tsp.invlib.listener.PageListeners;

/**
 * @author TheSilentPro (Silent)
 */
public class InvLib {

    private static JavaPlugin instance;

    public static void init(JavaPlugin plugin) {
        instance = plugin;
        plugin.getServer().getPluginManager().registerEvents(new PageListeners(), plugin);
    }

    public static JavaPlugin getPlugin() {
        return instance;
    }

    public static PageBuilder pageBuilder() {
        return new PageBuilder();
    }

    public static GUIBuilder guiBuilder() {
        return new GUIBuilder();
    }

}