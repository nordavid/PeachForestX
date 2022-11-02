package de.davideinenkel.peachforestx.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopItem {
    public ItemStack item;
    public Integer cost;

    public ShopItem(String name, String type, List<String> lore, Integer amount, Integer cost) {
        this.cost = cost;
        this.item = makeShopItem(type, name, amount, lore, cost);
    }

    private ItemStack makeShopItem(String type, String name, Integer amount, List<String> lore, Integer cost) {
        Material mat = Material.getMaterial(type);

        ItemStack item = new ItemStack(mat, amount);

        List<String> newLore = new ArrayList<String>();

        for (String s : lore) {
            newLore.add("§r§9" + s);
        }
        newLore.add("§r§9hehehe" + 0 + (Math.random() * (100 - 0)));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§r" + name + " §7[§6" + cost + "P§7]");
        itemMeta.setLore(newLore);
        item.setItemMeta(itemMeta);

        return  item;
    }
}
