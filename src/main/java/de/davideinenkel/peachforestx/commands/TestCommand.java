package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.Chat;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            //player.getInventory().remove(Material.PLAYER_HEAD);
            //player.getInventory().setItem(8, null);

            Iterator<Location> i = PeachForestX.deathChests.iterator();
            while (i.hasNext()) {
                Location loc = i.next(); // must be called before you can call i.remove()

                //Location chestLoc = Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).getLocation();
                //String deathHoloID = "DeathHoloID" + chestLoc.getBlockX() + chestLoc.getBlockY() + chestLoc.getBlockZ();
                //DHAPI.removeHologram(deathHoloID);
                player.sendMessage("1: " +loc);
                //Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).setType(Material.GLASS);
                player.getWorld().getBlockAt(loc).setType(Material.RED_STAINED_GLASS);
                Chat.sendMsgWithDefaultPrefix(player, "Todestruhen entfernt");

                i.remove();
            }

            return true;
        }
        return false;
    }
}
