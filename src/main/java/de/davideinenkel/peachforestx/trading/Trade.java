package de.davideinenkel.peachforestx.trading;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.menus.TradingMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Trade {
    public static HashMap<Player, Player> tradeRequests = new HashMap<>();

    public enum TradingMenus {
        HOST,
        TARGET
    }

    Player host;
    TradingMenu hostMenu;
    Player target;
    TradingMenu targetMenu;

    ItemStack[] hostItems;
    ItemStack[] targetItems;

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

    public void updateTradingMenus(TradingMenus tradingMenus) {
        if(tradingMenus == TradingMenus.TARGET) targetMenu.open();
        if(tradingMenus == TradingMenus.HOST) hostMenu.open();
    }

    public Player getHost() {
        return host;
    }
}
