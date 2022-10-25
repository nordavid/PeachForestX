package de.davideinenkel.pfshop.commands;

import de.davideinenkel.pfshop.ExampleGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class OpenShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ExampleGui gui = new ExampleGui(player);
            gui.openInventory(player);
            return true;
        }
        return false;
    }
}
