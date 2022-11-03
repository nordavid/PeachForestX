package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.utility.MenuItem;
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
            player.getInventory().setItem(8, null);
            return true;
        }
        return false;
    }
}
