package me.x1machinemaker1x.arierarobbery.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		for (BankVault vault : Vaults.getInstance().getVaults()) {
			if (vault.getSel().contains(e.getTo())) {
				if (vault.getVaultTimer() == null) {
					e.setTo(e.getFrom());
				}
				else if (!vault.getVaultTimer().getPlayer().getUniqueId().equals(e.getPlayer().getUniqueId())) {
					e.setTo(e.getFrom());
				}
			}
		}
	}
}