package com.poopstuff.listeners;

import com.poopstuff.items.PoopItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the Placeable Poop mechanics
 * - Tracks placed poop blocks
 * - Applies Slowness and Nausea when players walk on them
 * - Handles block breaking to drop the correct item
 */
public class PlaceablePoopListener implements Listener {

    // Track locations of placed poop blocks
    private final Map<Location, Boolean> poopBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItemInHand();

        // Check if placing a Poop Block
        if (PoopItems.isPlaceablePoop(itemInHand)) {
            Block placedBlock = event.getBlockPlaced();

            // Track this location as a poop block
            poopBlocks.put(placedBlock.getLocation(), true);

            player.sendMessage("§6Poop Block placed! §7Watch others suffer...");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        // Check if this is a tracked poop block
        if (poopBlocks.containsKey(location)) {
            poopBlocks.remove(location);

            // Cancel default drops and drop our custom item instead
            event.setDropItems(false);
            block.getWorld().dropItemNaturally(location, PoopItems.createPlaceablePoop());

            event.getPlayer().sendMessage("§6Poop Block removed!");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location fromLoc = event.getFrom();
        Location toLoc = event.getTo();

        // Check if player hasn't actually moved blocks
        if (toLoc == null || (fromLoc.getBlockX() == toLoc.getBlockX() &&
            fromLoc.getBlockY() == toLoc.getBlockY() &&
            fromLoc.getBlockZ() == toLoc.getBlockZ())) {
            return;
        }

        // Check the block the player is standing on
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();

        // If standing on a poop block
        if (blockBelow.getType() == Material.BROWN_MUSHROOM_BLOCK &&
            poopBlocks.containsKey(blockBelow.getLocation())) {

            // Apply Slowness II for 5 seconds (100 ticks)
            PotionEffect slowness = new PotionEffect(
                PotionEffectType.SLOWNESS,
                100, // 5 seconds
                1,   // Level II
                false,
                true,
                true
            );

            // Apply Nausea for 5 seconds (100 ticks)
            PotionEffect nausea = new PotionEffect(
                PotionEffectType.NAUSEA,
                100, // 5 seconds
                0,   // Level I
                false,
                true,
                true
            );

            player.addPotionEffect(slowness);
            player.addPotionEffect(nausea);

            // Only send message occasionally to avoid spam
            if (!player.hasPotionEffect(PotionEffectType.SLOWNESS)) {
                player.sendMessage("§6Eww! You stepped in poop! §e*Feels sick*");
            }
        }
    }
}
