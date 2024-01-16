package tsp.invlib.gui.page;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import tsp.invlib.gui.button.Button;
import tsp.invlib.handler.PageHandler;

import javax.annotation.Nonnull;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author TheSilentPro (Silent)
 */
public class SimplePage implements Page, Serializable {

    @Serial
    private static final long serialVersionUID = -2303582995747170968L;

    private final int size;
    private final String name;
    private final HashMap<Integer, Button> buttons;
    private final ArrayList<PageHandler> handlers;
    private final Inventory inventory;

    public SimplePage(int rows, String name, Map<Integer, Button> buttons, List<PageHandler> handlers) {
        this.size = rows * 9;
        this.name = name;
        this.buttons = new HashMap<>(buttons);
        this.handlers = new ArrayList<>(handlers);
        this.inventory = Bukkit.createInventory(this, size, name);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setButton(int slot, Button button) {
        this.buttons.put(slot, button);
    }

    @Override
    public Optional<Button> getButton(int slot) {
        return Optional.ofNullable(this.buttons.get(slot));
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return Collections.unmodifiableMap(buttons);
    }

    @Override
    public void reRender() {
        inventory.clear();
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            Button button = entry.getValue();
            if (button != null && !button.getItem().getType().isAir()) {
                inventory.setItem(entry.getKey(), button.getItem());
            }
        }
    }

    @Nonnull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public List<PageHandler> getHandlers() {
        return handlers;
    }

}