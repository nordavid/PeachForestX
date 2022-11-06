package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.PaginatedMenu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.utility.Chat;
import de.davideinenkel.peachforestx.utility.PlayerConfig;
import de.davideinenkel.peachforestx.utility.TargetTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class PlayerCompassMenu extends PaginatedMenu {

    public PlayerCompassMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Magic Compass";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players = new ArrayList<>(getServer().getOnlinePlayers());
        players.remove(playerMenuUtility.getOwner());

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")){
                if (page == 0){
                    Chat.sendMsgWithDefaultPrefix(p, "Du bist breits auf der ersten Seite", "§7");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")){
                if (!((index + 1) >= players.size())){
                    page = page + 1;
                    super.open();
                }else{
                    Chat.sendMsgWithDefaultPrefix(p, "Du bist auf der letzten Seite", "§7");
                }
            }
            // Actual player klicked
            else {
                Player target = Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PeachForestX.getInstance(), "uuid"), PersistentDataType.STRING)));
                if(target != null) {
                    TargetTracker.targets.put(p, target);
                    Chat.sendMsgWithDefaultPrefix(p, "Du trackst nun " + target.getDisplayName());
                    p.closeInventory();
                }
            }
        }
        else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            //close inventory
            p.closeInventory();

        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        ArrayList<Player> players = new ArrayList<>(getServer().getOnlinePlayers());
        players.remove(playerMenuUtility.getOwner());

        if(players != null && !players.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if (players.get(index) != null){
                    ///////////////////////////

                    inventory.addItem(getPlayerHead(players.get(index)));

                    ////////////////////////
                }
            }
        }
        ////////////////////////
    }

    public static ItemStack getPlayerHead(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        final SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName("§d" + player.getDisplayName());
        meta.setOwningPlayer(player);
        //String lore;

        List<String> lore = new ArrayList<>();
        //List<String> lore = Arrays.asList(ChatColor.GRAY + "Menü mit Rechtsklick öffnen");
        lore.add(ChatColor.GRAY + "Klicken um Spieler als Ziel zu wählen");
        //lore = ChatColor.GRAY + "Menü mit Rechtsklick öffnen";
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(new NamespacedKey(PeachForestX.getInstance(), "uuid"), PersistentDataType.STRING, player.getUniqueId().toString());
        head.setItemMeta(meta);
        return head;
    }

    public static Integer getInventorySlots() {
        Integer playerCount = Bukkit.getServer().getOnlinePlayers().size();

        if(playerCount <= 9) return 9 + 18;
        else if (playerCount <= 18) return  18 + 18;
        else if (playerCount <= 27) return  27 + 18;
        else return 54;
    }
}
