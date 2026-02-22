package com.poopstuff.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Creates Camera-related items
 */
public class CameraItems {

    /**
     * Creates the Camera item
     * Takes screenshots when used
     */
    public static ItemStack createCamera() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§eCamera");
            meta.setLore(Arrays.asList(
                "§7A handheld camera for capturing",
                "§7the perfect moment!",
                "",
                "§eRight-click to take a screenshot",
                "§7Screenshots save to your",
                "§7.minecraft/screenshots folder"
            ));

            meta.setCustomModelData(3001);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Security Camera block item
     * Can be placed and viewed from Tablet
     */
    public static ItemStack createSecurityCamera() {
        ItemStack item = new ItemStack(Material.OBSERVER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§cSecurity Camera");
            meta.setLore(Arrays.asList(
                "§7A surveillance camera that",
                "§7monitors an area 24/7!",
                "",
                "§ePlace to install camera",
                "§eUse Tablet to view feed",
                "§7Max: 7 cameras per player"
            ));

            meta.setCustomModelData(3002);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Tablet item
     * Views screenshots and security camera feeds
     */
    public static ItemStack createTablet() {
        ItemStack item = new ItemStack(Material.MAP);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§9Tablet");
            meta.setLore(Arrays.asList(
                "§7A high-tech device for viewing",
                "§7your cameras and screenshots!",
                "",
                "§eRight-click to open menu",
                "§7- View Security Cameras",
                "§7- Teleport to camera locations",
                "§7- Play sounds at cameras"
            ));

            meta.setCustomModelData(3003);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Check if an item is a Camera
     */
    public static boolean isCamera(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§eCamera");
    }

    /**
     * Check if an item is a Security Camera
     */
    public static boolean isSecurityCamera(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§cSecurity Camera");
    }

    /**
     * Check if an item is a Tablet
     */
    public static boolean isTablet(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§9Tablet");
    }
}
