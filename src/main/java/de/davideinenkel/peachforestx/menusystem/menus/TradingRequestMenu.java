package de.davideinenkel.peachforestx.menusystem.menus;

import de.davideinenkel.peachforestx.PeachForestX;
import de.davideinenkel.peachforestx.menusystem.PaginatedMenu;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.trading.Trade;
import de.davideinenkel.peachforestx.utility.Chat;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class TradingRequestMenu extends PaginatedMenu {
    public TradingRequestMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public Boolean isEventSelfManaged() {
        return false;
    }

    @Override
    public String getMenuName() {
        return "Spieler zum Traden wählen";
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
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Vorherige Seite")){
                if (page == 0){
                    Chat.sendMsgWithDefaultPrefix(p, "Du bist auf der ersten Seite", "§7");
                }else{
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Nächste Seite")){
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

                    if(Trade.tradeRequests.containsKey(p)) return;
                    Trade.tradeRequests.put(p, target);
                    /*BaseComponent[] targetMessage =
                            new ComponentBuilder(p.getDisplayName()).color(ChatColor.GOLD)
                                    .append(" will mit dir Traden ").color(ChatColor.WHITE)
                                    .append("Annehmen ").color(ChatColor.GREEN)
                                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT))
                                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptTradeRequest " + p.getDisplayName()))
                                    .append("or ")
                                    .append("Ablehnen").color(ChatColor.RED)
                                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT))
                                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/declineTradeRequest " + p.getDisplayName()))
                                    .create();*/

                    TextComponent accept = new TextComponent("§a[Annehmen]");
                    TextComponent decline = new TextComponent("§c[Ablehnen]");

                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accepttraderequest " + p.getDisplayName()));
                    accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Klicke um Anfrage anzunehmen!")));

                    decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/declinetraderequest " + p.getDisplayName()));
                    decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Klicke um Anfrage abzulehnen!")));

                    TextComponent msg = new TextComponent(p.getDisplayName() + " will mit dir Traden. ");
                    msg.addExtra(accept);
                    msg.addExtra(" oder ");
                    msg.addExtra(decline);

                    target.spigot().sendMessage(msg);
                    Chat.sendMsgWithDefaultPrefix(p, "§aTrading Anfrage abgeschickt. Warte auf Antwort...");

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
        lore.add(org.bukkit.ChatColor.GRAY + "Klicken um Trading-Anfrage zu schicken");
        //lore = ChatColor.GRAY + "Menü mit Rechtsklick öffnen";
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(new NamespacedKey(PeachForestX.getInstance(), "uuid"), PersistentDataType.STRING, player.getUniqueId().toString());
        head.setItemMeta(meta);
        return head;
    }
}
