package de.davideinenkel.peachforestx.utility;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerConfig {
    private static String subFolderName = "PlayerData";
    static File cfile;
    static FileConfiguration config;
    static File df = PeachForestX.getInstance().getDataFolder();
    static File folder = new File(df, subFolderName + File.separator);

    public static void create(Player p) {
        cfile = new File(df, subFolderName + File.separator + p.getUniqueId() + ".yml");
        if (!df.exists()) df.mkdir();
        if (!folder.exists()) folder.mkdir();
        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch(Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error creating " + cfile.getName() + "!");
                Bukkit.getLogger().info(e.getMessage());
            }
        }
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public static File getfolder() {
        return folder;
    }

    public static File getfile() {
        return cfile;
    }

    public static void load(Player p) {
        cfile = new File(df, subFolderName + File.separator + p.getUniqueId() + ".yml");
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(cfile);
        } catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error saving " + cfile.getName() + "!");
        }
    }
}