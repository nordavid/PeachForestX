package de.davideinenkel.peachforestx.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExpListener implements Listener {
    @EventHandler
    public void onExp(PlayerExpChangeEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("exp: " + e.getAmount());
    }
}
