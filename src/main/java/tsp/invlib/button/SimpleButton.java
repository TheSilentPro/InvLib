package tsp.invlib.button;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tsp.invlib.page.Page;
import tsp.invlib.page.controllable.PaginatedControllable;
import tsp.invlib.page.handler.context.ButtonClickContext;
import tsp.invlib.util.ItemUtils;
import tsp.invlib.util.State;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author TheSilentPro (Silent)
 */
public class SimpleButton implements Button {

    private static State PAPI_INSTALLED = State.NOT_SET;

    private ItemStack item;
    private final Consumer<ButtonClickContext> contextHandler;
    private boolean dynamic;
    private long lastTick;

    public SimpleButton(@Nullable ItemStack item, boolean dynamic, @Nullable Consumer<ButtonClickContext> contextHandler) {
        this.item = item;
        this.contextHandler = contextHandler;
        this.dynamic = dynamic;
        this.lastTick = -1;
    }

    public SimpleButton(@Nullable ItemStack item, @Nullable Consumer<ButtonClickContext> contextHandler) {
        this(item, false, contextHandler);
    }

    public SimpleButton(@Nullable ItemStack item, boolean dynamic) {
        this(item, dynamic, null);
    }

    public SimpleButton(@Nullable ItemStack item) {
        this(item, false, null);
    }

    public SimpleButton(@NotNull Button button) {
        this(button.getItem().orElse(null), button.getContextHandler());
    }

    @Override
    public Optional<ItemStack> getItem() {
        return Optional.ofNullable(this.item);
    }

    @Override
    public Consumer<ButtonClickContext> getContextHandler() {
        return contextHandler;
    }

    @Override
    public long getLastTick() {
        return lastTick;
    }

    @Override
    public void setLastTick(long lastTick) {
        this.lastTick = lastTick;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public Button updateItem(Consumer<ItemStack> item) {
        ItemStack clone = this.item.clone(); // Must be cloned
        item.accept(clone);
        return new SimpleButton(clone, contextHandler);
    }

    @Override
    public void onClick(ButtonClickContext ctx) {
        if (getContextHandler() != null) {
            getContextHandler().accept(ctx);
        }
    }

    @Override
    @NotNull
    public Button onRender(@Nullable Player player, Page page) {
        if (player == null) {
            return this;
        }

        if (PAPI_INSTALLED == State.NOT_SET) {
            PAPI_INSTALLED = State.byBoolean(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"));
        }

        Button button = this;
        if (PAPI_INSTALLED == State.TRUE) {
            button = updateItem(item -> {
                ItemUtils.translateMeta(item, str -> PlaceholderAPI.setPlaceholders(player, str));

                /*
                meta.setItemName(PlaceholderAPI.setPlaceholders(player, meta.getItemName()));

                if (meta.hasLore() && meta.getLore() != null) {
                    List<String> lore = meta.getLore();
                    List<String> newLore = new ArrayList<>();
                    for (String line : lore) {
                        newLore.add(PlaceholderAPI.setPlaceholders(player, line));
                    }
                    meta.setLore(newLore);
                }
                item.setItemMeta(meta);
                 */
            });
        }

        button = button.updateItem(item -> {
            if (page instanceof PaginatedControllable controllable) {
                ItemUtils.translateItem(this.item, controllable.getGui().getGuiRegistry().getCurrentPage(player.getUniqueId(), controllable.getGui().getKey(), 0), controllable.getGui().getPages().size());
            }
        });

        return button;
    }

}