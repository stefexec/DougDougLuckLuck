package de.gurkenwerfer.scratchofftickets.commands;

import de.gurkenwerfer.scratchofftickets.ScratchOffTickets;
import de.gurkenwerfer.scratchofftickets.utils.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class onLotteryCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown;
    ScratchOffTickets plugin;
    public onLotteryCommand(ScratchOffTickets plugin) {
        this.plugin = plugin;
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("lottery")) {
            if (args.length == 0) {
                Player p = (Player) sender;
                long minutes = plugin.getConfig().getInt("Shop.Cooldown");
                if (!cooldown.containsKey(p.getUniqueId()) || System.currentTimeMillis() - cooldown.get(p.getUniqueId()) > (minutes * 60000) || p.hasPermission("scratchofftickets.lottery.bypass")) {
                    cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                    // Open the buy menu
                    GUIUtil.openBuyMenu(p);
                }else{
                    p.sendMessage(ChatColor.RED + "You need to wait " + ChatColor.GRAY + (((minutes * 60000) - (System.currentTimeMillis() - cooldown.get(p.getUniqueId())))/60000) + ChatColor.RED + " minutes before buying a new ticket!");
                }
            } else if ( args.length == 1 && args[0].equalsIgnoreCase("help") && sender instanceof Player && sender.hasPermission("scratchofftickets.lottery")) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.GREEN + "You can buy 1 Lottery Ticket each hour");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
        }
        return true;
    }
}
