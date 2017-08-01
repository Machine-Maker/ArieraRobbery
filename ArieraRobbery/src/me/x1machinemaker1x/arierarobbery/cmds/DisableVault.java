package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class DisableVault extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		if (vault.getTime() == Long.MAX_VALUE) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.ALREADY_STATE.toString().replace("%state%", "disabled"));
			return;
		}
		vault.enable();
		Vaults.getInstance().saveVaults();
		p.sendMessage(Messages.PREFIX.toString() + Messages.STATE_TOGGLE.toString().replace("%state%", "disabled").replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "disablevault";
	}
	
	public String info() {
		return "Enables a vault";
	}
	
	public String permission() {
		return "arierarobbery.disablevault";
	}
	
	public String format() {
		return "/ar disablevault <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "dvault", "disv" };
	}
	
	public int argsReq() {
		return 1;
	}
}
