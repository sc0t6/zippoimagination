package com.poopstuff;

import com.poopstuff.commands.GivePoopCommand;
import com.poopstuff.commands.PoopMenuCommand;
import com.poopstuff.listeners.PlaceablePoopListener;
import com.poopstuff.listeners.SlimyPoopListener;
import com.poopstuff.listeners.ThrowablePoopListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PoopPlugin extends JavaPlugin {

    private static PoopPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register commands
        PoopMenuCommand poopMenuCommand = new PoopMenuCommand();
        getCommand("poopmenu").setExecutor(poopMenuCommand);
        getCommand("givepoop").setExecutor(new GivePoopCommand());

        // Register listeners
        getServer().getPluginManager().registerEvents(poopMenuCommand, this); // Also a listener for GUI clicks
        getServer().getPluginManager().registerEvents(new ThrowablePoopListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceablePoopListener(), this);
        getServer().getPluginManager().registerEvents(new SlimyPoopListener(), this);

        getLogger().info("The Poop Mod has been enabled! Type /poopmenu to get started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("The Poop Mod has been disabled!");
    }

    public static PoopPlugin getInstance() {
        return instance;
    }
}
