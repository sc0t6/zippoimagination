package com.poopstuff.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PoopItems {

    // Item identifiers stored in persistent data container
    public static final String THROWABLE_POOP_KEY = "throwable_poop";
    public static final String PLACEABLE_POOP_KEY = "placeable_poop";
    public static final String SLIMY_POOP_KEY = "slimy_poop";

    /**
     * Creates the Throwable Poop item
     * Base: Snowball (throwable)
     * Effect: Inflicts Nausea on hit
     */
    public static ItemStack createThrowablePoop() {
        ItemStack item = new ItemStack(Material.SNOWBALL);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§6Throwable Poop");
            meta.setLore(Arrays.asList(
                "§7A disgusting projectile",
                "§7that makes targets feel sick!",
                "",
                "§eThrow at enemies to inflict Nausea"
            ));

            // Add custom model data for resource pack support
            meta.setCustomModelData(1001);

            // Add enchant glow
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Placeable Poop block item
     * Base: Brown Mushroom Block
     * Effect: Slows down and inflicts Nausea for 5 seconds when walked on
     */
    public static ItemStack createPlaceablePoop() {
        ItemStack item = new ItemStack(Material.BROWN_MUSHROOM_BLOCK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§6Poop Block");
            meta.setLore(Arrays.asList(
                "§7A smelly block that makes",
                "§7anyone who steps on it feel terrible!",
                "",
                "§ePlace on ground to create a hazard",
                "§eEffects: Slowness + Nausea (5s)"
            ));

            // Add custom model data for resource pack support
            meta.setCustomModelData(1002);

            // Add enchant glow
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Slimy Poop item
     * Base: Honey Bottle (drinkable)
     * Effect: Inflicts Poison and Nausea when consumed
     */
    public static ItemStack createSlimyPoop() {
        ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§2Slimy Poop");
            meta.setLore(Arrays.asList(
                "§7A disturbing drinkable concoction",
                "§7that shouldn't be consumed!",
                "",
                "§cDrink to receive Poison + Nausea",
                "§7(Why would you do this?)"
            ));

            // Add custom model data for resource pack support
            meta.setCustomModelData(1003);

            // Add enchant glow
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Check if an item is a Throwable Poop
     */
    public static boolean isThrowablePoop(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§6Throwable Poop");
    }

    /**
     * Check if an item is a Placeable Poop
     */
    public static boolean isPlaceablePoop(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§6Poop Block");
    }

    /**
     * Check if an item is Slimy Poop
     */
    public static boolean isSlimyPoop(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§2Slimy Poop");
    }

    /**
     * Get all poop items as a list
     */
    public static List<ItemStack> getAllPoopItems() {
        return Arrays.asList(
            createThrowablePoop(),
            createPlaceablePoop(),
            createSlimyPoop()
        );
    }
}
