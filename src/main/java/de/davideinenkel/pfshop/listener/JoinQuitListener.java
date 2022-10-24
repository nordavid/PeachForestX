package de.davideinenkel.pfshop.listener;

import de.davideinenkel.pfshop.utility.PlayerConfig;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerConfig.create(event.getPlayer());

        LocalDateTime current = LocalDateTime.now();

        String lastTimeOn = PlayerConfig.get().getString("lastTimeOnline");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime lastDateTime = LocalDateTime.parse(lastTimeOn, formatter);

        long hours = ChronoUnit.HOURS.between(lastDateTime, current);

        event.setJoinMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Der Huan " + event.getPlayer().getDisplayName() + " ist da [Letztes mal on vor: " + hours + "h]");

        UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
        if(event.getPlayer().getUniqueId().equals(nordavid)) {
            //event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        PlayerConfig.load(event.getPlayer());
        PlayerConfig.get().set("lastTimeOnline", currentDateTime);
        PlayerConfig.save();

        UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
        if(event.getPlayer().getUniqueId().equals(nordavid)) {
            event.setQuitMessage("");
        }
    }
}
