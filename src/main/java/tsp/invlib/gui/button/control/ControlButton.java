package tsp.invlib.gui.button.control;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tsp.invlib.gui.button.SimpleButton;
import tsp.invlib.gui.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class ControlButton extends SimpleButton {

    private final int slot;

    public ControlButton(int slot, ItemStack item, Page page, ControlType type) {
        super(item, event -> {
            if (event.getWhoClicked() instanceof Player player) {
                if (type == ControlType.BACK) {
                    page.getGui().open(player, page.getGui().getPreviousPage());
                } else if (type == ControlType.NEXT) {
                    page.getGui().open(player, page.getGui().getNextPage());
                }
            }
        });
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

}