package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.MenuItem;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeathRespawnListener implements Listener {

    public static HashMap<Player , ItemStack[]> items = new HashMap<>();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        player.getInventory().setItem(8, MenuItem.getMenuItem(player));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Location deathLoc = e.getEntity().getLocation();
        deathLoc.add(0, 0, 0);

        Player player = e.getEntity();

        ItemStack[] content = e.getEntity().getInventory().getContents();
        items.put(e.getEntity(), content);
        e.getEntity().getInventory().clear();

        // remove Menu on player death
        //e.getDrops().remove(MenuItem.getMenuItem(player));
        e.getDrops().clear();

        player.getWorld().getBlockAt(deathLoc).setType(Material.CHEST);

        List<String> lines = Arrays.asList("1asdijf asdfju asdf aposidf");
        Hologram hologram = DHAPI.createHologram("name", deathLoc.add(-0.5, 1.5, 0.5), lines);

        if(!(player.getWorld().getBlockAt(deathLoc).getType() == Material.CHEST)) return;

        if(!(player.getWorld().getBlockAt(deathLoc).getState() instanceof TileState)) return;

        TileState state = (TileState) player.getWorld().getBlockAt(deathLoc).getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(PeachForestX.getInstance(), "death-chest");

        container.set(key, PersistentDataType.STRING, player.getUniqueId().toString());

        state.update();

        player.sendMessage("Todestruhe platziert [" + Math.floor(deathLoc.getX()) + ", " + Math.floor(deathLoc.getY()) + ", " + Math.floor(deathLoc.getZ()) + "]");

    }
}
