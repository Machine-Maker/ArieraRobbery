package me.x1machinemaker1x.arierarobbery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.x1machinemaker1x.arierarobbery.events.BlockBreak;
import me.x1machinemaker1x.arierarobbery.events.InventoryClick;
import me.x1machinemaker1x.arierarobbery.events.PlayerDeath;
import me.x1machinemaker1x.arierarobbery.events.PlayerInteract;
import me.x1machinemaker1x.arierarobbery.events.PlayerMove;
import me.x1machinemaker1x.arierarobbery.utils.Commands;
import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;
import net.milkbowl.vault.economy.Economy;

public class ArieraRobbery extends JavaPlugin {
	
	public static WorldEditPlugin worldEdit;
	private static Economy econ = null;
	
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		getConfig().options().copyHeader(true);
		saveConfig();
		
		Configs.getInstance().setup(this);
		Messages.setup(this);
		Vaults.getInstance().setup(this);
		registerEvents();
		
		Commands cm = new Commands();
		cm.setup(this);
		getCommand("arierarobbery").setExecutor(cm);
		
		if (!setupEconomy() ) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		else {
			this.getLogger().info("Vault found! ArieraRobbery has been successfully hooked in!");
		}
		
		worldEdit = getWorldEdit();
		if (worldEdit == null) {
			this.getLogger().severe("WorldEdit not found! Disabling this plugin");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("WorldEdit found! ArieraRobbery has been successfully hooked in!");
	}
	
	private WorldEditPlugin getWorldEdit() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if ((plugin == null) || (!(plugin instanceof WorldEditPlugin))) return null;
		return (WorldEditPlugin) plugin;
	}
	
	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new InventoryClick(this), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new PlayerDeath(), this);
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public static Economy getEcon() {
		return econ;
	}
}