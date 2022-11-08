package de.davideinenkel.peachforestx.data;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ShopCategory {

    public ItemStack category;
    private String index;

    public ShopCategory(String name, String iconItem, String index){
        this.index = index;
        this.category = makeShopCategory(name, iconItem);
    }

    private ItemStack makeShopCategory(String name, String iconItem){
        ItemStack category = new ItemStack(Material.getMaterial(iconItem));
        ItemMeta meta = category.getItemMeta();
        meta.setDisplayName(name);

        meta.getPersistentDataContainer().set(new NamespacedKey(PeachForestX.getInstance(), "category"), PersistentDataType.STRING, index);

        category.setItemMeta(meta);

        return category;
    }
}
