package de.gurkenwerfer.scratchofftickets.commands;

import de.gurkenwerfer.scratchofftickets.models.Ticket;
import de.gurkenwerfer.scratchofftickets.utils.TicketUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class onTicketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("scratchofftickets")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Usage: /scratchofftickets give <name> <rarity> <price> <id> <amount>");
                return true;
            }
            if (args[0].equalsIgnoreCase("give") && sender instanceof Player && sender.hasPermission("scratchofftickets.give") && args.length == 6) {

                Player p = (Player) sender;
                // Create a new ticket object
                Ticket ticket = new Ticket(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                // Give the player the ticket(s)
                p.getInventory().addItem(TicketUtils.createTicket(ticket));
                p.sendMessage(ChatColor.GREEN + "You have created a ticket!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
