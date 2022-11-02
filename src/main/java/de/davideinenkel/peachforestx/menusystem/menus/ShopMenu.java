package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.menusystem.PaginatedMenu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopMenu extends PaginatedMenu {

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Shop";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {

    }
}
