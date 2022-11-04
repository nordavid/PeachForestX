package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.ExampleGui;
import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.Holograms;
import de.davideinenkel.peachforestx.utility.MenuItem;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HotbarShopListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(p.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Menü") && p.getInventory().getHeldItemSlot() == 8) {
                    e.setCancelled(true);
                    ExampleGui gui = new ExampleGui(p);
                    gui.openInventory(p);
                    return;
                    //p.setVelocity(new Vector(0, 64, 0));
                }
            }
        }

        if(!(e.hasBlock())) return;
        if(!(e.getClickedBlock().getState() instanceof TileState)) return;
        TileState state = (TileState) e.getClickedBlock().getState();
        PersistentDataContainer container = state.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(PeachForestX.getInstance(), "death-chest");

        if(!container.has(key, PersistentDataType.STRING)) return;

        //container.get(key, PersistentDataType.STRING, "death-chest");
        if(e.getPlayer().getUniqueId().toString().equalsIgnoreCase(
                container.get(key, PersistentDataType.STRING)
        )) {
            // Remove chest
            e.getClickedBlock().setType(Material.AIR);

            // Give players items
            if(DeathRespawnListener.items.containsKey(e.getPlayer())){
                //e.getPlayer().getInventory().clear();
                for(ItemStack stack : DeathRespawnListener.items.get(e.getPlayer())){
                    if(stack != null){
                        if(stack.getType() != Material.PLAYER_HEAD) {
                            e.getPlayer().getInventory().addItem(stack);
                        }
                    }
                }

                DeathRespawnListener.items.remove(e.getPlayer());
            }

            // Play sound to player
            p.playSound(p.getLocation(), Sound.UI_LOOM_TAKE_RESULT, 10, 1);

            // Remove hologram
            NamespacedKey holoKey = new NamespacedKey(PeachForestX.getInstance(), "death-chest-holo");
            DHAPI.removeHologram(container.get(holoKey, PersistentDataType.STRING));
            PeachForestX.deathChests.remove(e.getClickedBlock().getLocation());
            return;
        }
        else {
            e.setCancelled(true);
            Chat.sendMsgWithDefaultPrefix(p, "Geh mal weg", true);
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
