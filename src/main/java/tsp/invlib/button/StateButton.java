package tsp.invlib.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.page.Page;
import tsp.invlib.page.handler.context.ButtonClickContext;
import tsp.invlib.util.ItemUtils;
import tsp.invlib.util.State;

/**
* @author TheSilentPro (Silent)
*/
public class StateButton extends SimpleButton {

    private final ItemStack enabled;
    private final ItemStack disabled;
    private final ItemStack unset;
    private State current;
    private boolean locked;

    public StateButton(ItemStack enabled, ItemStack disabled, ItemStack unset, State current, boolean dynamic) {
        super(current == State.TRUE ? enabled : (current == State.FALSE ? disabled : unset), dynamic);
        this.enabled = enabled;
        this.disabled = disabled;
        this.unset = unset;
        this.current = current;
    }

    public StateButton(ItemStack enabled, ItemStack disabled, ItemStack unset, boolean dynamic) {
        this(enabled, disabled, unset, State.NOT_SET, dynamic);
    }

    @Override
    public void onClick(ButtonClickContext ctx) {
        if (!locked) {
            State b = toggle();
            ctx.page().inventory().setItem(ctx.event().getRawSlot(), b == State.TRUE ? enabled : (b == State.FALSE ? disabled : unset));
        }
    }

    @Override
    public @NotNull Button onRender(@Nullable Player player, Page page) {
        return super.onRender(player, page).updateItem(item -> {
            ItemUtils.translateMeta(item, str -> str.replace("${{STATE}}", current.toString()));
        });
    }

    public State toggle() {
        if (current == State.NOT_SET) {
            current = State.TRUE;
        } else if (current == State.TRUE) {
            current = State.FALSE;
        } else if (current == State.FALSE) {
            current = State.NOT_SET;
        }
        return current;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public State getState() {
        return current;
    }

    public boolean isLocked() {
        return locked;
    }

}