package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.menusystem.Menu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.trading.Trade;
import de.davideinenkel.peachforestx.utility.CustomHead;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

public class TradingMenu extends Menu {
    Trade trade;

    Trade.Type menuType;

    Integer[] forbiddenSlots = {4,13,22,27,28,29,30,31,32,33,34,35,42,43,44};
    Integer[] droppableSlots = {1,2,3,10,11,12,19,20,21};

    public TradingMenu(PlayerMenuUtility playerMenuUtility, Trade trade) {
        super(playerMenuUtility);
        this.trade = trade;

        if(playerMenuUtility.getOwner() == trade.getHost()) menuType = Trade.Type.HOST;
        else menuType = Trade.Type.TARGET;
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

        // Debug
        e.getWhoClicked().sendMessage("RawSlot: " + e.getRawSlot() + " Slot: " + e.getSlot() + " Action " + e.getAction());
        player.sendMessage("State: " + trade.getTradeStateForPlayer(menuType) + " Type: " + menuType);

        // Handle interaction buttons
        if(clickedSlot == 43 && trade.getTradeStateForPlayer(menuType) == Trade.TradeState.TRADING) {
            //inventory.setItem(43, new ItemStack(Material.AIR));
            trade.lockTrade(player);
            return;
        } else if (clickedSlot == 43 && trade.canTradeBeAccepted()) {
            player.sendMessage("trraaadddding");
            trade.acceptTrade(player);
            return;
        } else if (clickedSlot == 44) {
            trade.tradeDeclined();
            return;
        }

        // Cancel trading area
        if(trade.getTradeStateForPlayer(menuType) != Trade.TradeState.TRADING) {
            e.setCancelled(true);
            return;
        }

        InventoryAction action = e.getAction();

        // TODO: Handle move to inventory (shift)
        // Place item in drop area
        if(Arrays.stream(droppableSlots).anyMatch(i -> i == clickedSlot)) {
            if(action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.PLACE_SOME || action == InventoryAction.SWAP_WITH_CURSOR) {
                trade.addItem(clickedSlot, e.getCursor().clone(), menuType);
            } else if(action == InventoryAction.PICKUP_ALL) {
                trade.removeItem(clickedSlot, e.getCurrentItem(), menuType);
            }
        }
    }

    @Override
    public void setMenuItems() {

        addTradingGui();
        addLockIndicators();
        addTradeItems();
        addInteractionButtons();

        inventory.setItem(0, CustomHead.getPlayerHead(playerMenuUtility.getOwner(), playerMenuUtility.getOwner().getDisplayName()));
        inventory.setItem(8, CustomHead.getPlayerHead(isHost() ? trade.getTarget() : trade.getHost(), isHost() ? trade.getTarget().getDisplayName() : trade.getHost().getDisplayName()));

        ItemStack peach = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/16b4d27bc1b466e3ab4123cbe241974a813573a7c36c5e5b8daf9c85a5ce0", "§a§lPeaches", "§fxxx");
        inventory.setItem(9, peach);
        inventory.setItem(17, peach);
    }

    private void addTradingGui() {
        for (int i = 27; i <= 35; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
        inventory.setItem(4, super.FILLER_GLASS);
        inventory.setItem(13, super.FILLER_GLASS);
        inventory.setItem(22, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
    }

    private void addInteractionButtons() {
        ItemStack clock = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/8a59084f659df462667d6ac711fc862d8cc5b0ee49bd4d50e85bb54c28a946f4", "§7§lWarte auf Trading-Partner...");
        ItemStack lock = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/c6e45d7cf4ff21732172d71b2a340626c708397626319b6d57d76c9ac48c675f", "§a§lAngebot locken");
        ItemStack accept = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6", "§a§lAngebot Annehmen");
        ItemStack decline = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/3c4d7a3bc3de833d3032e85a0bf6f2bef7687862b3c6bc40ce731064f615dd9d", "§c§lAbbrechen");

        if (trade.getTradeStateForPlayer(menuType) == Trade.TradeState.TRADEACCEPTED) {
            inventory.setItem(43, clock);
        } else if(trade.canTradeBeAccepted()) {
            inventory.setItem(43, accept);
        } else if (trade.getTradeStateForPlayer(menuType) == Trade.TradeState.LOCKED) {
            inventory.setItem(43, clock);
        } else if (trade.getTradeStateForPlayer(menuType) == Trade.TradeState.TRADING) {
            inventory.setItem(43, lock);
        }

        inventory.setItem(44, decline);
    }

    private void addLockIndicators() {
        Trade.TradeState tradeState = trade.getTradeStateForPlayer(menuType);
        if(tradeState == Trade.TradeState.TRADING) inventory.setItem(18, makeItem(Material.GRAY_STAINED_GLASS, ""));
        else if (tradeState == Trade.TradeState.LOCKED) inventory.setItem(18, makeItem(Material.RED_STAINED_GLASS, ""));
        else inventory.setItem(18, makeItem(Material.LIME_STAINED_GLASS, ""));

        tradeState = trade.getTradeStateForPlayer(menuType == Trade.Type.HOST ? Trade.Type.TARGET : Trade.Type.HOST);
        if(tradeState == Trade.TradeState.TRADING) inventory.setItem(26, makeItem(Material.GRAY_STAINED_GLASS, ""));
        else if (tradeState == Trade.TradeState.LOCKED) inventory.setItem(26, makeItem(Material.RED_STAINED_GLASS, ""));
        else inventory.setItem(26, makeItem(Material.LIME_STAINED_GLASS, ""));
    }

    private void addTradeItems() {
        // Place Host Items
        for(Map.Entry<Integer, ItemStack> entry : trade.getItems(Trade.Type.HOST).entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            if(!isHost()) slot = getInvertedSlot(slot);

            inventory.setItem(slot, is);
        }

        // Place Target Items
        for(Map.Entry<Integer, ItemStack> entry : trade.getItems(Trade.Type.TARGET).entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            if(isHost()) slot = getInvertedSlot(slot);

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
        return trade.getHost() == playerMenuUtility.getOwner();
    }
}
