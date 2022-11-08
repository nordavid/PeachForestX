package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.menusystem.Menu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.utility.CustomHead;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TradingMenu extends Menu {
    Player target;
    public TradingMenu(PlayerMenuUtility playerMenuUtility, Player target) {
        super(playerMenuUtility);
        this.target = target;
    }

    @Override
    public String getMenuName() {
        return "Trading Menü";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        for (int i = 27; i <= 35; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
        inventory.setItem(4, super.FILLER_GLASS);
        inventory.setItem(13, super.FILLER_GLASS);
        inventory.setItem(22, super.FILLER_GLASS);
        inventory.setItem(42, super.FILLER_GLASS);


        ItemStack accept = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemStack decline = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        accept = addMetaToItem(accept, ChatColor.GREEN + "" + ChatColor.BOLD + "Annehmen");
        decline = addMetaToItem(decline, ChatColor.RED + "" + ChatColor.BOLD + "Ablehnen");
        inventory.setItem(44, accept);
        inventory.setItem(43, decline);
    }
}
