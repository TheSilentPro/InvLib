package tsp.invlib.button;

import org.bukkit.inventory.ItemStack;
import tsp.invlib.page.handler.context.ButtonClickContext;

/**
 * @author TheSilentPro (Silent)
 */
public class LoopButton extends SimpleButton {

    private final ItemStack[] items;
    private int current;

    public LoopButton(ItemStack start, ItemStack[] items) {
        super(start);
        this.items = items;
        this.current = 0;
    }

    public LoopButton(ItemStack... buttons) {
        this(buttons[0], buttons);
    }

    @Override
    public void onClick(ButtonClickContext ctx) {
        if (current++ >= items.length - 1) {
            current = 0;
        }
        ItemStack item = items[current];

        ctx.page().inventory().setItem(ctx.event().getRawSlot(), item);
    }

    /* Doubt this will be ever needed.
    @Override
    public @NotNull Button onRender(@Nullable Player player, Page page) {
        return super.onRender(player, page).updateItem(item -> {
            ItemUtils.translateMeta(item, str -> str.replace("${{LOOP_CURRENT}}", String.valueOf(current)));
        });
    }
     */

    public int getCurrent() {
        return current;
    }

    public ItemStack[] getItems() {
        return items;
    }

}