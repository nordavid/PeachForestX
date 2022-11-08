package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.Menu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.CustomHead;
import de.davideinenkel.peachforestx.utility.PlayerConfig;
import de.davideinenkel.peachforestx.utility.Rewards;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends Menu {
    Integer balance = 0;

    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        PlayerConfig.load(playerMenuUtility.getOwner());
        balance = PlayerConfig.get().getInt("balance");
        return "Guthaben: §l" + balance + "P";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClick() == ClickType.LEFT) {
            final Player p = (Player) e.getWhoClicked();

            if(e.getRawSlot() == 4) {
                if (Rewards.getIsRewardReady(p)) {
                    Rewards.claimReward(p);
                    p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 1);
                    Chat.sendMsgWithDefaultPrefix(p, "Reward erhalten");
                    super.open();
                }
                else {
                    Chat.sendMsgWithDefaultPrefix(p, "Reward nicht ready");
                }
            } else if (e.getRawSlot() == 8) {
                new ShopMenu(PeachForestX.getPlayerMenuUtility(p)).open();
            } else if (e.getRawSlot() == 0) {
                new TradingRequestMenu(PeachForestX.getPlayerMenuUtility(p)).open();
            }
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack peach = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/16b4d27bc1b466e3ab4123cbe241974a813573a7c36c5e5b8daf9c85a5ce0");
        ItemStack reward = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6");
        ItemStack shop = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/34ccb52750e97e830aebfa8a21d5da0d364d0fdad9fb0cc220fe2ca8411842c3");
        ItemStack trade = CustomHead.getPlayerHead("https://textures.minecraft.net/texture/ce1fac3d96346e622e890f76ec015a709b673422257b1442061a3aa325982411");

        peach = addMetaToItem(peach, ChatColor.GOLD + "" + ChatColor.BOLD + "Peaches", "§fDu Drecksau hast " + balance + " Peaches");
        reward = addMetaToItem(reward, ChatColor.GREEN + "" + ChatColor.BOLD + "Reward abholen", "§fHol Dir dein daily reward ab Du Huan" , "§d" + Rewards.getRewardString(playerMenuUtility.getOwner()));
        shop = addMetaToItem(shop,ChatColor.RED + "" + ChatColor.BOLD + "Shop", "§fHol Dir doch was feines lul");
        trade = addMetaToItem(trade, ChatColor.GOLD + "" + ChatColor.BOLD + "Trading", "§fTrade with other players");

        //inventory.setItem(0, peach);
        inventory.setItem(4, reward);
        inventory.setItem(8, shop);
        inventory.setItem(0, trade);

        inventory.setItem(1, FILLER_GLASS);
        inventory.setItem(2, FILLER_GLASS);
        inventory.setItem(3, FILLER_GLASS);
        inventory.setItem(5, FILLER_GLASS);
        inventory.setItem(6, FILLER_GLASS);
        inventory.setItem(7, FILLER_GLASS);
    }
}
