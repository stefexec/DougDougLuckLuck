package de.gurkenwerfer.scratchofftickets.utils;

import de.gurkenwerfer.scratchofftickets.ScratchOffTickets;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class GUIUtil {

    public static void openBuyMenu(Player p){

        //Open up buyItem menu
        Inventory buyTicketMenu = Bukkit.createInventory(p, 9, "Buy Scratch-Off Tickets");

        // Buy Option
        ItemStack buyItem = new ItemStack(Material.EMERALD, 1);
        ItemMeta buyItemMeta = buyItem.getItemMeta();
        buyItemMeta.setDisplayName(ChatColor.DARK_GREEN + "Buy 1 Ticket");
        buyItem.setItemMeta(buyItemMeta);
        buyTicketMenu.setItem(0, buyItem);

        //Add player info
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta player_meta = (SkullMeta) playerHead.getItemMeta();
        player_meta.setDisplayName(p.getName());
        player_meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
        Economy econ = ScratchOffTickets.getEconomy();
        Integer balance = (int) econ.getBalance(p);
        player_meta.setLore(new ArrayList<String>() {{
            add(ChatColor.GOLD + "Your balance: " + ChatColor.GREEN + econ.format(balance));
        }});
        playerHead.setItemMeta(player_meta);
        buyTicketMenu.setItem(4, playerHead);

        //Cancel option
        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancel_meta = cancel.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.RED + "Exit");
        cancel.setItemMeta(cancel_meta);
        buyTicketMenu.setItem(8, cancel);

        p.openInventory(buyTicketMenu);
    }
}
