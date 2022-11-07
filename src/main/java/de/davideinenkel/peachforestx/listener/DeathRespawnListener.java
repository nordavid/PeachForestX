package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.Holograms;
import de.davideinenkel.peachforestx.utility.MenuItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Iterator;

public class DeathRespawnListener implements Listener {

    public static HashMap<Player , ItemStack[]> items = new HashMap<>();

    @EventHandler public void onMsg(AsyncPlayerChatEvent e) {
        e.setFormat(PeachForestX.getMainConfig().getString("ChatFormat"));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        Iterator<Block> i = e.blockList().iterator();
        while (i.hasNext()) {
            Block block = i.next();

            if (block.getType() == Material.CHEST) {
                // Ignore chest on explosions
                i.remove();
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        player.getInventory().setItem(8, MenuItem.getMenuItem(player));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if(items.containsKey(player)) {
            // remove Menu on player death
            e.getDrops().remove(MenuItem.getMenuItem(player));
            Chat.sendMsgWithDefaultPrefix(player, "Nehme zuerst deine existierende Todestruhe auf, bevor eine neue gespawnt werden kann!", true);
            return;
        }

        Location deathLoc = e.getEntity().getLocation();
        Location chestLoc = player.getWorld().getBlockAt(deathLoc).getLocation();

        //Store player inventory in map
        ItemStack[] content = e.getEntity().getInventory().getContents();
        items.put(e.getEntity(), content);
        e.getEntity().getInventory().clear();

        // Remove all drops on death
        e.getDrops().clear();

        player.getWorld().getBlockAt(chestLoc).setType(Material.CHEST);

        // Place hologram
        String holoName = Holograms.addDeathHolo(chestLoc, player);

        // Add location and holoid to hashmap
        PeachForestX.deathChests.put(chestLoc, holoName);

        if(!(player.getWorld().getBlockAt(deathLoc).getType() == Material.CHEST)) return;
        if(!(player.getWorld().getBlockAt(deathLoc).getState() instanceof TileState)) return;

        // Update chest state and pdc
        TileState state = (TileState) player.getWorld().getBlockAt(deathLoc).getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(PeachForestX.getInstance(), "death-chest");
        NamespacedKey holoKey = new NamespacedKey(PeachForestX.getInstance(), "death-chest-holo");

        container.set(key, PersistentDataType.STRING, player.getUniqueId().toString());
        container.set(holoKey, PersistentDataType.STRING, holoName);

        state.update();

        Chat.sendMsgWithDefaultPrefix(player, "Todestruhe platziert [" + Math.floor(deathLoc.getX()) + ", " + Math.floor(deathLoc.getY()) + ", " + Math.floor(deathLoc.getZ()) + "]", "§7");
    }
}
