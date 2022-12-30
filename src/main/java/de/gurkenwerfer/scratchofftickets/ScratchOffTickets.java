package de.gurkenwerfer.scratchofftickets;

import de.gurkenwerfer.scratchofftickets.commands.onLotteryCommand;
import de.gurkenwerfer.scratchofftickets.commands.onTicketCommand;
import de.gurkenwerfer.scratchofftickets.listeners.InventoryClickListener;
import de.gurkenwerfer.scratchofftickets.listeners.onPlayerClickListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ScratchOffTickets extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy economy = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("scratchofftickets").setExecutor(new onTicketCommand());
        this.getCommand("lottery").setExecutor(new onLotteryCommand(this));
        getServer().getPluginManager().registerEvents(new onPlayerClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Vault
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            System.out.println("Vault not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
