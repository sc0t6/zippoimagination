package com.poopstuff.listeners;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * Handles eating blocks (Soil, Snow, Sand, Dust, Flowers)
 * All give nausea effects when consumed
 */
public class EatablesListener implements Listener {

    // Track flower consumption per player
    private final Map<UUID, Integer> flowerEatenCount = new HashMap<>();
    private final Map<UUID, Long> lastEatTime = new HashMap<>();

    private static final long EAT_COOLDOWN = 1000; // 1 second between eats
    private static final int FLOWER_NAUSEA_THRESHOLD = 3; // Nausea after 3 flowers

    // Define eatable blocks
    private static final Set<Material> SOIL_BLOCKS = new HashSet<>(Arrays.asList(
        Material.DIRT,
        Material.GRASS_BLOCK,
        Material.COARSE_DIRT,
        Material.PODZOL,
        Material.MYCELIUM,
        Material.ROOTED_DIRT
    ));

    private static final Set<Material> SNOW_BLOCKS = new HashSet<>(Arrays.asList(
        Material.SNOW,
        Material.SNOW_BLOCK,
        Material.POWDER_SNOW
    ));

    private static final Set<Material> SAND_BLOCKS = new HashSet<>(Arrays.asList(
        Material.SAND,
        Material.RED_SAND
    ));

    private static final Set<Material> DUST_BLOCKS = new HashSet<>(Arrays.asList(
        Material.GRAVEL,
        Material.SUSPICIOUS_SAND,
        Material.SUSPICIOUS_GRAVEL
    ));

    private static final Set<Material> FLOWER_BLOCKS = new HashSet<>(Arrays.asList(
        Material.DANDELION,
        Material.POPPY,
        Material.BLUE_ORCHID,
        Material.ALLIUM,
        Material.AZURE_BLUET,
        Material.RED_TULIP,
        Material.ORANGE_TULIP,
        Material.WHITE_TULIP,
        Material.PINK_TULIP,
        Material.OXEYE_DAISY,
        Material.CORNFLOWER,
        Material.LILY_OF_THE_VALLEY,
        Material.WITHER_ROSE,
        Material.SUNFLOWER,
        Material.LILAC,
        Material.ROSE_BUSH,
        Material.PEONY
    ));

    private static final Set<Material> LEAVES = new HashSet<>(Arrays.asList(
        Material.OAK_LEAVES,
        Material.SPRUCE_LEAVES,
        Material.BIRCH_LEAVES,
        Material.JUNGLE_LEAVES,
        Material.ACACIA_LEAVES,
        Material.DARK_OAK_LEAVES,
        Material.MANGROVE_LEAVES,
        Material.CHERRY_LEAVES,
        Material.AZALEA_LEAVES,
        Material.FLOWERING_AZALEA_LEAVES
    ));

    @EventHandler
    public void onPlayerEatBlock(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        Material blockType = block.getType();
        UUID playerId = player.getUniqueId();

        // Check cooldown
        long currentTime = System.currentTimeMillis();
        if (lastEatTime.containsKey(playerId)) {
            if (currentTime - lastEatTime.get(playerId) < EAT_COOLDOWN) {
                return;
            }
        }

        // Check if block is eatable
        boolean isEatable = false;
        String blockName = "";

        if (SOIL_BLOCKS.contains(blockType)) {
            isEatable = true;
            blockName = "Soil";
            eatSoil(player, block);
        } else if (SNOW_BLOCKS.contains(blockType)) {
            isEatable = true;
            blockName = "Snow";
            eatSnow(player, block);
        } else if (SAND_BLOCKS.contains(blockType)) {
            isEatable = true;
            blockName = "Sand";
            eatSand(player, block);
        } else if (DUST_BLOCKS.contains(blockType)) {
            isEatable = true;
            blockName = "Dust/Gravel";
            eatDust(player, block);
        } else if (FLOWER_BLOCKS.contains(blockType)) {
            isEatable = true;
            blockName = "Flower";
            eatFlower(player, block);
        } else if (LEAVES.contains(blockType)) {
            isEatable = true;
            blockName = "Leaves";
            eatLeaves(player, block);
        }

        if (isEatable) {
            lastEatTime.put(playerId, currentTime);
            event.setCancelled(true); // Prevent block interaction
        }
    }

    /**
     * Eat Soil - Gives nausea
     */
    private void eatSoil(Player player, Block block) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0)); // 5 seconds
        player.sendMessage("§6You ate some §eSoil§6! Gross!");

        playEatEffects(player, block, Particle.BLOCK, Sound.ENTITY_GENERIC_EAT);
        // Don't break the block
    }

    /**
     * Eat Snow - Gives nausea
     */
    private void eatSnow(Player player, Block block) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0)); // 5 seconds
        player.sendMessage("§bYou ate some §fSnow§b! It's freezing!");

        playEatEffects(player, block, Particle.SNOWFLAKE, Sound.BLOCK_SNOW_BREAK);
        // Don't break the block
    }

    /**
     * Eat Sand - Gives nausea
     */
    private void eatSand(Player player, Block block) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0)); // 5 seconds
        player.sendMessage("§eYou ate some §6Sand§e! Your mouth is so dry!");

        playEatEffects(player, block, Particle.BLOCK, Sound.BLOCK_SAND_BREAK);
        // Don't break the block
    }

    /**
     * Eat Dust/Gravel - Gives nausea
     */
    private void eatDust(Player player, Block block) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0)); // 5 seconds
        player.sendMessage("§7You ate some §8Dust§7! You're choking!");

        playEatEffects(player, block, Particle.BLOCK, Sound.BLOCK_GRAVEL_BREAK);
        // Don't break the block
    }

    /**
     * Eat Flower - Gives nausea after 3 eaten
     */
    private void eatFlower(Player player, Block block) {
        UUID playerId = player.getUniqueId();
        int count = flowerEatenCount.getOrDefault(playerId, 0) + 1;
        flowerEatenCount.put(playerId, count);

        player.sendMessage("§dYou ate a §5Flower§d! (" + count + " flowers eaten)");

        if (count >= FLOWER_NAUSEA_THRESHOLD) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 1)); // 10 seconds, level 2
            player.sendMessage("§cYou've eaten too many flowers! You feel sick!");
            flowerEatenCount.put(playerId, 0); // Reset counter
        }

        playEatEffects(player, block, Particle.FALLING_DUST, Sound.BLOCK_GRASS_BREAK);
        block.setType(Material.AIR); // Remove the flower
    }

    /**
     * Eat Leaves - Gives nausea after 6-10 eaten
     */
    private void eatLeaves(Player player, Block block) {
        UUID playerId = player.getUniqueId();
        int count = flowerEatenCount.getOrDefault(playerId, 0) + 1;
        flowerEatenCount.put(playerId, count);

        player.sendMessage("§2You ate some §aLeaves§2! (" + count + " leaves eaten)");

        // Random threshold between 6-10
        int threshold = 6 + new Random().nextInt(5);
        if (count >= threshold) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 1)); // 10 seconds, level 2
            player.sendMessage("§cYou've eaten too many leaves! You feel terrible!");
            flowerEatenCount.put(playerId, 0); // Reset counter
        }

        playEatEffects(player, block, Particle.BLOCK, Sound.BLOCK_GRASS_BREAK);
        // Don't break the block
    }

    /**
     * Play eating effects
     */
    private void playEatEffects(Player player, Block block, Particle particle, Sound sound) {
        player.getWorld().spawnParticle(
            particle,
            block.getLocation().add(0.5, 0.5, 0.5),
            10,
            0.3, 0.3, 0.3,
            0.1,
            block.getBlockData()
        );

        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 0.5f, 1.0f);
    }
}
