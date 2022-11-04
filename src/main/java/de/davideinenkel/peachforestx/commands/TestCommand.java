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

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            //player.getInventory().remove(Material.PLAYER_HEAD);
            //player.getInventory().setItem(8, null);

            //PeachForestX.deathChests
            for (Location loc: PeachForestX.deathChests) {

                Location chestLoc = Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).getLocation();
                String deathHoloID = "DeathHoloID" + chestLoc.getBlockX() + chestLoc.getBlockY() + chestLoc.getBlockZ();
                DHAPI.removeHologram(deathHoloID);

                Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).setType(Material.AIR);
                Chat.sendMsgWithDefaultPrefix(player, "Todestruhen entfernt");
            }
            return true;
        }
        return false;
    }
}
