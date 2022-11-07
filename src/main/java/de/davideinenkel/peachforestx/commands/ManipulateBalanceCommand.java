package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ManipulateBalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID nordavid = UUID.fromString("9b8c95b6-3168-48d9-b565-8216d78c05ff");
            if(!player.getUniqueId().equals(nordavid)) return false;
        }

        if(args.length == 3) {
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) return false;

            try {
                Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            Integer amount = Integer.parseInt(args[2]);

            if(args[1].equalsIgnoreCase("add")) {
                PlayerConfig.load(target);
                PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") + amount);
                PlayerConfig.save();
            } else if (args[1].equalsIgnoreCase("set")) {
                PlayerConfig.load(target);
                PlayerConfig.get().set("balance", amount);
                PlayerConfig.save();
            } else if (args[1].equalsIgnoreCase("sub")) {
                PlayerConfig.load(target);
                PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") - amount);
                PlayerConfig.save();
            } else if (args[1].equalsIgnoreCase("get")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerConfig.load(target);
                    Chat.sendMsgWithDefaultPrefix(player, target.getDisplayName() + "´s Balance: " + PlayerConfig.get().getInt("balance"));
                }
            }
        }
        return true;
    }
}
