package com.infxnty.CommandTools;

import com.infxnty.CommandTools.Commands.CTCommand;
import com.infxnty.CommandTools.Listeners.BlockedCommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.PrintStream;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        // Printing to console on start
        PrintStream sysout = System.out;
        sysout.println("========================================");
        sysout.println("Name: CommandTools");
        sysout.println("Version: 1.0");
        sysout.println("Author: Infxnty");
        sysout.println("Status: Enabled");
        sysout.println("========================================");

        // Registering Events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockedCommands(this), this);

        // Registering Commands
        getCommand("commandtools").setExecutor(new CTCommand(this));

        // Misc
        loadConfig();

    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

}
