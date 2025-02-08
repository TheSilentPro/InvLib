package tsp.invlib.gui.registry;

import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheSilentPro (Silent)
 */
public class GUIRegistryImpl<T> implements GUIRegistry<T> {

    private final Map<UUID, Map<NamespacedKey, T>> pageTracker;

    public GUIRegistryImpl() {
        this.pageTracker = new HashMap<>();
    }

    @Override
    public void setCurrentPage(UUID id, NamespacedKey key, T index) {
        Map<NamespacedKey, T> map = pageTracker.computeIfAbsent(id, k -> new HashMap<>());
        map.put(key, index);
        this.pageTracker.put(id, map);
    }

    public T getCurrentPage(UUID id, NamespacedKey key, T def) {
        Map<NamespacedKey, T> map = pageTracker.computeIfAbsent(id, k -> new HashMap<>());
        T result = map.get(key);
        if (result == null) {
            map.put(key, def);
            this.pageTracker.put(id, map);
            return def;
        }

        return result;
    }

    @Override
    public Optional<T> getCurrentPage(UUID id, NamespacedKey key) {
        return getData(id).map(map -> map.get(key));
    }

    @Override
    public Optional<Map<NamespacedKey, T>> getData(UUID id) {
        return Optional.ofNullable(this.pageTracker.get(id));
    }

}