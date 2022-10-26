package de.davideinenkel.peachforestx;

import de.davideinenkel.peachforestx.commands.GetMenuCommand;
import de.davideinenkel.peachforestx.commands.OpenShopCommand;
import de.davideinenkel.peachforestx.listener.HotbarShopListener;
import de.davideinenkel.peachforestx.listener.JoinQuitListener;
import de.davideinenkel.peachforestx.listener.MenuListener;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class PeachForestX extends JavaPlugin {
    private static JavaPlugin instance = null;

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getCommand("shop").setExecutor(new OpenShopCommand());
        getCommand("setup").setExecutor(new GetMenuCommand());
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarShopListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Provide a player and return a menu system for that player
    //create one if they don't already have one
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p); //Return the object by using the provided player
        }
    }


    public static JavaPlugin getInstance() {
        return instance;
    }
}
