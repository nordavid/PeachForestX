package de.davideinenkel.pfshop.utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    public static ItemStack getMenuItem(Player player) {
        ItemStack ownHead = new ItemStack(Material.PLAYER_HEAD);

        final SkullMeta meta = (SkullMeta) ownHead.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Menü " + ChatColor.RESET + "" + ChatColor.GRAY + "[Rechtsklick]");
        meta.setOwningPlayer(player);
        //String lore;

        List<String> lore = new ArrayList<>();
        //List<String> lore = Arrays.asList(ChatColor.GRAY + "Menü mit Rechtsklick öffnen");
        lore.add(ChatColor.GRAY + "Menü mit Rechtsklick öffnen");
        //lore = ChatColor.GRAY + "Menü mit Rechtsklick öffnen";
        meta.setLore(lore);
        ownHead.setItemMeta(meta);
        return ownHead;
    }
}
