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

import java.util.Map;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            //player.getInventory().remove(Material.PLAYER_HEAD);
            //player.getInventory().setItem(8, null);

            for (Map.Entry<Location, String> entry : PeachForestX.deathChests.entrySet()) {
                Location loc = entry.getKey();
                String id = entry.getValue();

                Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).setType(Material.AIR);
                DHAPI.removeHologram(id);
                Chat.sendMsgWithDefaultPrefix(player, "Todestruhen entfernt");
            }
            PeachForestX.deathChests.clear();

            return true;
        }
        return false;
    }
}
