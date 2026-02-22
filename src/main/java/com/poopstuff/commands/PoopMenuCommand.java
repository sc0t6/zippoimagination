package com.poopstuff.commands;

import com.poopstuff.items.CameraItems;
import com.poopstuff.items.PoopItems;
import com.poopstuff.items.RayGunItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Opens a GUI menu showing all available poop items
 * Players can click to receive items (acts as a creative tab alternative)
 */
public class PoopMenuCommand implements CommandExecutor, Listener {

    private static final String MENU_TITLE = "§6§lZippo's Imagination Menu";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("poopmod.menu")) {
            player.sendMessage("§cYou don't have permission to use the Poop Menu!");
            return true;
        }

        openPoopMenu(player);
        return true;
    }

    /**
     * Opens the Poop Menu GUI for the player
     */
    private void openPoopMenu(Player player) {
        // Create a 6x9 (54 slot) inventory
        Inventory menu = Bukkit.createInventory(null, 54, MENU_TITLE);

        // Add category headers
        menu.setItem(4, createCategoryItem(Material.BROWN_MUSHROOM, "§6§lPoop Category"));
        menu.setItem(13, createCategoryItem(Material.BLAZE_ROD, "§b§lRay Guns"));
        menu.setItem(22, createCategoryItem(Material.TRIPWIRE_HOOK, "§e§lCameras"));

        // Poop items (Row 2)
        menu.setItem(10, PoopItems.createThrowablePoop());
        menu.setItem(11, PoopItems.createPlaceablePoop());
        menu.setItem(12, PoopItems.createSlimyPoop());

        // Ray Guns (Row 3)
        menu.setItem(19, RayGunItems.createShrinkRay());
        menu.setItem(20, RayGunItems.createGrowRay());
        menu.setItem(21, RayGunItems.createNormalRay());
        menu.setItem(23, RayGunItems.createRayGun());
        menu.setItem(24, RayGunItems.createRayGunAmmo());

        // Cameras (Row 4)
        menu.setItem(28, CameraItems.createCamera());
        menu.setItem(29, CameraItems.createSecurityCamera());
        menu.setItem(30, CameraItems.createTablet());

        // Info item
        menu.setItem(49, createInfoItem());

        player.openInventory(menu);
        player.sendMessage("§6Welcome to Zippo's Imagination! §eClick items to receive them.");
    }

    /**
     * Creates a category header item
     */
    private ItemStack createCategoryItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates an info item explaining the menu
     */
    private ItemStack createInfoItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e§lHow to Use");
            meta.setLore(Arrays.asList(
                "",
                "§7Click on any item above",
                "§7to receive it in your inventory!",
                "",
                "§6§lPoop Category:",
                "§7- Throwable, Placeable, Slimy Poop",
                "§7- Hold Shift with empty hand (3s) to poop",
                "",
                "§b§lRay Guns:",
                "§7- Shrink/Grow/Normal Ray (change size)",
                "§7- Ray Gun weapon (needs ammo)",
                "",
                "§e§lCameras:",
                "§7- Camera (take screenshots)",
                "§7- Security Cameras (max 7)",
                "§7- Tablet (view cameras)",
                "",
                "§a§lEatables:",
                "§7Right-click: Soil, Snow, Sand, Dust,",
                "§7Flowers, and Leaves to eat them!"
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Handle inventory click events in the menu
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if this is our menu
        if (!event.getView().getTitle().equals(MENU_TITLE)) return;

        // Cancel the event to prevent taking items
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Give the player the clicked item
        // Poop Items
        if (PoopItems.isThrowablePoop(clickedItem)) {
            player.getInventory().addItem(PoopItems.createThrowablePoop());
            player.sendMessage("§6You received: §eThrowable Poop!");
        } else if (PoopItems.isPlaceablePoop(clickedItem)) {
            player.getInventory().addItem(PoopItems.createPlaceablePoop());
            player.sendMessage("§6You received: §ePoop Block!");
        } else if (PoopItems.isSlimyPoop(clickedItem)) {
            player.getInventory().addItem(PoopItems.createSlimyPoop());
            player.sendMessage("§2You received: §aSlimy Poop!");
        }
        // Ray Guns
        else if (RayGunItems.isShrinkRay(clickedItem)) {
            player.getInventory().addItem(RayGunItems.createShrinkRay());
            player.sendMessage("§bYou received: §aShrink Ray!");
        } else if (RayGunItems.isGrowRay(clickedItem)) {
            player.getInventory().addItem(RayGunItems.createGrowRay());
            player.sendMessage("§aYou received: §eGrow Ray!");
        } else if (RayGunItems.isNormalRay(clickedItem)) {
            player.getInventory().addItem(RayGunItems.createNormalRay());
            player.sendMessage("§7You received: §eNormal Ray!");
        } else if (RayGunItems.isRayGun(clickedItem)) {
            player.getInventory().addItem(RayGunItems.createRayGun());
            player.sendMessage("§cYou received: §eRay Gun (Weapon)!");
        } else if (RayGunItems.isRayGunAmmo(clickedItem)) {
            player.getInventory().addItem(RayGunItems.createRayGunAmmo());
            player.sendMessage("§dYou received: §eRay Gun Ammo!");
        }
        // Cameras
        else if (CameraItems.isCamera(clickedItem)) {
            player.getInventory().addItem(CameraItems.createCamera());
            player.sendMessage("§eYou received: §aCamera!");
        } else if (CameraItems.isSecurityCamera(clickedItem)) {
            player.getInventory().addItem(CameraItems.createSecurityCamera());
            player.sendMessage("§cYou received: §eSecurity Camera!");
        } else if (CameraItems.isTablet(clickedItem)) {
            player.getInventory().addItem(CameraItems.createTablet());
            player.sendMessage("§9You received: §eTablet!");
        }
    }
}
