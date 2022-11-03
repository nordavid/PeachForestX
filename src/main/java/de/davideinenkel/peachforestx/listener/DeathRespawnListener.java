package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.MenuItem;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

        Player player = e.getEntity();

        ItemStack[] content = e.getEntity().getInventory().getContents();
        items.put(e.getEntity(), content);
        e.getEntity().getInventory().clear();

        // remove Menu on player death
        //e.getDrops().remove(MenuItem.getMenuItem(player));
        e.getDrops().clear();

        player.getWorld().getBlockAt(deathLoc).setType(Material.CHEST);

        Location chestLoc = player.getWorld().getBlockAt(deathLoc).getLocation();
        String deathHoloID = "DeathHoloID" + chestLoc.getBlockX() + chestLoc.getBlockY() + chestLoc.getBlockZ();
        Location holoLoc = chestLoc.add(0.5, 1.5, 0.5);

        if(!(player.getWorld().getBlockAt(deathLoc).getType() == Material.CHEST)) return;

        if(!(player.getWorld().getBlockAt(deathLoc).getState() instanceof TileState)) return;

        TileState state = (TileState) player.getWorld().getBlockAt(deathLoc).getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(PeachForestX.getInstance(), "death-chest");

        container.set(key, PersistentDataType.STRING, player.getUniqueId().toString());

        state.update();

        PeachForestX.deathChests.add(deathLoc);

        //Set hologram
        List<String> lines = Arrays.asList("§6" + player.getDisplayName() + "´s Todestruhe");
        Hologram hologram = DHAPI.createHologram(deathHoloID, holoLoc, lines);

        player.sendMessage("Todestruhe platziert [" + Math.floor(deathLoc.getX()) + ", " + Math.floor(deathLoc.getY()) + ", " + Math.floor(deathLoc.getZ()) + "]");

    }
}
