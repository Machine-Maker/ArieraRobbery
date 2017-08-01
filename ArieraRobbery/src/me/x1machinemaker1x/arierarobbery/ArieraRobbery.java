package me.x1machinemaker1x.arierarobbery;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.x1machinemaker1x.arierarobbery.events.BlockBreak;
import me.x1machinemaker1x.arierarobbery.events.InventoryClick;
import me.x1machinemaker1x.arierarobbery.events.PlayerInteract;
import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.objects.SignTimer;
import me.x1machinemaker1x.arierarobbery.utils.Commands;
import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class ArieraRobbery extends JavaPlugin {
	
	public static WorldEditPlugin worldEdit;
	
	private HashMap<UUID, SignTimer> signTimers;
	
	public void onEnable() {
		
		Configs.getInstance().setup(this);
		Messages.setup(this);
		Vaults.getInstance().setup(this);
		registerEvents();
		
		Commands cm = new Commands();
		cm.setup(this);
		getCommand("arierarobbery").setExecutor(cm);
		
		worldEdit = getWorldEdit();
		if (worldEdit == null) {
			this.getLogger().severe("WorldEdit not found! Disabling this plugin");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("WorldEdit found! ArieraJail has been successfully hooked in!");
		
		signTimers = new HashMap<UUID, SignTimer>();
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
	}
	
	public void addTimer(UUID playerUUID, BankVault vault) {
		signTimers.put(playerUUID, new SignTimer(playerUUID, this, vault));
		signTimers.get(playerUUID).runTaskTimer(this, 0L, 20L);
	}
	
	public void cancelSignTimer(UUID playerUUID) {
		signTimers.remove(playerUUID);
	}
}
