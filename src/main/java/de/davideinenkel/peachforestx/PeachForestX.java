package de.davideinenkel.peachforestx;

import de.davideinenkel.peachforestx.commands.*;
import de.davideinenkel.peachforestx.listener.DeathRespawnListener;
import de.davideinenkel.peachforestx.listener.HotbarShopListener;
import de.davideinenkel.peachforestx.listener.JoinQuitListener;
import de.davideinenkel.peachforestx.listener.MenuListener;
import de.davideinenkel.peachforestx.menusystem.PlayerMenuUtility;
import de.davideinenkel.peachforestx.utility.Rewards;
import de.davideinenkel.peachforestx.utility.TargetTracker;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class PeachForestX extends JavaPlugin {
    public static HashMap<Location , String> deathChests = new HashMap<>();

    //https://blog.jeff-media.com/persistent-data-container-the-better-alternative-to-nbt-tags/
    public static FileConfiguration config;
    private static JavaPlugin instance = null;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();
        config = getConfig();

        getCommand("pfreload").setExecutor(new ReloadConfigCommand());
        getCommand("test").setExecutor(new TestCommand());
        getCommand("shop").setExecutor(new OpenShopCommand());
        getCommand("setup").setExecutor(new GetMenuCommand());
        getCommand("balance").setExecutor(new ManipulateBalanceCommand());
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new DeathRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new HotbarShopListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        Rewards.runRewardScheduler();
        TargetTracker.runTargetScheduler();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Map.Entry<Location, String> entry : deathChests.entrySet()) {
            Location loc = entry.getKey();
            String id = entry.getValue();

            Bukkit.getServer().getWorlds().get(0).getBlockAt(loc).setType(Material.AIR);
            DHAPI.removeHologram(id);
        }
        deathChests.clear();
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

    public static void reloadMainConfig() {
        /*instance.saveConfig();*/
        instance.reloadConfig();
        config = instance.getConfig();
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static FileConfiguration getMainConfig() {
        return config;
    }
}
