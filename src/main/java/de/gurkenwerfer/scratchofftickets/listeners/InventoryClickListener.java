package de.gurkenwerfer.scratchofftickets.listeners;

import de.gurkenwerfer.scratchofftickets.ScratchOffTickets;
import de.gurkenwerfer.scratchofftickets.models.Ticket;
import de.gurkenwerfer.scratchofftickets.utils.TicketUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class InventoryClickListener implements Listener {

    ScratchOffTickets plugin;
    public InventoryClickListener(ScratchOffTickets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(e.getClickedInventory() != null && e.getView().getTitle().equalsIgnoreCase("Buy Scratch-Off Tickets") && e.getCurrentItem() != null) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.EMERALD || e.getCurrentItem().getType() == Material.BARRIER && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "Buy 1 Ticket")) {
                    Economy economy = ScratchOffTickets.getEconomy();
                    if(economy.getBalance(p) >= 1000) {
                        economy.withdrawPlayer(p, 1000);
                        Random random = new Random();
                        int randomInt = random.nextInt(100);
                        if (randomInt <= 90){
                            addTicket(p, "Common", 1000, 1);
                        } else if (randomInt <= 95){
                            addTicket(p, "Rare", 3000, 1);
                        } else if (randomInt <= 98){
                            addTicket(p, "Epic", 5000, 1);
                        } else {
                            addTicket(p, "Legendary", 10000, 1);
                        }
                        p.sendMessage(ChatColor.GREEN + "You have bought a random scratch-off ticket for $1000!");
                    } else {
                        p.sendMessage(ChatColor.RED + "You do not have enough money to buy a scratch-off ticket!");
                        return;
                    }
                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "You bought " + ChatColor.GRAY + "1" + ChatColor.GREEN + " Ticket!");
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Exit")) {
                    p.closeInventory();
                    p.sendMessage(ChatColor.GOLD + "Maybe next time!");
                }
            }
        }
    }

    public void addTicket(Player p, String rarity, int price, int amount) {
        int id = plugin.getConfig().getInt("Stats." + rarity);
        id++;
        Ticket ticket = new Ticket("Scratch-Off Ticket", rarity, price, id, amount);
        p.getInventory().addItem(TicketUtils.createTicket(ticket));

        // TODO: Add ticket stats to database
        //plugin.getConfig().set("Stats." + rarity, id);
        //plugin.saveConfig();
    }
}
