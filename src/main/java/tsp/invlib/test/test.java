package tsp.invlib.test;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.invlib.InvLib;
import tsp.invlib.gui.GUI;
import tsp.invlib.gui.SimpleGUI;
import tsp.invlib.gui.button.SimpleButton;
import tsp.invlib.gui.page.PageBuilder;

/**
 * @author TheSilentPro (Silent)
 */
public class test extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        InvLib.init(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void ojoin(PlayerJoinEvent event) {
        GUI gui = new SimpleGUI();
        gui.addPage(new PageBuilder()
                        .rows(6)
                        .name("Test Page 1")
                        .preventClick()
                        .includeControlButtons()
                        .button(40, new SimpleButton(new ItemStack(Material.DIAMOND), e -> e.getWhoClicked().sendMessage("You clicked the diamond!")))
                .build()
        );
        gui.addPage(new PageBuilder()
                .rows(3)
                .name("Test Page 2")
                .preventClick()
                .includeControlButtons()
                .button(14, new SimpleButton(new ItemStack(Material.EMERALD), e -> e.getWhoClicked().sendMessage("You clicked the emerald!")))
                .build()
        );
        gui.addPage(new PageBuilder()
                .rows(1)
                .name("Test Page 3")
                .preventClick()
                .button(4, new SimpleButton(new ItemStack(Material.GOLD_INGOT), e -> e.getWhoClicked().sendMessage("You clicked the gold!")))
                .build()
        );

        gui.open(event.getPlayer());
    }

}