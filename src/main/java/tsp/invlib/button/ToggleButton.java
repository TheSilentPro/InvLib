package tsp.invlib.button;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.page.Page;
import tsp.invlib.page.handler.context.ButtonClickContext;
import tsp.invlib.util.ItemUtils;

/**
 * @author TheSilentPro (Silent)
 */
public class ToggleButton extends SimpleButton {

    private final ItemStack enabled;
    private final ItemStack disabled;
    private boolean current;
    private boolean locked;

    public ToggleButton(ItemStack enabled, ItemStack disabled, boolean current, boolean dynamic) {
        super(current ? enabled : disabled, dynamic);
        this.current = current;
        this.enabled = enabled;
        this.disabled = disabled;
    }

    public ToggleButton(ItemStack enabled, ItemStack disabled, boolean dynamic) {
        this(enabled, disabled, false, dynamic);
    }

    @Override
    public void onClick(ButtonClickContext ctx) {
        if (!locked) {
            ctx.page().inventory().setItem(ctx.event().getRawSlot(), toggle() ? enabled : disabled);
        }
    }

    @Override
    public @NotNull Button onRender(@Nullable Player player, Page page) {
        return super.onRender(player, page).updateItem(item -> {
            ItemUtils.translateMeta(item, str -> str.replace("${{TOGGLE_STATE}}", String.valueOf(current)));
        });
    }

    public boolean toggle(boolean force) {
        if (locked) {
            if (!force) {
                return current;
            }
        }
        this.current = !current;
        return current;
    }

    public boolean toggle() {
        return toggle(true);
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public ItemStack getDisabled() {
        return disabled;
    }

    public ItemStack getEnabled() {
        return enabled;
    }

    public boolean isToggled() {
        return current;
    }

    public boolean isLocked() {
        return locked;
    }

}