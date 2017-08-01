package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class EnableVault extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		if (vault.getTime() != Long.MAX_VALUE) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.ALREADY_STATE.toString().replace("%state%", "enabled"));
			return;
		}
		vault.enable();
		Vaults.getInstance().saveVaults();
		p.sendMessage(Messages.PREFIX.toString() + Messages.STATE_TOGGLE.toString().replace("%state%", "enabled").replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "enablevault";
	}
	
	public String info() {
		return "Enables a vault";
	}
	
	public String permission() {
		return "arierarobbery.enablevault";
	}
	
	public String format() {
		return "/ar enablevault <vaultname>";
	}
	
	public String[] aliases() {
		return new String[] { "evault", "ev" };
	}
	
	public int argsReq() {
		return 1;
	}
}
