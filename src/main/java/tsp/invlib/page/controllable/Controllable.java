package tsp.invlib.page.controllable;

import org.bukkit.entity.Player;
import tsp.invlib.button.Button;
import tsp.invlib.gui.GUI;
import tsp.invlib.page.Page;

import java.util.Optional;

/**
 * Represents a controllable {@link Page}.
 *
 * @author TheSilentPro (Silent)
 */
public interface Controllable<T> {

    GUI<T> getGui();

    int getBackSlot();

    int getCurrentSlot();

    int getNextSlot();

    void onBack(Player player, Page page);

    void onCurrent(Player player, Page page);

    void onNext(Player player, Page page);

    default Optional<Button> getBackButton() {
        return Optional.empty();
    }

    default Optional<Button> getCurrentButton() {
        return Optional.empty();
    }

    default Optional<Button> getNextButton() {
        return Optional.empty();
    }

}