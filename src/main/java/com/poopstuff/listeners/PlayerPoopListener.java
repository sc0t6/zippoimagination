package com.poopstuff.listeners;

import com.poopstuff.PoopPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles the Player Poop mechanic
 * Players hold Shift with empty hand for 3 seconds to poop behind them
 * (Spigot cannot detect P key without client mod, so using empty hand as trigger)
 */
public class PlayerPoopListener implements Listener {

    private final Map<UUID, BukkitRunnable> poopingPlayers = new HashMap<>();
    private final Map<UUID, Long> lastPoopTime = new HashMap<>();
    private static final long POOP_COOLDOWN = 10000; // 10 seconds cooldown
    private static final int POOP_DURATION = 60; // 3 seconds (60 ticks)

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (event.isSneaking()) {
            // Player started sneaking, start the poop timer
            startPoopTimer(player);
        } else {
            // Player stopped sneaking, cancel the timer
            cancelPoopTimer(playerId);
        }
    }

    private void startPoopTimer(Player player) {
        UUID playerId = player.getUniqueId();

        // Check cooldown
        if (lastPoopTime.containsKey(playerId)) {
            long timeSinceLastPoop = System.currentTimeMillis() - lastPoopTime.get(playerId);
            if (timeSinceLastPoop < POOP_COOLDOWN) {
                return; // Still on cooldown
            }
        }

        // Cancel existing timer if any
        cancelPoopTimer(playerId);

        // Create new timer
        BukkitRunnable task = new BukkitRunnable() {
            int ticksElapsed = 0;

            @Override
            public void run() {
                // Check if player is still sneaking with empty hand
                if (!player.isSneaking() || player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    cancelPoopTimer(playerId);
                    return;
                }

                ticksElapsed++;

                // Show progress particles every 0.5 seconds
                if (ticksElapsed % 10 == 0) {
                    player.spawnParticle(Particle.SMOKE, player.getLocation(), 5, 0.3, 0.5, 0.3, 0.01);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.3f, 0.8f);
                }

                // After 3 seconds (60 ticks)
                if (ticksElapsed >= POOP_DURATION) {
                    performPoop(player);
                    cancel();
                    poopingPlayers.remove(playerId);
                }
            }
        };

        poopingPlayers.put(playerId, task);
        task.runTaskTimer(PoopPlugin.getInstance(), 0L, 1L);
    }

    private void cancelPoopTimer(UUID playerId) {
        BukkitRunnable task = poopingPlayers.remove(playerId);
        if (task != null) {
            task.cancel();
        }
    }

    private void performPoop(Player player) {
        // Get the block behind the player
        Location playerLoc = player.getLocation();
        Location behindLoc = playerLoc.clone().subtract(playerLoc.getDirection().normalize());
        behindLoc.setY(Math.floor(playerLoc.getY())); // Ensure it's at the player's feet level

        Block targetBlock = behindLoc.getBlock();
        Block blockAbove = targetBlock.getRelative(0, 1, 0);

        // Check if the block behind is air or replaceable
        if (blockAbove.getType() == Material.AIR || blockAbove.getType().isAir()) {
            // Place the poop block
            blockAbove.setType(Material.BROWN_MUSHROOM_BLOCK);

            // Effects
            player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, blockAbove.getLocation().add(0.5, 0.5, 0.5), 20, 0.3, 0.3, 0.3, 0.05);
            player.getWorld().playSound(blockAbove.getLocation(), Sound.BLOCK_HONEY_BLOCK_SLIDE, 1.0f, 0.5f);
            player.sendMessage("§6💩 You pooped!");

            // Set cooldown
            lastPoopTime.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            player.sendMessage("§cThere's no space to poop here!");
        }
    }
}
