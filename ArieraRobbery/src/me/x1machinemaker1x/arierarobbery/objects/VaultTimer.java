package me.x1machinemaker1x.arierarobbery.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierarobbery.ArieraRobbery;
import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultTimer extends BukkitRunnable {
	
	private final Player player;
	private final BankVault vault;
	private double count;
	private final double maxTime;
	private boolean doneRobbing;
	private final double amountToTake;
	
	public VaultTimer(UUID uuid, BankVault vault) {
		this.player = Bukkit.getPlayer(uuid);
		this.vault = vault;
		this.maxTime = Configs.getInstance().getConfig(ConfigType.CONFIG).getDouble("time-must-stay-in-vault-minutes")*60.0;
		this.count = 0;
		this.doneRobbing = false;
		this.amountToTake = Configs.getInstance().getConfig(ConfigType.CONFIG).getDouble("amount-to-get-when-robbed");
	}
	
	@Override
	public void run() {
		if (count >= maxTime) {
			player.sendMessage(Messages.VAULT_TIME_DONE.toString());
			vault.setBeingRobbed(false);
			vault.resetRobbable();
			Vaults.getInstance().saveVaults();
			doneRobbing = true;
			count = 0;
			double amountToGive = 0;
			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				EconomyResponse r = ArieraRobbery.getEcon().withdrawPlayer(p, amountToTake);
				if (r.transactionSuccess()) {
					if (p.isOnline()) {
						if (p.getName().equals(player.getName())) continue;
						((Player)p).sendMessage(Messages.MONEY_TAKEN.toString().replace("%amount%", ArieraRobbery.getEcon().format(r.amount)));
					}
					amountToGive += r.amount;
				}
			}
			EconomyResponse r = ArieraRobbery.getEcon().depositPlayer(player, amountToGive);
			if (r.transactionSuccess()) {
				player.sendMessage(Messages.MONEY_GIVEN.toString().replace("%amount%", ArieraRobbery.getEcon().format(r.amount)));
			}
		}
		else if (!doneRobbing) {
			if (vault.getSel().contains(player.getLocation())) {
				if (count == 0) {//Only runs the first second the task is active
					player.sendMessage(Messages.ENTERED_VAULT.toString().replace("%minutes%", Messages.convertTime((long) (Configs.getInstance().getConfig(ConfigType.CONFIG).getDouble("time-must-stay-in-vault-minutes")*60*1000))));
					vault.setBeingRobbed(true);
					Bukkit.getServer().broadcast(Messages.ROB_BROADCAST.toString().replace("%vaultname%", vault.getName()), "arierarobbery.notify");
				}
				count++;
				if (count % 60 == 0) {
					player.sendMessage(Messages.TIME_PASSED.toString().replace("%time%", Messages.convertTime((long) (count*1000))));
				}
			}
			else if (count != 0) {
				player.sendMessage(Messages.LEFT_VAULT.toString());
				vault.toggleDoor();
				vault.setBeingRobbed(false);
				vault.setVaultTimer(null);
				this.cancel();
			}
		}
		else {
			if (!vault.getSel().contains(player.getLocation())) {
				vault.toggleDoor();
				vault.setVaultTimer(null);
				this.cancel();
			}
		}
	}
	
	public double getCount() {
		return count;
	}
	
	public Player getPlayer() {
		return player;
	}
}