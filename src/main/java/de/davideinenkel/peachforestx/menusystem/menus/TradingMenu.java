package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.menusystem.Menu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.trading.Trade;
import de.davideinenkel.peachforestx.utility.CustomHead;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

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
        if(!Arrays.stream(droppableSlots).anyMatch(i -> i == clickedSlot) && clickedSlot < getSlots()) e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        if(clickedSlot == 43 && !trade.getLockStateForPlayer(player)) {
            inventory.setItem(43, new ItemStack(Material.AIR));
            trade.lockTrade(player);
            return;
        } else if (clickedSlot == 43 && trade.getTradeState() == Trade.TradeState.READYTOTRADE) {
            player.sendMessage("trraaadddding");
            trade.tradeAccepted();
            return;
        } else if (clickedSlot == 44) {
            trade.tradeDeclined();
            return;
        }

        if(trade.getLockStateForPlayer(player)) {
            e.setCancelled(true);
            return;
        }

        InventoryAction action = e.getAction();

        // Debug
        e.getWhoClicked().sendMessage("RawSlot: " + e.getRawSlot() + " Slot: " + e.getSlot() + " Action " + e.getAction());

        // TODO: Handle move to inventory (shift)
        // Place item in drop area
        if(Arrays.stream(droppableSlots).anyMatch(i -> i == clickedSlot)) {
            if(action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.PLACE_SOME || action == InventoryAction.SWAP_WITH_CURSOR) {
                //player.sendMessage("Amount: " + e.getCursor().getAmount());
                trade.addItem(clickedSlot, e.getCursor().clone(), isHost() ? Trade.Type.HOST : Trade.Type.TARGET);
                trade.updateTradingMenus(isHost() ? Trade.Type.TARGET : Trade.Type.HOST);
            } else if(action == InventoryAction.PICKUP_ALL) {
                trade.removeItem(clickedSlot, e.getCurrentItem(), isHost() ? Trade.Type.HOST : Trade.Type.TARGET);
                trade.updateTradingMenus(isHost() ? Trade.Type.TARGET : Trade.Type.HOST);
            }
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

        inventory.setItem(26, super.FILLER_GLASS);

        if(isHost() && trade.getLockStateForPlayer(trade.getHost())) {
            inventory.setItem(18, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
        }
        else if (isHost() && !trade.getLockStateForPlayer(trade.getHost())){
            inventory.setItem(18, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
        }

        if(!isHost() && trade.getLockStateForPlayer(trade.getTarget())) {
            inventory.setItem(18, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
        }
        else if (!isHost() && !trade.getLockStateForPlayer(trade.getTarget())){
            inventory.setItem(18, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
        }


        if(isHost() && trade.getLockStateForPlayer(trade.getTarget())) {
            inventory.setItem(26, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
        }
        else if (isHost() && !trade.getLockStateForPlayer(trade.getTarget())){
            inventory.setItem(26, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
        }

        if(!isHost() && trade.getLockStateForPlayer(trade.getHost())) {
            inventory.setItem(26, new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1));
        }
        else if (!isHost() && !trade.getLockStateForPlayer(trade.getHost())){
            inventory.setItem(26, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
        }

        //ItemStack accept = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        //ItemStack decline = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);

        //https://textures.minecraft.net/texture/8a59084f659df462667d6ac711fc862d8cc5b0ee49bd4d50e85bb54c28a946f4

        inventory.setItem(0, CustomHead.getPlayerHead(playerMenuUtility.getOwner(), playerMenuUtility.getOwner().getDisplayName()));
        inventory.setItem(8, CustomHead.getPlayerHead(isHost() ? trade.getTarget() : trade.getHost(), isHost() ? trade.getTarget().getDisplayName() : trade.getHost().getDisplayName()));

        ItemStack peach = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/16b4d27bc1b466e3ab4123cbe241974a813573a7c36c5e5b8daf9c85a5ce0");
        peach = addMetaToItem(peach, ChatColor.GOLD + "" + ChatColor.BOLD + "Peaches", "§fxxx");
        inventory.setItem(9, peach);
        inventory.setItem(17, peach);

        ItemStack lock = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/c6e45d7cf4ff21732172d71b2a340626c708397626319b6d57d76c9ac48c675f");
        lock = addMetaToItem(lock, "§a§lAngebot locken");
        ItemStack accept = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6");
        accept = addMetaToItem(accept, ChatColor.GREEN + "" + ChatColor.BOLD + "Angebot Annehmen");
        ItemStack decline = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/3c4d7a3bc3de833d3032e85a0bf6f2bef7687862b3c6bc40ce731064f615dd9d");
        decline = addMetaToItem(decline, ChatColor.RED + "" + ChatColor.BOLD + "Abbrechen");

        if(trade.getTradeState() == Trade.TradeState.READYTOTRADE) {
            inventory.setItem(43, accept);
        } else if (!trade.getLockStateForPlayer(playerMenuUtility.getOwner())) {
            inventory.setItem(43, lock);
        }

        inventory.setItem(44, decline);

        // Place Host Items
        for(Map.Entry<Integer, ItemStack> entry : trade.getItems(Trade.Type.HOST).entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            if(!isHost()) slot = getInvertedSlot(slot);
            Bukkit.getLogger().info("xDS slot: " + slot + " item " + is.getType());

            inventory.setItem(slot, is);
        }

        // Place Target Items
        for(Map.Entry<Integer, ItemStack> entry : trade.getItems(Trade.Type.TARGET).entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            if(isHost()) slot = getInvertedSlot(slot);
            Bukkit.getLogger().info("xDSt slot: " + slot);
            inventory.setItem(slot, is);
        }
    }

    public Integer getInvertedSlot(Integer slot) {
        if(slot < 4) return 8 - slot;
        else if (slot < 13) return 26 - slot;
        else if (slot < 22) return 44 - slot;
        return null;
    }

    private Boolean isHost() {
        if(trade.getHost() == playerMenuUtility.getOwner()) return true;
        return false;
    }
}
