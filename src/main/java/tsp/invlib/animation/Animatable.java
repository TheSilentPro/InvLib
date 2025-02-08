package tsp.invlib.animation;

import org.bukkit.inventory.InventoryView;
import tsp.invlib.page.Page;

import java.time.Duration;

/**
 * Represents an animated {@link Page} or {@link tsp.invlib.button.Button}.
 *
 * @author TheSilentPro (Silent)
 */
public interface Animatable {

    void tick(InventoryView view, Page page);

    long getLastTick();

    void setLastTick(long l);

    /**
     * Returns the interval at which this will tick.
     * <p>
     *     1 tick = 50ms
     *     10 ticks = 500ms
     *     20 ticks = 1000ms (1 second)
     * </p>
     *
     * @return The interval at which this will repeat.
     */
    default Duration getInterval() {
        return Duration.ofSeconds(1);
    }

}