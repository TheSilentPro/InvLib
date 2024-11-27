package tsp.invlib.gui.button.control;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.button.SimpleButton;

/**
 * @author TheSilentPro (Silent)
 */
public class ControlButton extends SimpleButton {

    private int slot;
    private ControlType type;

    public ControlButton(int slot, @NotNull ItemStack item, @NotNull GUI gui, @Nullable GUI parentGui, @NotNull ControlType type) {
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
        this.slot = slot;
        this.type = type;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public ControlType getType() {
        return type;
    }

    public void setType(@NotNull ControlType type) {
        this.type = type;
    }

}