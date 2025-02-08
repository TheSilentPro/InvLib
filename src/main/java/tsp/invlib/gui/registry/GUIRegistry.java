package tsp.invlib.gui.registry;

import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheSilentPro (Silent)
 */
public interface GUIRegistry<T> {

    static <T> GUIRegistry<T> newRegistry() {
        return new GUIRegistryImpl<>();
    }

    void setCurrentPage(UUID id, NamespacedKey key, T index);

    T getCurrentPage(UUID id, NamespacedKey key, T def);

    Optional<T> getCurrentPage(UUID id, NamespacedKey key);

    Optional<Map<NamespacedKey, T>> getData(UUID id);

}
