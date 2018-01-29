package com.infxnty.CommandTools.Commands;

import com.infxnty.CommandTools.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CTCommand implements CommandExecutor {

    private Main main; public CTCommand(Main main) { this.main = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String prefix = main.getConfig().getString("prefix");
            String reloadperm = main.getConfig().getString("reloadperm");
            String noperm = main.getConfig().getString("noperm");
            String helpperm = main.getConfig().getString("helpperm");
            String addperm = main.getConfig().getString("addblockedcmds");
            String reloaded = main.getConfig().getString("reloaded");
            if (cmd.getName().equalsIgnoreCase("commandtools")) {
                if (args.length == 0 || args[0].equalsIgnoreCase("help") && p.hasPermission(helpperm)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------&8[&3CommandTools&8]&m------------------&r"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b1. &3/ct help &8(&7Displays the help for the plugin&8)"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b2. &3/ct reload &8(&7Reloads the config file&8)"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b3. &3/ct add <command> &8(&7Block additional commands&8)"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Ideas for additional features? PM me on Spigot!"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m------------------------------------------------"));
                } else {
                    // Reload Command
                    if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r") && p.hasPermission(reloadperm)) {
                        main.reloadConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + reloaded));
                    } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r") && !p.hasPermission(reloadperm)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noperm));
                    }

                    // Add to list command
                    if (args[0].equalsIgnoreCase("add") && p.hasPermission(addperm)) {
                        if (args.length == 2) {
                            String added = args[1];
                            List<String> l = getList();
                            if (l.contains(added)) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You are already blocking that command!"));
                            } else {
                                l.add(added);
                                main.getConfig().set("blockedcmds", l);
                                main.saveConfig();
                            }
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Now blocking: " + added));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Invalid usage. Do /ct add <command>"));
                        }
                    }
                }
            }
        } else {
            System.out.println("Only players can use this command!");
        }
        return false;
    }

    public List<String> getList() {
        return main.getConfig().getStringList("blockedcmds");
    }
}
