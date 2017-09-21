package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class CheckTime extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		for (BankVault vault : Vaults.getInstance().getVaults()) {
			if (!vault.isBeingRobbed()) continue;
			if (vault.getSel().contains(p.getLocation())) {
				p.sendMessage(Messages.TIME_PASSED.toString().replace("%time%", Messages.convertTime((long) (vault.getVaultTimer().getCount()*1000))));
				return;
			}
		}
		p.sendMessage(Messages.NOT_IN_VAULT.toString());
	}
	
	public String name() {
		return "checktime";
	}
	
	public String info() {
		return "Checks the time in your ongoing robbery";
	}
	
	public String permission() {
		return "arierarobbery.checktime";
	}
	
	public String format() {
		return "/ar checktime";
	}
	
	public String[] aliases() {
		return new String[] { "ctime", "ct" };
	}
	
	public int argsReq() {
		return 0;
	}
}