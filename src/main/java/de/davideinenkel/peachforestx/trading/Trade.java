package de.davideinenkel.peachforestx.trading;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.menus.TradingMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Trade {
    public static HashMap<Player, Player> tradeRequests = new HashMap<>();

    public enum Type {
        HOST,
        TARGET
    }

    public enum TradeState {
        TRADING,
        LOCKED,
        WAITING,
        READYTOTRADE
    }

    private Player host;
    private Player target;
    private TradingMenu hostMenu;
    private TradingMenu targetMenu;

    private Boolean hasHostLocked = false;
    private Boolean hasTargetLocked = false;

    private HashMap<Integer, ItemStack> hostItems = new HashMap<>();
    private HashMap<Integer, ItemStack> targetItems = new HashMap<>();

    private TradeState tradeState = TradeState.TRADING;

    public Trade(Player host, Player target) {
        this.host = host;
        this.target = target;
        hostMenu = new TradingMenu(PeachForestX.getPlayerMenuUtility(host), this);
        targetMenu = new TradingMenu(PeachForestX.getPlayerMenuUtility(target), this);
        hostMenu.open();
        targetMenu.open();
    }

    public void updateTradingMenus() {
        hostMenu.open();
        targetMenu.open();
    }

    public void updateTradingMenus(Type type) {
        if(type == Type.TARGET) targetMenu.open();
        if(type == Type.HOST) hostMenu.open();
    }

    public void tradeAccepted() {
        host.closeInventory();
        target.closeInventory();

        // Place Host Items
        for(Map.Entry<Integer, ItemStack> entry : hostItems.entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            target.getInventory().addItem(is);
        }

        // Place Target Items
        for(Map.Entry<Integer, ItemStack> entry : targetItems.entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            host.getInventory().addItem(is);
        }
    }

    public void tradeDeclined() {
        host.closeInventory();
        target.closeInventory();

        // Place Host Items
        for(Map.Entry<Integer, ItemStack> entry : hostItems.entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            host.getInventory().addItem(is);
        }

        // Place Target Items
        for(Map.Entry<Integer, ItemStack> entry : targetItems.entrySet()) {
            Integer slot = entry.getKey();
            ItemStack is = entry.getValue();
            target.getInventory().addItem(is);
        }
    }

    public Player getHost() {
        return host;
    }

    public Player getTarget() {return target;}

    public void lockTrade(Player player) {
        if(player == host) hasHostLocked = true;
        else hasTargetLocked = true;
        checkIfTradeIsReady();
        updateTradingMenus();
    }

    private void checkIfTradeIsReady() {
        if(hasHostLocked && hasTargetLocked) {
            tradeState = TradeState.READYTOTRADE;
            updateTradingMenus();
        }
    }

    public Boolean getLockStateForPlayer(Player player) {
        if(player == host) return hasHostLocked;
        else return hasTargetLocked;
    }

    public TradeState getTradeState() {
        return tradeState;
    }

    public void addItem(Integer slot, ItemStack item, Type type) {
        Bukkit.getLogger().info("item added");
        if(type == Type.HOST) {
            if(!hostItems.containsKey(slot)) hostItems.put(slot, item);
        }
        if(type == Type.TARGET) {
            if(!targetItems.containsKey(slot)) targetItems.put(slot, item);
        }
    }

    public void removeItem(Integer slot, ItemStack item, Type type) {
        if(type == Type.HOST) {
            hostItems.remove(slot);
        }
        if(type == Type.TARGET) {
            targetItems.remove(slot);
        }
    }

    public HashMap<Integer, ItemStack> getItems(Type type) {
        if(type == Type.HOST) return hostItems;
        if(type == Type.TARGET) return targetItems;
        return null;
    }
}
