package me.x1machinemaker1x.arierarobbery.cmds;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class DeleteSign extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		Block b = p.getTargetBlock((Set<Material>) null, 4);
		for (Location loc : vault.getDoor()) {
			if (loc.equals(b.getLocation())) {
				vault.setSign(null);
				Vaults.getInstance().saveVaults();
				return;
			}
		}
		p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_SIGN.toString());
	}
	
	public String name() {
		return "deletesign";
	}
	
	public String info() {
		return "Deletes a sign from a vault";
	}
	
	public String permission() {
		return "arierarobbery.deletesign";
	}
	
	public String format() {
		return "/ar deletesign <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "delsign", "ds" };
	}
	
	public int argsReq() {
		return 1;
	}
}
