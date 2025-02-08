package tsp.invlib.page.controllable;

import org.bukkit.entity.Player;
import tsp.invlib.page.Page;

/**
 * A {@link Controllable} page that is paginated by integers.
 *
 * @author TheSilentPro (Silent)
 */
public interface PaginatedControllable extends Controllable<Integer> {

    @Override
    default void onBack(Player player, Page page) {
        int current = getGui().getGuiRegistry().getCurrentPage(player.getUniqueId(), getGui().getKey(), 0);
        getGui().getPage(current - 1).ifPresent(previous -> {
            previous.open(player);
            getGui().getGuiRegistry().setCurrentPage(player.getUniqueId(), getGui().getKey(), current - 1);
        });
    }

    @Override
    default void onCurrent(Player player, Page page) {}

    @Override
    default void onNext(Player player, Page page) {
        int current = getGui().getGuiRegistry().getCurrentPage(player.getUniqueId(), getGui().getKey(), 0);
        getGui().getPage(current + 1).ifPresent(next -> {
            next.open(player);
            getGui().getGuiRegistry().setCurrentPage(player.getUniqueId(), getGui().getKey(), current + 1);
        });
    }

}