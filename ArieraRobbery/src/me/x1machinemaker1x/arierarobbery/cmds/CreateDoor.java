package me.x1machinemaker1x.arierarobbery.cmds;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class CreateDoor extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		Block b = p.getTargetBlock((Set<Material>) null, 4);
		if (!b.getType().equals(Material.IRON_DOOR_BLOCK)) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_DOOR.toString());
			return;
		}
		if (args[1].length() != 4) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PASSCODE.toString());
			return;
		}
		boolean isNumbers = true;
		for (char c : args[1].toCharArray()) {
			if (!Character.isDigit(c)) isNumbers = false;
		}
		if (!isNumbers) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PASSCODE.toString());
			return;
		}
		List<Location> door = new ArrayList<Location>();
		door.add(b.getLocation());
		if (b.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.IRON_DOOR_BLOCK))
			door.add(b.getLocation().add(0, 1, 0));
		else
			door.add(b.getLocation().add(0, -1, 0));
		vault.setDoor(door, args[1]);
		Vaults.getInstance().saveVaults();
		p.sendMessage(Messages.PREFIX.toString() + Messages.DOOR_CREATED.toString().replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "createdoor";
	}
	
	public String info() {
		return "Creates a door for a vault";
	}
	
	public String permission() {
		return "arierarobbery.createdoor";
	}
	
	public String format() {
		return "/ar createdoor <vaultname> <passcode>";
	}
	
	public String[] aliases() {
		return new String[] { "cdoor", "cd" };
	}
	
	public int argsReq() {
		return 2;
	}
}
