package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.menus.KillPlayerMenu;
import de.davideinenkel.peachforestx.menusystem.menus.PlayerCompassMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            new PlayerCompassMenu(PeachForestX.getPlayerMenuUtility(player)).open();
            return true;
        }
        return false;
    }
}
