package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class VaultCheck extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		if (vault.isRobbable()) {
			p.sendMessage(Messages.ROBBABLE.toString());
		}
		else {
			p.sendMessage(Messages.NOT_ROBBABLE.toString());
			p.sendMessage(Messages.TIME_LEFT.toString().replace("%time%", Messages.convertTime(vault.getTime() - System.currentTimeMillis())));
		}
	}
	
	public String name() {
		return "check";
	}
	
	public String info() {
		return "Checks if a vault is robbable";
	}
	
	public String permission() {
		return "arierarobbery.check";
	}
	
	public String format() {
		return "/ar check <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "c" };
	}
	
	public int argsReq() {
		return 1;
	}
}