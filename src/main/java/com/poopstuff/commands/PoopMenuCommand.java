package com.poopstuff.commands;

import com.poopstuff.items.PoopItems;
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

    private static final String MENU_TITLE = "§6§l💩 The Poop Mod Menu";

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
        // Create a 3x9 (27 slot) inventory
        Inventory menu = Bukkit.createInventory(null, 27, MENU_TITLE);

        // Add decorative borders
        ItemStack border = createBorderItem();
        for (int i = 0; i < 27; i++) {
            if (i < 9 || i >= 18 || i % 9 == 0 || i % 9 == 8) {
                menu.setItem(i, border);
            }
        }

        // Add poop items in the center
        menu.setItem(10, PoopItems.createThrowablePoop());
        menu.setItem(13, PoopItems.createPlaceablePoop());
        menu.setItem(16, PoopItems.createSlimyPoop());

        // Add info item
        menu.setItem(22, createInfoItem());

        player.openInventory(menu);
        player.sendMessage("§6Welcome to the Poop Mod! §eClick items to receive them.");
    }

    /**
     * Creates a decorative border item
     */
    private ItemStack createBorderItem() {
        ItemStack item = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates an info item explaining the menu
     */
    private ItemStack createInfoItem() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e§lHow to Use");
            meta.setLore(Arrays.asList(
                "",
                "§7Click on any poop item above",
                "§7to receive it in your inventory!",
                "",
                "§6Throwable Poop §7- Throw at enemies",
                "§6Poop Block §7- Place as a hazard",
                "§2Slimy Poop §7- Drink for effects",
                "",
                "§eHave fun with poop! 💩"
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

        // Give the player the clicked poop item
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
    }
}
