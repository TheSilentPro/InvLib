package tsp.invlib.page.handler.context;

import org.bukkit.event.inventory.InventoryClickEvent;
import tsp.invlib.button.Button;
import tsp.invlib.page.Page;

/**
 * @author TheSilentPro (Silent)
 */
public class ButtonClickContext extends PageContext<InventoryClickEvent> {

    private final Button button;

    public ButtonClickContext(InventoryClickEvent event, Page page, Button button) {
        super(event, page);
        this.button = button;
    }

    public Button button() {
        return button;
    }

}