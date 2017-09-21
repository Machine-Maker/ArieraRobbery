package me.x1machinemaker1x.arierarobbery.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class PlayerDeath implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		for (BankVault vault : Vaults.getInstance().getVaults()) {
			if (vault.getSignTimer() != null) {
				try {
					vault.getSignTimer().cancel();
				} catch (Exception __) { }
				vault.setSignTimer(null);
			}
			if (vault.getVaultTimer() != null) {
				try {
					vault.getVaultTimer().cancel();
				} catch (Exception __) { }
				vault.setVaultTimer(null);
				vault.setBeingRobbed(false);
				vault.toggleDoor(); //close door
			}
		}
	}
}