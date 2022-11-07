package de.davideinenkel.peachforestx.commands;

import de.davideinenkel.peachforestx.PeachForestX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PeachForestX.reloadMainConfig();
        return true;
    }
}
