package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.trading.TradingSystem;
import de.davideinenkel.peachforestx.utility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeclineTradeRequestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player target = (Player) commandSender;
        Player host = Bukkit.getPlayer(args[0]);

        if(TradingSystem.tradeRequests.containsKey(host)) {
            TradingSystem.tradeRequests.remove(host);
            Chat.sendMsgWithDefaultPrefix(host, target.getDisplayName() + "§c hat deine Trade-Anfrage abgelehnt.");
            Chat.sendMsgWithDefaultPrefix(target, "§cDu hast die Trade-Anfrage abgelehnt.");
            return true;
        }


        return false;
    }
}
