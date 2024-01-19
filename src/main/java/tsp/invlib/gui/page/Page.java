package tsp.invlib.gui.page;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.Button;
import tsp.invlib.handler.PageHandler;

import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public interface Page extends InventoryHolder {

    GUI getGui();

    void setGui(GUI gui);

    int getSize();

    String getName();

    void setButton(int slot, Button button);

    Optional<Button> getButton(int slot);

    Map<Integer, Button> getButtons();

    void reRender();

    default void open(Player player) {
        reRender();
        player.openInventory(getInventory());
    }

    default List<PageHandler> getHandlers() {
        return Collections.emptyList();
    }

    default <T extends PageHandler> List<T> getHandlers(Class<? extends T> clazz) {
        List<T> result = new ArrayList<>();

        for (PageHandler handler : getHandlers()) {
            if (clazz.isInstance(handler)) {
                //noinspection unchecked
                result.add((T) handler);
            }
        }

        return result;
    }

}
