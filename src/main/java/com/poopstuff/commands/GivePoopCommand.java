package com.poopstuff.commands;

import com.poopstuff.items.PoopItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Admin command to give poop items to players
 * Usage: /givepoop <player> <throwable|placeable|slimy>
 */
public class GivePoopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("poopmod.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUsage: /givepoop <player> <throwable|placeable|slimy> [amount]");
            return true;
        }

        // Get target player
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return true;
        }

        // Get item type
        String itemType = args[1].toLowerCase();
        ItemStack item;

        switch (itemType) {
            case "throwable":
            case "throw":
                item = PoopItems.createThrowablePoop();
                break;
            case "placeable":
            case "place":
            case "block":
                item = PoopItems.createPlaceablePoop();
                break;
            case "slimy":
            case "slime":
            case "drink":
                item = PoopItems.createSlimyPoop();
                break;
            default:
                sender.sendMessage("§cInvalid item type! Use: throwable, placeable, or slimy");
                return true;
        }

        // Get amount (default 1)
        int amount = 1;
        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
                if (amount < 1 || amount > 64) {
                    sender.sendMessage("§cAmount must be between 1 and 64!");
                    return true;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage("§cInvalid amount: " + args[2]);
                return true;
            }
        }

        // Give items
        item.setAmount(amount);
        target.getInventory().addItem(item);

        sender.sendMessage("§aGave " + amount + "x " + item.getItemMeta().getDisplayName() + " §ato " + target.getName());
        target.sendMessage("§6You received " + amount + "x " + item.getItemMeta().getDisplayName() + "§6!");

        return true;
    }
}
