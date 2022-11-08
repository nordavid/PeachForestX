package de.davideinenkel.peachforestx.listener;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onMsg(AsyncPlayerChatEvent e) {
        e.setFormat(PeachForestX.getMainConfig().getString("ChatFormat"));
    }
}
