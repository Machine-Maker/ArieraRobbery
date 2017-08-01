package me.x1machinemaker1x.arierarobbery.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class PlayerInteract implements Listener {
	
	List<Integer> reservedSlots;
	ItemStack space;
	ItemStack number;
	
	public PlayerInteract() {
		reservedSlots = new ArrayList<Integer>();
		reservedSlots.add(31); reservedSlots.add(3); reservedSlots.add(4); reservedSlots.add(5); reservedSlots.add(12);
		reservedSlots.add(13); reservedSlots.add(14); reservedSlots.add(21); reservedSlots.add(22); reservedSlots.add(23);
		space = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta meta = space.getItemMeta();
		meta.setDisplayName(ChatColor.BLACK + "");
		meta.setLore(new ArrayList<String>());
		space.setItemMeta(meta);
		number = new ItemStack(Material.BLAZE_ROD, 1);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!Vaults.getInstance().isSign(e.getClickedBlock().getLocation())) return;
		if (!(e.getClickedBlock().getState() instanceof Sign)) {
			Vaults.getInstance().delSign(e.getClickedBlock().getLocation());
			return;
		}
		Sign sign = (Sign) e.getClickedBlock().getState();
		BankVault vault = Vaults.getInstance().getVault(sign.getLine(1).substring(sign.getLine(1).indexOf(":") + 4));
		if (!vault.isRobbable()) {
			e.getPlayer().sendMessage(Messages.NOT_ROBBABLE.toString());
			return;
		}
		Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_RED + "Passcode: " + ChatColor.GOLD + vault.getName());
		for (int i = 0; i < inv.getSize(); i++)
			if (!reservedSlots.contains(i))
				inv.setItem(i, space);
		for (int i = 0; i < 10; i++) {
			ItemMeta meta = number.getItemMeta();
			meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.GOLD + i);
			number.setItemMeta(meta);
			inv.setItem(reservedSlots.get(i), number);
		}
		e.getPlayer().openInventory(inv);
	}
}
