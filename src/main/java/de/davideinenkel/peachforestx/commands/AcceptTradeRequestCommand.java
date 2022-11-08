package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.menusystem.menus.TradingMenu;
import de.davideinenkel.peachforestx.utility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptTradeRequestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player target = (Player) commandSender;
        Player player = Bukkit.getPlayer(args[0]);

        Chat.sendMsgWithDefaultPrefix(player, target.getDisplayName() + "§a hat deine Trade-Anfrage angenommen.");
        Chat.sendMsgWithDefaultPrefix(target, "§aDu hast die Trade-Anfrage angenommen.");

        new TradingMenu(PeachForestX.getPlayerMenuUtility(player), target).open();
        new TradingMenu(PeachForestX.getPlayerMenuUtility(target), player).open();

        return false;
    }
}
