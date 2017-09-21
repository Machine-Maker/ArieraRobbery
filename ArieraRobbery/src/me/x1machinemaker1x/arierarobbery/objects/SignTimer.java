package me.x1machinemaker1x.arierarobbery.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierarobbery.utils.Messages;

public class SignTimer extends BukkitRunnable {
	
	private final Player player;
	private int count;
	private int maxTime;
	private BankVault vault;
	private final Plugin plugin;
	
	public SignTimer(UUID playerUUID, BankVault vault, Plugin plugin) {
		this.player = Bukkit.getPlayer(playerUUID);
		this.count = 0;
		this.vault = vault;
		this.maxTime = Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("time-must-stay-near-sign-seconds");
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if (count >= maxTime) {
			this.cancel();
			vault.toggleDoor(); //open door
			player.sendMessage(Messages.DOOR_OPENED.toString().replace("%minutes%", Messages.convertTime((long) (Configs.getInstance().getConfig(ConfigType.CONFIG).getDouble("time-must-stay-in-vault-minutes")*60*1000))).replace("%amount%", String.valueOf(Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("amount-to-get-when-robbed"))));
			vault.setVaultTimer(new VaultTimer(player.getUniqueId(), vault));
			vault.getVaultTimer().runTaskTimer(plugin, 0L, 20L);
			vault.setSignTimer(null);
		}
		else {
			if (player.getLocation().distanceSquared(vault.getSign()) > 25) {
				this.cancel();
				player.sendMessage(Messages.LEFT_SIGN.toString());
				vault.setSignTimer(null);
			}
			count ++;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
}
