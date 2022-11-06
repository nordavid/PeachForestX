package de.davideinenkel.peachforestx.utility;

import de.davideinenkel.peachforestx.PeachForestX;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TargetTracker {
    public static HashMap<Player, Player> targets = new HashMap<>();

    public static void runTargetScheduler() {
        new BukkitRunnable() {
            @Override
            public void run() {
                //your action to do every minute
                for (Map.Entry<Player, Player> entry : targets.entrySet()) {
                    Player player = entry.getKey();
                    Player target = entry.getValue();

                    player.setCompassTarget(target.getLocation());
                }
            }
        }.runTaskTimer(PeachForestX.getInstance(),0,20*1);
    }
}
