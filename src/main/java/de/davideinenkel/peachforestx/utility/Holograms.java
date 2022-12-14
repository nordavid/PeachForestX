package de.davideinenkel.peachforestx.utility;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Holograms {
    public static String addDeathHolo(Location loc, Player player) {
        String deathHoloID = getHoloIdFromLocation(loc);
        Location holoLoc = loc.clone();
        List<String> lines = Arrays.asList("R.I.P.", player.getDisplayName());
        Hologram hologram = DHAPI.createHologram(deathHoloID, holoLoc.add(0.5, 1.5, 0.5), lines);

        return hologram.getName();
    }

    public static void removeDeathHolo(Location loc) {
        String deathHoloID = getHoloIdFromLocation(loc);
        DHAPI.removeHologram(deathHoloID);
    }

    public  static String getHoloIdFromLocation(Location loc) {
        return "DeathHoloID" + loc.getBlockX() + loc.getBlockY() + loc.getBlockZ();
    }
}
