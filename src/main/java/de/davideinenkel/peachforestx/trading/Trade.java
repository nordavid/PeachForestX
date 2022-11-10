package de.davideinenkel.peachforestx.trading;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.menus.TradingMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Trade {
    public static HashMap<Player, Player> tradeRequests = new HashMap<>();

    public enum Type {
        HOST,
        TARGET
    }

    public enum TradeState {
        TRADING,
        LOCKED,
        READYTOTRADE
    }

    private Player host;
    private Player target;
    private TradingMenu hostMenu;
    private TradingMenu targetMenu;

    private TradeState hostTradeState = TradeState.TRADING;
    private TradeState targetTradeState = TradeState.TRADING;

    private HashMap<Integer, ItemStack> hostItems = new HashMap<>();
    private HashMap<Integer, ItemStack> targetItems = new HashMap<>();

    public Trade(Player host, Player target) {
        this.host = host;
        this.target = target;
        hostMenu = new TradingMenu(PeachForestX.getPlayerMenuUtility(host), this);
        targetMenu = new TradingMenu(PeachForestX.getPlayerMenuUtility(target), this);
        hostMenu.open();
        targetMenu.open();
    }

    public Player getHost() {
        return host;
    }

    public Player getTarget() {return target;}

    public void acceptTrade(Player player) {
        if(player == host) hostTradeState = TradeState.READYTOTRADE;
        else targetTradeState = TradeState.READYTOTRADE;

        if(hostTradeState == TradeState.READYTOTRADE && targetTradeState == TradeState.READYTOTRADE) executeTrade();
    }

    private void executeTrade() {
        host.closeInventory();
        target.closeInventory();

        for (ItemStack is : hostItems.values()) {
            target.getInventory().addItem(is);
        }

        for (ItemStack is : targetItems.values()) {
            host.getInventory().addItem(is);
        }
    }

    public void tradeDeclined() {
        host.closeInventory();
        target.closeInventory();

        for (ItemStack is : hostItems.values()) {
            host.getInventory().addItem(is);
        }

        for (ItemStack is : targetItems.values()) {
            target.getInventory().addItem(is);
        }
    }

    public void lockTrade(Player player) {
        if(player == host) hostTradeState = TradeState.LOCKED;
        else targetTradeState = TradeState.LOCKED;

        updateTradingMenus();
    }

    public Boolean haveBothLocked() {
        if(hostTradeState == TradeState.LOCKED && targetTradeState == TradeState.LOCKED) return true;
        else return false;
    }

    public TradeState getTradeStateForPlayer(Type type) {
        if(type == Type.HOST) return hostTradeState;
        else return targetTradeState;
    }

    public void addItem(Integer slot, ItemStack item, Type type) {
        Bukkit.getLogger().info("item added");
        if(type == Type.HOST) {
            if(!hostItems.containsKey(slot)) hostItems.put(slot, item);
            updateTradingMenus(Type.TARGET);
        }
        if(type == Type.TARGET) {
            if(!targetItems.containsKey(slot)) targetItems.put(slot, item);
            updateTradingMenus(Type.HOST);
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

    public void updateTradingMenus() {
        hostMenu.open();
        targetMenu.open();
    }

    public void updateTradingMenus(Type type) {
        if(type == Type.TARGET) targetMenu.open();
        if(type == Type.HOST) hostMenu.open();
    }
}
