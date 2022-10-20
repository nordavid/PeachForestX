package de.davideinenkel.pfshop;

import de.davideinenkel.pfshop.commands.OpenShopCommand;
import de.davideinenkel.pfshop.listener.JoinQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pfshop extends JavaPlugin {
    public static JavaPlugin instance = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getCommand("shop").setExecutor(new OpenShopCommand());
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
