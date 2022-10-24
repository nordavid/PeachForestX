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
import org.bukkit.util.Vector;

public class HotbarShopListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(p.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Menü") && p.getInventory().getHeldItemSlot() == 8) {
                    ExampleGui gui = new ExampleGui(p);
                    gui.openInventory(p);
                    //p.setVelocity(new Vector(0, 64, 0));
                }
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if(e.getItemInHand().getType() == Material.PLAYER_HEAD) {
            if (e.getItemInHand().getItemMeta().getDisplayName().contains("Menü")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getType() == Material.PLAYER_HEAD) {
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Menü")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Menü")) {
                e.setCancelled(true);
            }
        }

        //if(e.getRawSlot() == 8 && e.getInventory().getType().equals(InventoryType.PLAYER)) {
        //    e.setCancelled(true);
        //}
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if(e.getCursor() == null) return;
        if(e.getCursor().getType() == Material.PLAYER_HEAD) {
            if (e.getCursor().getItemMeta().isUnbreakable()) {
                e.setCancelled(true);
            }
        }
    }
}
