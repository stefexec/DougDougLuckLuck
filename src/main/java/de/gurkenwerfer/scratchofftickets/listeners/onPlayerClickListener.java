package de.gurkenwerfer.scratchofftickets.listeners;

import de.gurkenwerfer.scratchofftickets.ScratchOffTickets;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class onPlayerClickListener implements Listener {

    ScratchOffTickets plugin;
    public onPlayerClickListener(ScratchOffTickets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {

        if (e.getItem() != null && e.getItem().getType() == Material.PAPER && e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemMeta ticketMeta = e.getItem().getItemMeta();
            Player p = e.getPlayer();
            if (ticketMeta != null && ticketMeta.hasLore()) {
                e.getPlayer().sendMessage("You redeemed a ticket!");
                if (ticketMeta.getLore().get(0).contains("Common")) {
                    Random random = new Random();
                    int randomInt = random.nextInt(100);
                    // Pick from Common Loot Table (90% Chance)
                    if (randomInt <= 90) {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Common").getStringList("CommonLoot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                        }
                    // Else pick from Jackpot Table (10% Chance)
                    } else {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Common").getStringList("CommonJackpot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won the JACKPOT and got a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won the JACKPOT amount of " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        }
                    }
                } else if (ticketMeta.getLore().get(0).contains("Rare")) {
                    Random random = new Random();
                    int randomInt = random.nextInt(100);
                    // Pick from Common Loot Table (90% Chance)
                    if (randomInt <= 90) {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Rare").getStringList("RareLoot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                        }
                    // Else pick from Jackpot Table (10% Chance)
                    } else {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Rare").getStringList("RareJackpot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won the JACKPOT and got a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won the JACKPOT amount of " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        }
                    }
                } else if (ticketMeta.getLore().get(0).contains("Epic")) {
                    Random random = new Random();
                    int randomInt = random.nextInt(100);
                    // Pick from Common Loot Table (90% Chance)
                    if (randomInt <= 90) {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Epic").getStringList("EpicLoot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                        }
                    // Else pick from Jackpot Table (10% Chance)
                    } else {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Epic").getStringList("EpicJackpot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won the JACKPOT and got a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won the JACKPOT amount of " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        }
                    }
                } else if (ticketMeta.getLore().get(0).contains("Legendary")) {
                    Random random = new Random();
                    int randomInt = random.nextInt(100);
                    // Pick from Common Loot Table (90% Chance)
                    if (randomInt <= 90) {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Legendary").getStringList("LegendaryLoot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                        }
                    // Else pick from Jackpot Table (10% Chance)
                    } else {
                        List<String> loot = plugin.getConfig().getConfigurationSection("Legendary").getStringList("LegendaryJackpot");
                        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
                        int index = new Random().nextInt(loot.size());
                        String item = loot.get(index);
                        if (Material.getMaterial(item.toUpperCase()) != null) {
                            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
                            e.getPlayer().getInventory().addItem(wonItem);
                            e.getPlayer().sendMessage("You won the JACKPOT and got a " + item + "!");
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        } else if (item.startsWith("Spoons:")) {
                            String[] split = item.split(":");
                            int amount = Integer.parseInt(split[1]);
                            Economy economy = ScratchOffTickets.getEconomy();
                            EconomyResponse response = economy.depositPlayer(p, amount);
                            if (response.transactionSuccess()) {
                                e.getPlayer().sendMessage("You won the JACKPOT amount of " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getPlayer().sendMessage("An error occurred: " + response.errorMessage);
                            }
                            p.playSound(p.getLocation(), "ticket.win.1", 0.7f, 1);
                        }
                    }
                }
            }
        }
    }
}
