package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.MenuItem;
import de.davideinenkel.peachforestx.utility.PlayerConfig;
import de.davideinenkel.peachforestx.utility.TargetTracker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class JoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerConfig.create(player);
        LocalDateTime current = LocalDateTime.now();


        String lastTimeOn = PlayerConfig.get().getString("lastTimeOnline");

        player.getInventory().setItem(8, MenuItem.getMenuItem(player));

        if(lastTimeOn == null) {
            // first join
            event.setJoinMessage(PeachForestX.getMainConfig().getString("Prefix") + " §7Der Huan " + player.getDisplayName() + " ist da. Bitte geh nie wieder");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastDateTime = LocalDateTime.parse(lastTimeOn, formatter);

        long hours = ChronoUnit.HOURS.between(lastDateTime, current);

        if(hours == 0) {
            event.setJoinMessage(PeachForestX.getMainConfig().getString("Prefix") + " §7Der Huan " + player.getDisplayName() + " ist da");
            return;
        }

        event.setJoinMessage(PeachForestX.getMainConfig().getString("Prefix") + " §7Der Huan " + player.getDisplayName() + " ist da (Letztes mal on vor: " + hours + "h)");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        TargetTracker.targets.remove(event.getPlayer());

        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        PlayerConfig.load(event.getPlayer());
        PlayerConfig.get().set("lastTimeOnline", currentDateTime);
        PlayerConfig.save();

        //-----

        UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
        if(event.getPlayer().getUniqueId().equals(nordavid)) {
            event.setQuitMessage("");
        }
        else {
            event.setQuitMessage(PeachForestX.getMainConfig().getString("Prefix") + "§7Ciao " + event.getPlayer().getDisplayName());
        }
    }
}
