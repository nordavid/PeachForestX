package de.davideinenkel.pfshop.commands;

import de.davideinenkel.pfshop.ExampleGui;
import de.davideinenkel.pfshop.utility.PlayerHead;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class GetMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack menuItem = PlayerHead.getPlayerHead("https://textures.minecraft.net/texture/192e0cc32d8982212b35f40d9ea53f74b6bfa7bdd223f75b0c32860af735f3d7");

            ItemStack ownHead = new ItemStack(Material.PLAYER_HEAD);

            final SkullMeta meta = (SkullMeta) ownHead.getItemMeta();



            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Menü " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "[Rechtsklick]");


            String lore;
            lore = ChatColor.GRAY + "Menü mit Rechtsklick öffnen";

            meta.setOwningPlayer(player);

            meta.setLore(Arrays.asList(lore));

            ownHead.setItemMeta(meta);

            player.getInventory().setItem(8, ownHead);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
