package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.data.ShopItem;
import de.davideinenkel.peachforestx.menusystem.PaginatedMenu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.PlayerConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ShopMenu extends PaginatedMenu {

    ArrayList<ShopItem> shopItems = new ArrayList<>();

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        loadItems();
    }

    @Override
    public String getMenuName() {
        PlayerConfig.load(playerMenuUtility.getOwner());
        Integer bal = PlayerConfig.get().getInt("balance");
        return "Guthaben §l" + bal + "P " + "§r[Seite " + (page + 1) + "]";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        //super.open();

        Player p = (Player) e.getWhoClicked();

        if (!e.getCurrentItem().getType().equals(Material.BARRIER) && !e.getCurrentItem().getType().equals(Material.PLAYER_HEAD) && !e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            ItemStack clickedShopItem = e.getCurrentItem();
            String displayName = ChatColor.stripColor(clickedShopItem.getItemMeta().getDisplayName());
            Integer cost = Integer.parseInt(displayName.split("\\[")[1].split("P")[0]);

            PlayerConfig.load(p);
            if(PlayerConfig.get().getInt("balance") >= cost) {
                PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") - cost);
                PlayerConfig.save();

                p.getInventory().addItem(new ItemStack(clickedShopItem.getType(), clickedShopItem.getAmount()));
                Chat.sendMsgWithDefaultPrefix(p, cost + "P abgebucht");
                super.open();
            }
        }
        else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            //close inventory
            p.closeInventory();

        }
        else if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")){
                if (page == 0){
                    Chat.sendMsgWithDefaultPrefix(p, "Du bist breits auf der ersten Seite", "§7");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")){
                if (!((index + 1) >= shopItems.size())){
                    page = page + 1;
                    super.open();
                }else{
                    Chat.sendMsgWithDefaultPrefix(p, "Du bist auf der letzten Seite", "§7");
                }
            }
        }
    }

    @Override
    public void setMenuItems() {

        addMenuBorder();

        ///////////////////////////////////// Pagination loop template
        if(shopItems != null && !shopItems.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= shopItems.size()) break;
                if (shopItems.get(index) != null){
                    ///////////////////////////

                    inventory.addItem(shopItems.get(index).item);

                    ////////////////////////
                }
            }
        }
    }

    private void loadItems() {
        FileConfiguration config = PeachForestX.getMainConfig();
        if(config == null) return;
        ConfigurationSection shopItemsCS = config.getConfigurationSection("Shop.Items");

        for (String s : shopItemsCS.getKeys(false)) {
            shopItems.add(new ShopItem(shopItemsCS.getString(s + ".name"), shopItemsCS.getString(s + ".type").toUpperCase(), shopItemsCS.getStringList(s + ".lore"), shopItemsCS.getInt(s + ".amount"), shopItemsCS.getInt(s + ".cost")));
        }
    }
}
