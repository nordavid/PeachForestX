package de.davideinenkel.pfshop.commands;

import de.davideinenkel.pfshop.ExampleGui;
import de.davideinenkel.pfshop.Pfshop;
import de.davideinenkel.pfshop.utility.PlayerConfig;
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
            // Here we need to give items to our player
            ExampleGui gui = new ExampleGui();
            gui.openInventory(player);

            PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") + 10);
            //PlayerConfig.save();
            Integer bal = PlayerConfig.get().getInt("balance");

            //String test = PlayerConfig.get().get("balance").toString();
            player.sendTitle("Huan", "heheheh: " + bal.toString() + "P", 10, 70, 20);

            getServer().getPluginManager().registerEvents(gui, Pfshop.getInstance());
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
