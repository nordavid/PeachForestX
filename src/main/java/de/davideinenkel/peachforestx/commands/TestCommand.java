package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.utility.Chat;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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

            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("bb")) {
                    BossBar bb = Bukkit.createBossBar(args[1], BarColor.PINK, BarStyle.SOLID);

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        bb.addPlayer(p);
                    }
                }
            }

            if(args.length == 3) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if(target == null) return false;

                if(args[1].equalsIgnoreCase("dn")) {
                    target.setDisplayName(args[2]);
                }
            }
            return true;
        }
        return false;
    }
}
