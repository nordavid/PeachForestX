package de.davideinenkel.peachforestx.utility;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CustomHead {
    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4"); // We reuse the same "random" UUID all the time

    private static PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url); // The URL to the skin, for example: https://textures.minecraft.net/texture/18813764b2abc94ec3c3bc67b9147c21be850cdf996679703157f4555997ea63a
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        return profile;
    }

    public static ItemStack getPlayerHead(String url) {
        PlayerProfile profile = getProfile(url);
        //System.out.println(profile.getTextures().getSkin().toString());
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile); // Set the owning player of the head to the player profile
        head.setItemMeta(meta);
        return  head;
    }

    public static ItemStack getPlayerHead(String url, String displayName, String... lore) {
        PlayerProfile profile = getProfile(url);
        //System.out.println(profile.getTextures().getSkin().toString());
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile); // Set the owning player of the head to the player profile
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        head.setItemMeta(meta);
        return  head;
    }

    public static ItemStack getPlayerHead(Player player, String displayName, String... lore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        final SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setOwningPlayer(player);
        meta.setLore(Arrays.asList(lore));

        meta.getPersistentDataContainer().set(new NamespacedKey(PeachForestX.getInstance(), "uuid"), PersistentDataType.STRING, player.getUniqueId().toString());
        head.setItemMeta(meta);
        return head;
    }
}
