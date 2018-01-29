package com.infxnty.CommandTools.Listeners;

import com.infxnty.CommandTools.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class BlockedCommands implements Listener {

    private Main main; public BlockedCommands(Main main) { this.main = main; }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String prefix = main.getConfig().getString("prefix");
        String noperm = main.getConfig().getString("noperm");
        String bypassperm = main.getConfig().getString("bypassperm");
        String unknown = main.getConfig().getString("unknown");
        List<String> l = main.getConfig().getStringList("blockedcmds");

        // Blocking Commands
        for (String i : l) {
            if (e.getMessage().toLowerCase().contains(i) && !p.hasPermission(bypassperm)) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noperm));
            }
        }
        // Custom Unknown Command
        if ((!e.isCancelled())) {
            String command = e.getMessage().split(" ")[0];
            org.bukkit.help.HelpTopic htopic = Bukkit.getServer().getHelpMap().getHelpTopic(command);
            if (htopic == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + unknown));
                e.setCancelled(true);
            }
        }
    }
}
