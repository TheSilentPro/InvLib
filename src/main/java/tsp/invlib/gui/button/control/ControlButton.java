package tsp.invlib.gui.button.control;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.SimpleButton;

/**
 * @author TheSilentPro (Silent)
 */
public class ControlButton extends SimpleButton {

    private int slot;

    public ControlButton(int slot, ItemStack item, GUI gui, GUI parentGui, ControlType type) {
        super(item, event -> {
            if (type == ControlType.NONE) {
                return;
            }

            if (event.getWhoClicked() instanceof Player player) {
                if (type == ControlType.BACK) {
                    gui.open(player, gui.getPreviousPage());
                } else if (type == ControlType.NEXT) {
                    gui.open(player, gui.getNextPage());
                } else if (type == ControlType.CURRENT) {
                    if (parentGui != null) {
                        parentGui.open(player);
                    }
                }
            }
        });
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

}