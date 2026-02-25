package com.poopstuff.listeners;

import com.poopstuff.items.RayGunItems;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

/**
 * Handles all Ray Gun interactions
 * - Shrink Ray: Makes entities 0.5 size
 * - Grow Ray: Makes entities 1.5 size
 * - Normal Ray: Resets entities to 1.0 size
 * - Ray Gun (Weapon): Shoots damaging projectiles
 */
public class RayGunListener implements Listener {

    private static final double SHRINK_SCALE = 0.5;
    private static final double GROW_SCALE = 1.5;
    private static final double NORMAL_SCALE = 1.0;
    private static final double RAY_GUN_DAMAGE = 15.0;
    private static final double RAY_RANGE = 50.0;

    /**
     * Handle right-click with ray guns (self-targeting)
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Shrink Ray - Right click to shrink self
        if (RayGunItems.isShrinkRay(item)) {
            event.setCancelled(true);
            setPlayerScale(player, SHRINK_SCALE);
            player.sendMessage("§bYou shrunk yourself!");
            playRayEffect(player.getEyeLocation(), player.getLocation(), Color.AQUA);
            return;
        }

        // Grow Ray - Right click to grow self
        if (RayGunItems.isGrowRay(item)) {
            event.setCancelled(true);
            setPlayerScale(player, GROW_SCALE);
            player.sendMessage("§aYou grew yourself!");
            playRayEffect(player.getEyeLocation(), player.getLocation(), Color.GREEN);
            return;
        }

        // Normal Ray - Right click to restore self
        if (RayGunItems.isNormalRay(item)) {
            event.setCancelled(true);
            setPlayerScale(player, NORMAL_SCALE);
            player.sendMessage("§7You restored your normal size!");
            playRayEffect(player.getEyeLocation(), player.getLocation(), Color.GRAY);
            return;
        }

        // Ray Gun (Weapon) - Right click to shoot
        if (RayGunItems.isRayGun(item)) {
            event.setCancelled(true);
            shootRayGun(player);
        }
    }

    /**
     * Handle left-click on entities with ray guns
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Entity target = event.getRightClicked();

        if (!(target instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingTarget = (LivingEntity) target;

        // Shrink Ray - Left click entity to shrink target
        if (RayGunItems.isShrinkRay(item)) {
            event.setCancelled(true);
            setEntityScale(livingTarget, SHRINK_SCALE);
            player.sendMessage("§bYou shrunk " + getEntityName(livingTarget) + "!");
            playRayEffect(player.getEyeLocation(), livingTarget.getEyeLocation(), Color.AQUA);
            return;
        }

        // Grow Ray - Left click entity to grow target
        if (RayGunItems.isGrowRay(item)) {
            event.setCancelled(true);
            setEntityScale(livingTarget, GROW_SCALE);
            player.sendMessage("§aYou grew " + getEntityName(livingTarget) + "!");
            playRayEffect(player.getEyeLocation(), livingTarget.getEyeLocation(), Color.GREEN);
            return;
        }

        // Normal Ray - Left click entity to restore target
        if (RayGunItems.isNormalRay(item)) {
            event.setCancelled(true);
            setEntityScale(livingTarget, NORMAL_SCALE);
            player.sendMessage("§7You restored " + getEntityName(livingTarget) + " to normal size!");
            playRayEffect(player.getEyeLocation(), livingTarget.getEyeLocation(), Color.GRAY);
        }
    }

    /**
     * Shoot the Ray Gun weapon (requires ammo)
     */
    private void shootRayGun(Player player) {
        // Check for ammo in inventory
        ItemStack ammo = null;
        for (ItemStack inventoryItem : player.getInventory().getContents()) {
            if (RayGunItems.isRayGunAmmo(inventoryItem)) {
                ammo = inventoryItem;
                break;
            }
        }

        if (ammo == null) {
            player.sendMessage("§cOut of ammo! Craft Ray Gun Ammo first!");
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.0f, 1.0f);
            return;
        }

        // Consume 1 ammo
        ammo.setAmount(ammo.getAmount() - 1);

        // Ray trace to find target
        RayTraceResult result = player.getWorld().rayTraceEntities(
            player.getEyeLocation(),
            player.getLocation().getDirection(),
            RAY_RANGE,
            0.5,
            entity -> entity instanceof LivingEntity && entity != player
        );

        Location endLocation;
        if (result != null && result.getHitEntity() != null) {
            LivingEntity target = (LivingEntity) result.getHitEntity();
            target.damage(RAY_GUN_DAMAGE, player);
            endLocation = target.getEyeLocation();
            player.sendMessage("§cYou blasted " + getEntityName(target) + " for §4" + RAY_GUN_DAMAGE + " ❤§c!");
        } else {
            endLocation = player.getEyeLocation().add(player.getLocation().getDirection().multiply(RAY_RANGE));
        }

        // Play effects
        playRayEffect(player.getEyeLocation(), endLocation, Color.RED);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5f, 2.0f);
    }

    /**
     * Set player scale (size)
     */
    private void setPlayerScale(Player player, double scale) {
        AttributeInstance attribute = player.getAttribute(Attribute.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(scale);
        }
    }

    /**
     * Set entity scale (size)
     */
    private void setEntityScale(LivingEntity entity, double scale) {
        AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(scale);
        }
    }

    /**
     * Play ray beam visual effect
     */
    private void playRayEffect(Location start, Location end, Color color) {
        World world = start.getWorld();
        if (world == null) return;

        // Calculate the distance and direction
        double distance = start.distance(end);
        Location current = start.clone();
        org.bukkit.util.Vector direction = end.toVector().subtract(start.toVector()).normalize();

        // Create particle beam
        for (double i = 0; i < distance; i += 0.3) {
            current.add(direction.clone().multiply(0.3));
            world.spawnParticle(
                Particle.DUST,
                current,
                2,
                0.05, 0.05, 0.05,
                0,
                new Particle.DustOptions(color, 1.0f)
            );
        }

        // Sound effect
        world.playSound(start, Sound.BLOCK_BEACON_ACTIVATE, 0.5f, 2.0f);
    }

    /**
     * Get display name for entity
     */
    private String getEntityName(LivingEntity entity) {
        if (entity instanceof Player) {
            return ((Player) entity).getName();
        }
        return entity.getType().name().toLowerCase().replace("_", " ");
    }
}
