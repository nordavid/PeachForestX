package de.davideinenkel.pfshop.listener;

import de.davideinenkel.pfshop.ExampleGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class HotbarShopListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(p.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("menu") && p.getInventory().getHeldItemSlot() == 8) {
                    ExampleGui gui = new ExampleGui(p);
                    gui.openInventory(p);
                }
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if(e.getItemInHand().getType() == Material.PLAYER_HEAD) {
            if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("menu")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getType() == Material.PLAYER_HEAD) {
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase("menu")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("menu")) {
                e.setCancelled(true);
            }
        }

        //if(e.getRawSlot() == 8 && e.getInventory().getType().equals(InventoryType.PLAYER)) {
        //    e.setCancelled(true);
        //}
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if(e.getCursor().getType() == Material.PLAYER_HEAD) {
            if (e.getCursor().getItemMeta().isUnbreakable()) {
                e.setCancelled(true);
            }
        }
    }
}
