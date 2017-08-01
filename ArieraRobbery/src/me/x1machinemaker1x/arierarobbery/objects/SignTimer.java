package me.x1machinemaker1x.arierarobbery.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierarobbery.ArieraRobbery;
import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierarobbery.utils.Messages;

public class SignTimer extends BukkitRunnable {
	
	private final Player player;
	private final ArieraRobbery plugin;
	private int count;
	private int maxTime;
	private BankVault vault;
	
	public SignTimer(UUID playerUUID, ArieraRobbery plugin, BankVault vault) {
		this.player = Bukkit.getPlayer(playerUUID);
		this.plugin = plugin;
		this.count = 0;
		this.maxTime = Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("time-must-stay-near-sign-seconds");
	}
	
	@Override
	public void run() {
		if (count >= maxTime) {
			this.cancel();
			plugin.cancelSignTimer(player.getUniqueId());
			vault.toggleDoor(); //open door
		}
		if (player.getLocation().distanceSquared(vault.getSign()) > 25) {
			this.cancel();
			plugin.cancelSignTimer(player.getUniqueId());
			player.sendMessage(Messages.LEFT_SIGN.toString());
		}
		count ++;
	}
}
