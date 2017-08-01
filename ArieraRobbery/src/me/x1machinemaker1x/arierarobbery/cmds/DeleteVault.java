package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class DeleteVault extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		Vaults.getInstance().delVault(vault);
		p.sendMessage(Messages.PREFIX.toString() + Messages.VAULT_DELETED.toString().replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "deletevault";
	}
	
	public String info() {
		return "Deletes a vault";
	}
	
	public String permission() {
		return "arierarobbery.deletevault";
	}
	
	public String format() {
		return "/ar deletevault <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "delvault", "dv" };
	}
	
	public int argsReq() {
		return 1;
	}

}
