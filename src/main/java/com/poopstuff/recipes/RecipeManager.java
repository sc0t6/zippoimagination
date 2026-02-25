package com.poopstuff.recipes;

import com.poopstuff.PoopPlugin;
import com.poopstuff.items.CameraItems;
import com.poopstuff.items.PoopItems;
import com.poopstuff.items.RayGunItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 * Manages crafting recipes for all custom items
 * Makes items craftable and discoverable in the recipe book
 */
public class RecipeManager {

    private final PoopPlugin plugin;

    public RecipeManager(PoopPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register all crafting recipes
     */
    public void registerAllRecipes() {
        registerPoopRecipes();
        registerRayGunRecipes();
        registerCameraRecipes();

        plugin.getLogger().info("Registered " + plugin.getServer().getRecipesFor(PoopItems.createThrowablePoop()).size() + " custom recipes!");
    }

    /**
     * Register poop-related recipes
     */
    private void registerPoopRecipes() {
        // Throwable Poop: 4 dirt + 1 snowball = 8 throwable poop
        NamespacedKey throwableKey = new NamespacedKey(plugin, "throwable_poop");
        ShapedRecipe throwableRecipe = new ShapedRecipe(throwableKey, PoopItems.createThrowablePoop());
        throwableRecipe.shape("DDD", "DSB", "DDD");
        throwableRecipe.setIngredient('D', Material.DIRT);
        throwableRecipe.setIngredient('S', Material.SNOWBALL);
        throwableRecipe.setIngredient('B', Material.BROWN_DYE);
        plugin.getServer().addRecipe(throwableRecipe);

        // Placeable Poop: 4 brown mushroom + 4 dirt + 1 brown dye = 4 poop blocks
        NamespacedKey placeableKey = new NamespacedKey(plugin, "placeable_poop");
        ShapedRecipe placeableRecipe = new ShapedRecipe(placeableKey, PoopItems.createPlaceablePoop());
        placeableRecipe.shape("MBM", "DDD", "MBM");
        placeableRecipe.setIngredient('M', Material.BROWN_MUSHROOM);
        placeableRecipe.setIngredient('B', Material.BROWN_DYE);
        placeableRecipe.setIngredient('D', Material.DIRT);
        plugin.getServer().addRecipe(placeableRecipe);

        // Slimy Poop: 1 honey bottle + 4 slime balls + 1 brown dye = 1 slimy poop
        NamespacedKey slimyKey = new NamespacedKey(plugin, "slimy_poop");
        ShapedRecipe slimyRecipe = new ShapedRecipe(slimyKey, PoopItems.createSlimyPoop());
        slimyRecipe.shape(" S ", "SHS", " B ");
        slimyRecipe.setIngredient('S', Material.SLIME_BALL);
        slimyRecipe.setIngredient('H', Material.HONEY_BOTTLE);
        slimyRecipe.setIngredient('B', Material.BROWN_DYE);
        plugin.getServer().addRecipe(slimyRecipe);
    }

    /**
     * Register ray gun recipes
     */
    private void registerRayGunRecipes() {
        // Shrink Ray: 2 blaze rods + 1 light blue dye + 2 redstone + 2 diamonds
        NamespacedKey shrinkKey = new NamespacedKey(plugin, "shrink_ray");
        ShapedRecipe shrinkRecipe = new ShapedRecipe(shrinkKey, RayGunItems.createShrinkRay());
        shrinkRecipe.shape("DBD", " R ", " B ");
        shrinkRecipe.setIngredient('D', Material.DIAMOND);
        shrinkRecipe.setIngredient('B', Material.BLAZE_ROD);
        shrinkRecipe.setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(shrinkRecipe);

        // Grow Ray: 2 blaze rods + 1 lime dye + 2 redstone + 2 emeralds
        NamespacedKey growKey = new NamespacedKey(plugin, "grow_ray");
        ShapedRecipe growRecipe = new ShapedRecipe(growKey, RayGunItems.createGrowRay());
        growRecipe.shape("EBE", " R ", " B ");
        growRecipe.setIngredient('E', Material.EMERALD);
        growRecipe.setIngredient('B', Material.BLAZE_ROD);
        growRecipe.setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(growRecipe);

        // Normal Ray: 2 blaze rods + 1 gray dye + 2 redstone + 2 iron ingots
        NamespacedKey normalKey = new NamespacedKey(plugin, "normal_ray");
        ShapedRecipe normalRecipe = new ShapedRecipe(normalKey, RayGunItems.createNormalRay());
        normalRecipe.shape("IBI", " R ", " B ");
        normalRecipe.setIngredient('I', Material.IRON_INGOT);
        normalRecipe.setIngredient('B', Material.BLAZE_ROD);
        normalRecipe.setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(normalRecipe);

        // Ray Gun Ammo: 1 nether star + 2 eye of enders = 16 ammo
        NamespacedKey ammoKey = new NamespacedKey(plugin, "ray_gun_ammo");
        ShapedRecipe ammoRecipe = new ShapedRecipe(ammoKey, RayGunItems.createRayGunAmmo());
        ammoRecipe.shape(" E ", "ENE", " E ");
        ammoRecipe.setIngredient('E', Material.ENDER_EYE);
        ammoRecipe.setIngredient('N', Material.NETHER_STAR);
        ItemStack ammoStack = RayGunItems.createRayGunAmmo();
        ammoStack.setAmount(16);
        ammoRecipe.setResult(ammoStack);
        plugin.getServer().addRecipe(ammoRecipe);

        // Ray Gun: 1 golden horse armor + 4 gold ingots + 2 redstone blocks + 2 diamonds
        NamespacedKey rayGunKey = new NamespacedKey(plugin, "ray_gun");
        ShapedRecipe rayGunRecipe = new ShapedRecipe(rayGunKey, RayGunItems.createRayGun());
        rayGunRecipe.shape("DGD", "GHG", " R ");
        rayGunRecipe.setIngredient('D', Material.DIAMOND);
        rayGunRecipe.setIngredient('G', Material.GOLD_INGOT);
        rayGunRecipe.setIngredient('H', Material.GOLDEN_HORSE_ARMOR);
        rayGunRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
        plugin.getServer().addRecipe(rayGunRecipe);
    }

    /**
     * Register camera recipes
     */
    private void registerCameraRecipes() {
        // Camera: 1 tripwire hook + 4 iron ingots + 2 glass panes + 1 redstone
        NamespacedKey cameraKey = new NamespacedKey(plugin, "camera");
        ShapedRecipe cameraRecipe = new ShapedRecipe(cameraKey, CameraItems.createCamera());
        cameraRecipe.shape("IGI", "ITI", " R ");
        cameraRecipe.setIngredient('I', Material.IRON_INGOT);
        cameraRecipe.setIngredient('G', Material.GLASS_PANE);
        cameraRecipe.setIngredient('T', Material.TRIPWIRE_HOOK);
        cameraRecipe.setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(cameraRecipe);

        // Security Camera: 1 observer + 4 iron ingots + 2 redstone + 1 ender eye
        NamespacedKey securityKey = new NamespacedKey(plugin, "security_camera");
        ShapedRecipe securityRecipe = new ShapedRecipe(securityKey, CameraItems.createSecurityCamera());
        securityRecipe.shape("IRI", "IOI", " E ");
        securityRecipe.setIngredient('I', Material.IRON_INGOT);
        securityRecipe.setIngredient('R', Material.REDSTONE);
        securityRecipe.setIngredient('O', Material.OBSERVER);
        securityRecipe.setIngredient('E', Material.ENDER_EYE);
        plugin.getServer().addRecipe(securityRecipe);

        // Tablet: 1 map + 4 iron ingots + 2 glass panes + 1 redstone + 1 compass
        NamespacedKey tabletKey = new NamespacedKey(plugin, "tablet");
        ShapedRecipe tabletRecipe = new ShapedRecipe(tabletKey, CameraItems.createTablet());
        tabletRecipe.shape("GIG", "IMI", "CRC");
        tabletRecipe.setIngredient('G', Material.GLASS_PANE);
        tabletRecipe.setIngredient('I', Material.IRON_INGOT);
        tabletRecipe.setIngredient('M', Material.MAP);
        tabletRecipe.setIngredient('C', Material.COMPASS);
        tabletRecipe.setIngredient('R', Material.REDSTONE);
        plugin.getServer().addRecipe(tabletRecipe);
    }

    /**
     * Unregister all recipes (for reload)
     */
    public void unregisterAllRecipes() {
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "throwable_poop"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "placeable_poop"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "slimy_poop"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "shrink_ray"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "grow_ray"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "normal_ray"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "ray_gun_ammo"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "ray_gun"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "camera"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "security_camera"));
        plugin.getServer().removeRecipe(new NamespacedKey(plugin, "tablet"));
    }
}
