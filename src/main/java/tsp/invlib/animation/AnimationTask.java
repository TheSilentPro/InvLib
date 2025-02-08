package tsp.invlib.animation;

import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.invlib.button.Button;
import tsp.invlib.page.Page;
import tsp.invlib.page.registry.PageRegistry;

import java.util.Map;

public class AnimationTask implements Runnable {

    private final PageRegistry pageRegistry;

    public AnimationTask(PageRegistry pageRegistry) {
        this.pageRegistry = pageRegistry;
    }

    public AnimationTask() {
        this(PageRegistry.INSTANCE);
    }

    public void register(JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 0L, 1L);
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis() / 50;

        // TODO: Optimize?
        for (Map.Entry<InventoryView, Page> entry : pageRegistry.getPages().entrySet()) {
            Page page = entry.getValue();
            InventoryView view = entry.getKey();

            tickIfNeeded(page, now, view, page);

            for (Map.Entry<Integer, Button> buttonEntry : page.getButtons().entrySet()) {
                Button button = buttonEntry.getValue();
                tickIfNeeded(button, now, view, page);
            }
        }
    }

    private void tickIfNeeded(Animatable animatable, long now, InventoryView view, Page page) {
        if (animatable.getLastTick() == -1) {
            return;
        }

        long intervalMillis = page.getInterval().toMillis() / 50;
        long lastTick = animatable.getLastTick();

        if (now - lastTick >= intervalMillis) {
            page.tick(view, page);
            animatable.setLastTick(now);
        }

    }


}