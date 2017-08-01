package me.x1machinemaker1x.arierarobbery.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.x1machinemaker1x.arierarobbery.ArieraRobbery;
import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

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
			plugin.addTimer(e.getWhoClicked().getUniqueId(), vault);
		    e.getWhoClicked().closeInventory();
		    //send message code was correct. vault door opening. you must stay within 5 blocks area for %time% seconds
			vault.setEnteredPasscode("$$$$");
		}
		else {
			vault.setEnteredPasscode(enteredPasscode);
		}
	}

}
