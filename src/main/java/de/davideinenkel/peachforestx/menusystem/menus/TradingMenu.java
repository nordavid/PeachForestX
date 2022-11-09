package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.menusystem.Menu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.trading.Trade;
import de.davideinenkel.peachforestx.utility.CustomHead;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class TradingMenu extends Menu {
    Trade trade;

    Integer[] forbiddenSlots = {4,13,22,27,28,29,30,31,32,33,34,35,42,43,44};
    Integer[] droppableSlots = {1,2,3,10,11,12,19,20,21};

    public TradingMenu(PlayerMenuUtility playerMenuUtility, Trade trade) {
        super(playerMenuUtility);
        this.trade = trade;
    }

    @Override
    public Boolean isEventSelfManaged() {
        return true;
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
        Integer clickedSlot = e.getRawSlot();
        //if(!Arrays.stream(droppableSlots).anyMatch(i -> i == clickedSlot) || clickedSlot < getSlots()) e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        InventoryAction action = e.getAction();
        // Debug
        e.getWhoClicked().sendMessage("RawSlot: " + e.getRawSlot() + " Slot: " + e.getSlot() + " Action " + e.getAction());

        if(action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.PLACE_SOME) {
            player.sendMessage("HELLO????");
            player.sendMessage("Amount: " + e.getCursor().getAmount());
        }

        //tradingSystem.updateTradingMenus();
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

        //https://textures.minecraft.net/texture/a7695f96dda626faaa010f4a5f28a53cd66f77de0cc280e7c5825ad65eedc72e

        ItemStack yes = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/a7695f96dda626faaa010f4a5f28a53cd66f77de0cc280e7c5825ad65eedc72e");
        yes = addMetaToItem(yes, ChatColor.GREEN + "" + ChatColor.BOLD + "Annehmen");
        ItemStack no = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/3c4d7a3bc3de833d3032e85a0bf6f2bef7687862b3c6bc40ce731064f615dd9d");
        no = addMetaToItem(no, ChatColor.RED + "" + ChatColor.BOLD + "Ablehnen");

        ItemStack accept = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemStack decline = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        accept = addMetaToItem(accept, ChatColor.GREEN + "" + ChatColor.BOLD + "Annehmen");
        decline = addMetaToItem(decline, ChatColor.RED + "" + ChatColor.BOLD + "Ablehnen");
        inventory.setItem(44, yes);
        inventory.setItem(43, no);
        if(isHost()) inventory.setItem(42, yes);
    }

    private Boolean isHost() {
        if(trade.getHost() == playerMenuUtility.getOwner()) return true;
        return false;
    }
}
