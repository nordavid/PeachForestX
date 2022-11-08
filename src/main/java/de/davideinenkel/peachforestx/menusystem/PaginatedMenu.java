package de.davideinenkel.peachforestx.menusystem;

import de.davideinenkel.peachforestx.utility.CustomHead;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/*

A class extending the functionality of the regular Menu, but making it Paginated

This pagination system was made from Jer's code sample. <3

 */

public abstract class PaginatedMenu extends Menu {

    //Keep track of what page the menu is on
    protected int page = 0;
    //28 is max items because with the border set below,
    //28 empty slots are remaining.
    protected int maxItemsPerPage = 36;
    //the index represents the index of the slot
    //that the loop is on
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){

        ItemStack left = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/ad73cf66d31b83cd8b8644c15958c1b73c8d97323b801170c1d8864bb6a846d");
        ItemStack right = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/c86185b1d519ade585f184c34f3f3e20bb641deb879e81378e4eaf209287");

        inventory.setItem(50, super.FILLER_GLASS);
        inventory.setItem(51, addMetaToItem(left, ChatColor.GREEN + "Vorherige Seite"));
        inventory.setItem(52, addMetaToItem(right, ChatColor.GREEN + "Nächste Seite"));
        inventory.setItem(53, makeItem(Material.BARRIER, ChatColor.RED + "Schließen"));


        for (int i = 36; i < 45; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}

