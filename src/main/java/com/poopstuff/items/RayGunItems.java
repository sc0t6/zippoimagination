package com.poopstuff.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Creates all Ray Gun items
 */
public class RayGunItems {

    /**
     * Creates the Shrink Ray
     * Makes players 0.5 size
     */
    public static ItemStack createShrinkRay() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§bShrink Ray");
            meta.setLore(Arrays.asList(
                "§7A mysterious device that",
                "§7shrinks anything it hits!",
                "",
                "§eRight-click to shrink yourself",
                "§eLeft-click entity to shrink target",
                "§7Target size: 0.5x"
            ));

            meta.setCustomModelData(2001);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Grow Ray
     * Makes players 1.5 size
     */
    public static ItemStack createGrowRay() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§aGrow Ray");
            meta.setLore(Arrays.asList(
                "§7A mysterious device that",
                "§7enlarges anything it hits!",
                "",
                "§eRight-click to grow yourself",
                "§eLeft-click entity to grow target",
                "§7Target size: 1.5x"
            ));

            meta.setCustomModelData(2002);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Normal Ray
     * Resets player size to 1.0
     */
    public static ItemStack createNormalRay() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§7Normal Ray");
            meta.setLore(Arrays.asList(
                "§7A mysterious device that",
                "§7restores normal size!",
                "",
                "§eRight-click to restore yourself",
                "§eLeft-click entity to restore target",
                "§7Target size: 1.0x (Normal)"
            ));

            meta.setCustomModelData(2003);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates the Ray Gun (weapon)
     * High damage weapon that requires ammo
     */
    public static ItemStack createRayGun() {
        ItemStack item = new ItemStack(Material.GOLDEN_HORSE_ARMOR);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§cRay Gun §7(Weapon)");
            meta.setLore(Arrays.asList(
                "§7A powerful energy weapon",
                "§7that deals incredible damage!",
                "",
                "§eRight-click to shoot",
                "§cRequires Ray Gun Ammo",
                "§7Damage: 15 ❤"
            ));

            meta.setCustomModelData(2004);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Creates Ray Gun Ammo
     * Crafted from: Nether Star + 2 Eye of Enders
     */
    public static ItemStack createRayGunAmmo() {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§dRay Gun Ammo");
            meta.setLore(Arrays.asList(
                "§7Powerful energy cells",
                "§7for the Ray Gun weapon",
                "",
                "§7Craft with: §bNether Star",
                "§7+ §a2x Eye of Enders"
            ));

            meta.setCustomModelData(2005);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Check if an item is a Shrink Ray
     */
    public static boolean isShrinkRay(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§bShrink Ray");
    }

    /**
     * Check if an item is a Grow Ray
     */
    public static boolean isGrowRay(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§aGrow Ray");
    }

    /**
     * Check if an item is a Normal Ray
     */
    public static boolean isNormalRay(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§7Normal Ray");
    }

    /**
     * Check if an item is a Ray Gun (weapon)
     */
    public static boolean isRayGun(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().contains("§cRay Gun");
    }

    /**
     * Check if an item is Ray Gun Ammo
     */
    public static boolean isRayGunAmmo(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("§dRay Gun Ammo");
    }
}
