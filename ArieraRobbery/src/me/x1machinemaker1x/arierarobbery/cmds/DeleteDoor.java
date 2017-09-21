package me.x1machinemaker1x.arierarobbery.cmds;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class DeleteDoor extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		Block b = p.getTargetBlock((Set<Material>) null, 4);
		List<Location> door = vault.getDoor();
		boolean deletedDoor = false;
		for (Location loc : door) {
			if (loc == null) {
				p.sendMessage(Messages.PREFIX.toString() + Messages.NO_DOOR.toString());
				return;
			}
			if (loc.equals(b.getLocation())) {
				vault.delDoor();
				Vaults.getInstance().saveVaults();
				p.sendMessage(Messages.PREFIX.toString() + Messages.DOOR_DELETED.toString().replace("%vaultname%", args[0]));
				deletedDoor = true;
			}
		}
		if (deletedDoor) {
			for (Location loc : door) {
				loc.getBlock().setType(Material.AIR);
			}
		}
		else {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_DOOR.toString());
		}
	}
	
	public String name() {
		return "deletedoor";
	}
	
	public String info() {
		return "Deletes a door from a vault";
	}
	
	public String permission() {
		return "arierarobbery.deletedoor";
	}
	
	public String format() {
		return "/ar deletedoor <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "deldoor", "dd" };
	}
	
	public int argsReq() {
		return 1;
	}
}
