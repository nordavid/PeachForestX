package de.davideinenkel.pfshop.listener;

import de.davideinenkel.pfshop.utility.PlayerConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerConfig.create(event.getPlayer());

        UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
        if(event.getPlayer().getUniqueId().equals(nordavid)) {
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerConfig.save();

        UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
        if(event.getPlayer().getUniqueId().equals(nordavid)) {
            event.setQuitMessage("");
        }
    }
}
