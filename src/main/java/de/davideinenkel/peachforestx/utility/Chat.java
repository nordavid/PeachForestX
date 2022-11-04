package de.davideinenkel.peachforestx.utility;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.entity.Player;

public class Chat {
    public static void sendMsgWithPrefix(Player player,String prefix, String msg) {
        player.sendMessage(prefix + msg);
    }

    public static void sendMsgWithDefaultPrefix(Player player, String msg) {
            player.sendMessage(PeachForestX.getMainConfig().getString("Prefix") + " §r" + msg);
    }

    public static void sendMsgWithDefaultPrefix(Player player, String msg, String msgColor) {
        player.sendMessage(PeachForestX.getMainConfig().getString("Prefix") + " §r" + msgColor + msg);
    }

    public static void sendMsgWithDefaultPrefix(Player player, String msg, Boolean warning) {
        if(warning) {
            player.sendMessage(PeachForestX.getMainConfig().getString("Prefix") + " §c" + msg);

        }
        else {
            player.sendMessage(PeachForestX.getMainConfig().getString("Prefix") + " §r" + msg);

        }
    }
}
