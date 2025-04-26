package tsp.invlib.gui;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.button.Button;
import tsp.invlib.gui.registry.GUIRegistry;
import tsp.invlib.page.Page;
import tsp.invlib.page.controllable.PaginatedControllable;
import tsp.invlib.util.ItemUtils;

import java.util.Map;

/**
 * @author TheSilentPro (Silent)
 */
public class PaginatedGUI extends AbstractGUI<Integer> {

    public static final GUIRegistry<Integer> INSTANCE = GUIRegistry.newRegistry();

    private int current = 0;

    public PaginatedGUI(GUIRegistry<Integer> guiRegistry, NamespacedKey key, Map<Integer, Page> pages) {
        super(guiRegistry, key, pages);
    }

    public PaginatedGUI(GUIRegistry<Integer> guiRegistry, NamespacedKey key) {
        super(guiRegistry, key);
    }

    public PaginatedGUI(NamespacedKey key) {
        this(INSTANCE, key);
    }

    public void setControls(@NotNull Button backButton, @NotNull Button currentButton, @NotNull Button nextButton, boolean translate) {
        for (Map.Entry<Integer, Page> entry : getPages().entrySet()) {
            Page page = entry.getValue();
            if (page instanceof PaginatedControllable controllable) {
                int currentPage = entry.getKey();
                // Back
                if (getPages().get(currentPage - 1) != null) {
                    page.setButton(controllable.getBackSlot(), controllable.getBackButton().orElse(backButton).updateItem(item -> {
                        if (!translate) {
                            return;
                        }

                        ItemUtils.translateItem(item, currentPage, getPages().size());
                    }));
                }
                // Next
                if (getPages().get(currentPage + 1) != null) {
                    page.setButton(controllable.getNextSlot(), controllable.getNextButton().orElse(nextButton).updateItem(item -> {
                        if (!translate) {
                            return;
                        }

                        ItemUtils.translateItem(item, currentPage, getPages().size());
                    }));
                }
                // Current
                page.setButton(controllable.getCurrentSlot(), controllable.getCurrentButton().orElse(currentButton).updateItem(item -> {
                    if (!translate) {
                        return;
                    }

                    ItemUtils.translateItem(item, currentPage, getPages().size());
                }));
            }
        }
    }

    public void setControls(@NotNull Button backButton, @NotNull Button currentButton, @NotNull Button nextButton) {
        setControls(backButton, currentButton, nextButton, false);
    }

    public void open(Player player) {
        super.open(player, 0);
    }

    @Override
    public void setPage(Integer index, Page page) {
        super.setPage(index, page);

        if (index >= current) {
            current = index + 1;
        }
    }

    public void addPage(Page page) {
        setPage(current++, page);
    }

}