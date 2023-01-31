package de.gurkenwerfer.scratchofftickets.listeners;

import de.gurkenwerfer.scratchofftickets.ScratchOffTickets;
import de.gurkenwerfer.scratchofftickets.models.Rarity;
import io.lumine.mythic.bukkit.MythicBukkit;
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

    public enum RewardType {
        LOOT("Loot"),
        JACKPOT("Jackpot");
        private final String name;
        RewardType(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return name;
        }
    }

    public void generateLoot(Rarity rarity, ItemStack i, Player p) {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        RewardType rewardType;
        if (randomInt <= 90) {
            rewardType = RewardType.LOOT;
        } else {
            rewardType = RewardType.JACKPOT;
        }
        List<String> loot = plugin.getConfig().getConfigurationSection(rarity.toString()).getStringList(rarity.toString() + rewardType);
        p.playSound(p.getLocation(), "ticket.redeem.1", 0.7f, 1);
        int index = random.nextInt(loot.size());
        String item = loot.get(index);

        if (Material.getMaterial(item.toUpperCase()) != null) {
            ItemStack wonItem = new ItemStack(Material.getMaterial(item.toUpperCase()));
            p.getInventory().addItem(wonItem);
            if (rewardType == RewardType.LOOT) {
                p.sendMessage("You won a " + item + "!");
            } else {
                p.sendMessage("You won the JACKPOT and got a " + item + "!");
            }
            i.setAmount(i.getAmount() - 1);
        } else if (item.startsWith("Spoons:")) {
            String[] split = item.split(":");
            int amount = Integer.parseInt(split[1]);
            Economy economy = ScratchOffTickets.getEconomy();
            EconomyResponse response = economy.depositPlayer(p, amount);
            if (!response.transactionSuccess())  p.sendMessage("An error occurred: " + response.errorMessage);

            if (rewardType.equals(RewardType.LOOT)) {
                p.sendMessage("You won " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
            } else {
                p.sendMessage("You won the JACKPOT amount of " + economy.format(amount) + " " + economy.currencyNamePlural() + "!");
            }
            i.setAmount(i.getAmount() - 1);

        } else {
            //check if the item name is a valid mythicmobs item
            MythicBukkit mythicBukkit = MythicBukkit.inst();
            if (mythicBukkit.getItemManager().getItem(item).isPresent()) {
                ItemStack wonItem = (ItemStack) mythicBukkit.getItemManager().getItem(item).get().generateItemStack(1);
                p.getInventory().addItem(wonItem);
                if (rewardType == RewardType.LOOT) {
                    p.sendMessage("You won a " + item + "!");
                } else {
                    p.sendMessage("You won the JACKPOT and got a " + item + "!");
                }
                i.setAmount(i.getAmount() - 1);
            } else {
                p.sendMessage("An error occurred while redeeming the ticket. Please contact an administrator.");
            }


        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        //if the item isn't right-clicked and a paper then return;
        if (e.getItem() == null || e.getItem().getType() != Material.PAPER || e.getAction() != Action.RIGHT_CLICK_AIR) return;

        ItemMeta ticketMeta = e.getItem().getItemMeta();

        if (ticketMeta == null || !ticketMeta.hasLore()) return;

        e.getPlayer().sendMessage("You redeemed a ticket!");

        if (ticketMeta.getLore().get(0).contains("Common")) generateLoot(Rarity.COMMON, e.getItem(), e.getPlayer());
        else if (ticketMeta.getLore().get(0).contains("Rare")) generateLoot(Rarity.RARE, e.getItem(),e.getPlayer());
        else if (ticketMeta.getLore().get(0).contains("Epic")) generateLoot(Rarity.EPIC, e.getItem(), e.getPlayer());
        else if (ticketMeta.getLore().get(0).contains("Legendary")) generateLoot(Rarity.LEGENDARY, e.getItem(),e.getPlayer());
    }
}
