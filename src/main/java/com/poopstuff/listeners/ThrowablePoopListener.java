package com.poopstuff.listeners;

import com.poopstuff.items.PoopItems;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles the Throwable Poop mechanics
 * - Detects when a player throws a Throwable Poop item
 * - Applies Nausea effect when it hits an entity
 */
public class ThrowablePoopListener implements Listener {

    // Track which snowballs are actually poop projectiles
    private final Map<UUID, Boolean> poopProjectiles = new HashMap<>();

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player) event.getEntity().getShooter();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // Check if the player is throwing a Throwable Poop
        if (PoopItems.isThrowablePoop(itemInHand)) {
            // Mark this snowball as a poop projectile
            poopProjectiles.put(event.getEntity().getUniqueId(), true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;

        Snowball snowball = (Snowball) event.getEntity();

        // Check if this snowball is a poop projectile
        if (!poopProjectiles.containsKey(snowball.getUniqueId())) return;

        // Remove from tracking
        poopProjectiles.remove(snowball.getUniqueId());

        // Get the entity that was hit
        Entity hitEntity = event.getHitEntity();

        if (hitEntity instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) hitEntity;

            // Apply Nausea effect (15 seconds, level 2)
            PotionEffect nausea = new PotionEffect(
                PotionEffectType.NAUSEA,
                300, // 15 seconds (20 ticks per second)
                1,   // Level 2
                false,
                true,
                true
            );
            target.addPotionEffect(nausea);

            // Send message to the target
            if (target instanceof Player) {
                Player targetPlayer = (Player) target;
                targetPlayer.sendMessage("§6You've been hit by poop! §e*Nausea intensifies*");
            }

            // Send message to the thrower
            if (snowball.getShooter() instanceof Player) {
                Player shooter = (Player) snowball.getShooter();
                shooter.sendMessage("§6Direct hit! §eYour poop found its target!");
            }
        }
    }
}
