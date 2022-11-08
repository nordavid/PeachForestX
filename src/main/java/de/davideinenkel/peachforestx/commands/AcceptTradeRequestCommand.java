package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.trading.TradingSystem;
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
        Player host = Bukkit.getPlayer(args[0]);

        if(TradingSystem.tradeRequests.containsKey(host) && TradingSystem.tradeRequests.get(host) == target) {
            Chat.sendMsgWithDefaultPrefix(host, target.getDisplayName() + "§a hat deine Trade-Anfrage angenommen.");
            Chat.sendMsgWithDefaultPrefix(target, "§aDu hast die Trade-Anfrage angenommen.");

            new TradingSystem(host, target);

            TradingSystem.tradeRequests.remove(host);
            return true;
        }



        return false;
    }
}
