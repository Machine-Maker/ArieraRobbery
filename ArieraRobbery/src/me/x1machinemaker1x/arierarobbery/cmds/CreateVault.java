package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.x1machinemaker1x.arierarobbery.ArieraRobbery;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class CreateVault extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Selection sel = ArieraRobbery.worldEdit.getSelection(p);
		if (sel == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_SELECTION.toString());
			return;
		}
		if (!(sel instanceof CuboidSelection)) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CUBOID.toString());
			return;
		}
		if (Vaults.getInstance().getVault(args[0]) != null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.BAD_NAME.toString());
			return;
		}
		CuboidSelection cSel = (CuboidSelection) sel;
		String name = args[0];
		Vaults.getInstance().addVault(name, cSel);
		p.sendMessage(Messages.PREFIX.toString() + Messages.VAULT_CREATED.toString().replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "createvault";
	}
	
	public String info() {
		return "Creates a bank vault";
	}
	
	public String permission() {
		return "arierarobbery.createvault";
	}
	
	public String format() {
		return "/ar createvault <name>";
	}
	
	public String[] aliases() {
		return new String[] { "cvault", "cv" };
	}
	
	public int argsReq() {
		return 1;
	}

}
