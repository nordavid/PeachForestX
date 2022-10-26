package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.MenuItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DeathRespawnListener implements Listener {

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

        player.getWorld().getBlockAt(deathLoc).setType(Material.CHEST);

        if(!(player.getWorld().getBlockAt(deathLoc).getType() == Material.CHEST)) return;

        if(!(player.getWorld().getBlockAt(deathLoc).getState() instanceof TileState)) return;

        TileState state = (TileState) player.getWorld().getBlockAt(deathLoc).getState();
        PersistentDataContainer container = state.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(PeachForestX.getInstance(), "death-chest");

        container.set(key, PersistentDataType.STRING, player.getUniqueId().toString());

        state.update();

        player.sendMessage("Todestruhe platziert [" + deathLoc.getX() + ", " + deathLoc.getY() + ", " + deathLoc.getZ() + "]");

    }
}
