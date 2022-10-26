package de.davideinenkel.peachforestx;

import de.davideinenkel.peachforestx.utility.PlayerConfig;
import de.davideinenkel.peachforestx.utility.CustomHead;
import de.davideinenkel.peachforestx.utility.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.bukkit.Bukkit.getServer;

public class ExampleGui implements Listener {
    private Inventory inv;
    private final Player p;
    private Integer balance;

    public ExampleGui(Player player) {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        p = player;
        PlayerConfig.load(player);
        Integer bal = PlayerConfig.get().getInt("balance");
        balance = bal;
        inv = Bukkit.createInventory(null, 9, "Guthaben: §l" + bal + "P");

        // Put the items into the inventory
        initializeItems();

    }

    public void updateGui() {
        p.closeInventory();
        PlayerConfig.load(p);
        Integer bal = PlayerConfig.get().getInt("balance");
        balance = bal;
        inv = Bukkit.createInventory(null, 9, "Guthaben: §l" + bal + "P");

        // Put the items into the inventory
        initializeItems();
        openInventory(p);
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {

        ItemStack peach = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/16b4d27bc1b466e3ab4123cbe241974a813573a7c36c5e5b8daf9c85a5ce0");
        ItemStack reward = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6");
        ItemStack shop = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/34ccb52750e97e830aebfa8a21d5da0d364d0fdad9fb0cc220fe2ca8411842c3");

        peach = addMetaToItem(peach, ChatColor.GOLD + "" + ChatColor.BOLD + "Peaches", "§fDu Drecksau hast " + balance + " Peaches");
        reward = addMetaToItem(reward, ChatColor.GREEN + "" + ChatColor.BOLD + "Reward abholen", "§fHol Dir dein daily reward ab Du Huan" , "§d" + Rewards.getRewardString(p));
        shop = addMetaToItem(shop,ChatColor.RED + "" + ChatColor.BOLD + "Shop", "§fHol Dir doch was feines lul");

        inv.setItem(0, peach);
        inv.setItem(4, reward);
        inv.setItem(8, shop);

        inv.setItem(1, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        inv.setItem(2, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        inv.setItem(3, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        inv.setItem(5, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        inv.setItem(6, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        inv.setItem(7, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "", ""));
        //inv.addItem(createGuiItem(Material.IRON_HELMET, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    protected  ItemStack addMetaToItem(final ItemStack is, final String name, final String... lore) {
        final ItemStack item = is;
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
        getServer().getPluginManager().registerEvents(this, PeachForestX.getInstance());
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        if(e.getClick() == ClickType.SWAP_OFFHAND) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("xD");
        }

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        if(e.getClick() == ClickType.LEFT) {

            final Player p = (Player) e.getWhoClicked();

            // Using slots click is a best option for your inventory click's
            //p.sendMessage("You clicked at slot " + e.getRawSlot());

            if(e.getRawSlot() == 4) {
               if (Rewards.getIsRewardReady(p)) {
                   PlayerConfig.load(p);
                   PlayerConfig.get().set("balance", PlayerConfig.get().getInt("balance") + 300);
                   PlayerConfig.get().set("lastReward", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                   PlayerConfig.save();
                   p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
                   p.sendMessage("Reward erhalten");
                   updateGui();
               }
               else {
                   p.sendMessage("Nee nee");
               }
            }
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if(e.getInventory().equals(inv)) {
            HandlerList.unregisterAll(this);
        }
    }
}