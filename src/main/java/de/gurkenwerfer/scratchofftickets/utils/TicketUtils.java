package de.gurkenwerfer.scratchofftickets.utils;

import de.gurkenwerfer.scratchofftickets.models.Rarity;
import de.gurkenwerfer.scratchofftickets.models.Ticket;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TicketUtils {
    public static ItemStack createTicket(Ticket ticket) {
        ItemStack ticketStack = new ItemStack(Material.PAPER, ticket.getAmount());
        ItemMeta ticketMeta = ticketStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        assert ticketMeta != null;
        ticketMeta.setDisplayName(ticket.getName());
        Rarity rarity = ticket.getRarity();
        switch (rarity) {
            case COMMON -> {
                ticketMeta.setCustomModelData(600);
                lore.add("Rarity: " + ChatColor.DARK_RED + ticket.getRarity().toString());
            }
            case RARE -> {
                ticketMeta.setCustomModelData(601);
                lore.add("Rarity: " + ChatColor.BLUE + ticket.getRarity());
            }
            case EPIC -> {
                ticketMeta.setCustomModelData(602);
                lore.add("Rarity: " + ChatColor.LIGHT_PURPLE + ticket.getRarity());
            }
            case LEGENDARY -> {
                ticketMeta.setCustomModelData(603);
                lore.add("Rarity: " + ChatColor.GOLD + ticket.getRarity());
            }
        }
        lore.add("Price: " + ChatColor.GRAY + ticket.getPrice());
        lore.add("ID: " + ChatColor.GRAY + ticket.getId());
        ticketMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true);
        ticketMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        ticketMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
        ticketMeta.setLore(lore);
        ticketStack.setItemMeta(ticketMeta);

        return new ItemStack(ticketStack);
    }
}
