package com.poopstuff.listeners;

import com.poopstuff.items.PoopItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Handles the Slimy Poop mechanics
 * - Detects when a player drinks Slimy Poop
 * - Applies Poison and Nausea effects when consumed
 */
public class SlimyPoopListener implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if drinking Slimy Poop
        if (PoopItems.isSlimyPoop(item)) {
            // Apply Poison effect (10 seconds, level 1)
            PotionEffect poison = new PotionEffect(
                PotionEffectType.POISON,
                200, // 10 seconds (20 ticks per second)
                0,   // Level I
                false,
                true,
                true
            );

            // Apply Nausea effect (15 seconds)
            PotionEffect nausea = new PotionEffect(
                PotionEffectType.NAUSEA,
                300, // 15 seconds
                0,   // Level I
                false,
                true,
                true
            );

            player.addPotionEffect(poison);
            player.addPotionEffect(nausea);

            player.sendMessage("§2You drank the Slimy Poop! §c*Intense regret and poisoning*");
            player.sendMessage("§7Why would you do that?!");

            // Return an empty glass bottle after consumption
            player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
        }
    }
}
