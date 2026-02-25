package com.poopstuff;

import com.poopstuff.commands.GivePoopCommand;
import com.poopstuff.commands.PoopMenuCommand;
import com.poopstuff.listeners.CameraListener;
import com.poopstuff.listeners.EatablesListener;
import com.poopstuff.listeners.PlaceablePoopListener;
import com.poopstuff.listeners.PlayerPoopListener;
import com.poopstuff.listeners.RayGunListener;
import com.poopstuff.listeners.SlimyPoopListener;
import com.poopstuff.listeners.ThrowablePoopListener;
import com.poopstuff.recipes.RecipeManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PoopPlugin extends JavaPlugin {

    private static PoopPlugin instance;
    private RecipeManager recipeManager;

    @Override
    public void onEnable() {
        instance = this;

        // Register recipes
        recipeManager = new RecipeManager(this);
        recipeManager.registerAllRecipes();

        // Register commands
        PoopMenuCommand poopMenuCommand = new PoopMenuCommand();
        getCommand("poopmenu").setExecutor(poopMenuCommand);
        getCommand("givepoop").setExecutor(new GivePoopCommand());

        // Register listeners
        getServer().getPluginManager().registerEvents(poopMenuCommand, this); // Also a listener for GUI clicks
        getServer().getPluginManager().registerEvents(new ThrowablePoopListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceablePoopListener(), this);
        getServer().getPluginManager().registerEvents(new SlimyPoopListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPoopListener(), this);
        getServer().getPluginManager().registerEvents(new RayGunListener(), this);
        getServer().getPluginManager().registerEvents(new CameraListener(), this);
        getServer().getPluginManager().registerEvents(new EatablesListener(), this);

        getLogger().info("Zippo's Imagination has been enabled!");
        getLogger().info("Use /poopmenu to get items or craft them in survival!");
    }

    @Override
    public void onDisable() {
        // Unregister recipes
        if (recipeManager != null) {
            recipeManager.unregisterAllRecipes();
        }

        getLogger().info("Zippo's Imagination has been disabled!");
    }

    public static PoopPlugin getInstance() {
        return instance;
    }
}
