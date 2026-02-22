package com.poopstuff.listeners;

import com.poopstuff.PoopPlugin;
import com.poopstuff.items.CameraItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Handles Camera, Security Camera, and Tablet interactions
 */
public class CameraListener implements Listener {

    // Store security camera locations per player (max 7)
    private final Map<UUID, List<Location>> securityCameras = new HashMap<>();
    private static final int MAX_CAMERAS = 7;

    /**
     * Handle Camera item usage (take screenshot)
     */
    @EventHandler
    public void onCameraUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Camera - Take screenshot
        if (CameraItems.isCamera(item)) {
            event.setCancelled(true);
            takeScreenshot(player);
            return;
        }

        // Tablet - Open camera menu
        if (CameraItems.isTablet(item)) {
            event.setCancelled(true);
            openTabletMenu(player);
        }
    }

    /**
     * Handle Security Camera placement
     */
    @EventHandler
    public void onSecurityCameraPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();

        if (!CameraItems.isSecurityCamera(item)) {
            return;
        }

        UUID playerId = player.getUniqueId();
        List<Location> cameras = securityCameras.getOrDefault(playerId, new ArrayList<>());

        // Check camera limit
        if (cameras.size() >= MAX_CAMERAS) {
            event.setCancelled(true);
            player.sendMessage("§cYou've reached the maximum of " + MAX_CAMERAS + " security cameras!");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return;
        }

        // Add camera location
        Location cameraLoc = event.getBlock().getLocation();
        cameras.add(cameraLoc);
        securityCameras.put(playerId, cameras);

        player.sendMessage("§aSecure Camera #" + cameras.size() + " installed! (" + cameras.size() + "/" + MAX_CAMERAS + ")");
        player.getWorld().playSound(cameraLoc, Sound.BLOCK_ANVIL_PLACE, 0.5f, 1.5f);

        // Spawn particles
        player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, cameraLoc.add(0.5, 0.5, 0.5), 20, 0.3, 0.3, 0.3, 0.1);
    }

    /**
     * Handle Security Camera breaking
     */
    @EventHandler
    public void onSecurityCameraBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.OBSERVER) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        List<Location> cameras = securityCameras.get(playerId);

        if (cameras == null) {
            return;
        }

        // Remove camera from list
        Location blockLoc = block.getLocation();
        if (cameras.removeIf(loc -> loc.equals(blockLoc))) {
            player.sendMessage("§cSecurity Camera removed! (" + cameras.size() + "/" + MAX_CAMERAS + " remaining)");
            player.getWorld().playSound(blockLoc, Sound.BLOCK_GLASS_BREAK, 1.0f, 0.8f);
        }
    }

    /**
     * Take a screenshot (client-side)
     */
    private void takeScreenshot(Player player) {
        // Flash effect
        player.getWorld().spawnParticle(
            Particle.FLASH,
            player.getEyeLocation(),
            1,
            0, 0, 0,
            0
        );

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.5f);

        player.sendMessage("§e📸 Screenshot taken!");
        player.sendMessage("§7Check your .minecraft/screenshots folder");
        player.sendMessage("§7(Press F2 in-game to take screenshots)");
    }

    /**
     * Open the Tablet menu showing security cameras
     */
    private void openTabletMenu(Player player) {
        UUID playerId = player.getUniqueId();
        List<Location> cameras = securityCameras.getOrDefault(playerId, new ArrayList<>());

        if (cameras.isEmpty()) {
            player.sendMessage("§cYou haven't placed any security cameras yet!");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return;
        }

        // Create inventory GUI
        Inventory menu = Bukkit.createInventory(null, 9, "§9Tablet - Security Cameras");

        // Add camera items
        for (int i = 0; i < cameras.size() && i < 7; i++) {
            Location cam = cameras.get(i);
            ItemStack cameraItem = new ItemStack(Material.ENDER_EYE);
            org.bukkit.inventory.meta.ItemMeta meta = cameraItem.getItemMeta();

            if (meta != null) {
                meta.setDisplayName("§eCamera #" + (i + 1));
                meta.setLore(Arrays.asList(
                    "§7Location: " + cam.getBlockX() + ", " + cam.getBlockY() + ", " + cam.getBlockZ(),
                    "§7World: " + cam.getWorld().getName(),
                    "",
                    "§eClick to view camera"
                ));
                cameraItem.setItemMeta(meta);
            }

            menu.setItem(i, cameraItem);
        }

        player.openInventory(menu);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.2f);
    }

    /**
     * Handle Tablet menu clicks
     */
    @EventHandler
    public void onTabletMenuClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        String title = event.getView().getTitle();
        if (!title.equals("§9Tablet - Security Cameras")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || clicked.getType() != Material.ENDER_EYE) return;

        UUID playerId = player.getUniqueId();
        List<Location> cameras = securityCameras.get(playerId);

        if (cameras == null) return;

        int slot = event.getSlot();
        if (slot >= 0 && slot < cameras.size()) {
            Location cameraLoc = cameras.get(slot);

            // Teleport player to camera location
            Location viewLoc = cameraLoc.clone().add(0.5, 1.5, 0.5);
            player.teleport(viewLoc);
            player.closeInventory();

            player.sendMessage("§aTeleported to Camera #" + (slot + 1) + "!");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.getWorld().spawnParticle(Particle.PORTAL, viewLoc, 30, 0.5, 0.5, 0.5, 0.1);
        }
    }

    /**
     * Get security cameras for a player
     */
    public List<Location> getPlayerCameras(UUID playerId) {
        return securityCameras.getOrDefault(playerId, new ArrayList<>());
    }
}
