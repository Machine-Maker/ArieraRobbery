package me.x1machinemaker1x.arierarobbery.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.x1machinemaker1x.arierarobbery.ArieraRobbery;
import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.objects.SignTimer;
import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;

public class InventoryClick implements Listener {
	
	private ArieraRobbery plugin;
	
	public InventoryClick(ArieraRobbery plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getRawSlot() != e.getSlot()) return;
		if (e.getInventory().getHolder() != null) return;
		if (!e.getInventory().getName().contains(ChatColor.DARK_RED + "Passcode")) return;
		e.setCancelled(true);
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR);
		if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
		String numberClicked = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
		BankVault vault = Vaults.getInstance().getVault(e.getInventory().getName().substring(e.getInventory().getName().indexOf(":") + 4));
		String enteredPasscode = vault.getEnteredPasscode().substring(1) + numberClicked;
		if (enteredPasscode.equals(vault.getPasscode())) { //Passcode is correct
			if (vault.getSignTimer() != null || vault.getVaultTimer() != null) {
				e.getWhoClicked().closeInventory();
				e.getWhoClicked().sendMessage(Messages.NOT_ROBBABLE.toString());
				vault.setEnteredPasscode("$$$$");
			}
			else {
				vault.setSignTimer(new SignTimer(e.getWhoClicked().getUniqueId(), vault, plugin));
				vault.getSignTimer().runTaskTimer(plugin, 0L, 20L);
			    e.getWhoClicked().closeInventory();
			    e.getWhoClicked().sendMessage(Messages.PASSCODE_CORRECT.toString().replace("%seconds%", Messages.convertTime(Configs.getInstance().getConfig(ConfigType.CONFIG).getLong("time-must-stay-near-sign-seconds")*1000)));
				vault.setEnteredPasscode("$$$$");
			}
		}
		else {
			vault.setEnteredPasscode(enteredPasscode);
		}
	}

}
