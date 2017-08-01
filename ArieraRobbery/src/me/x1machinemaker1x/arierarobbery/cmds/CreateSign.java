package me.x1machinemaker1x.arierarobbery.cmds;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class CreateSign extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		BankVault vault = Vaults.getInstance().getVault(args[0]);
		if (vault == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_VAULT.toString());
			return;
		}
		Block b = p.getTargetBlock((Set<Material>) null, 4);
		if (!(b.getState() instanceof Sign)) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_SIGN.toString());
			return;
		}
		Sign s = (Sign) b.getState();
		s.setLine(0, "§4[§1ArieraRobbery§4]");
		s.setLine(1, "§cVault: §r" + args[0]);
		s.setLine(2, "");
		s.setLine(3, "");
		s.update();
		vault.setSign(b.getLocation());
		Vaults.getInstance().saveVaults();
		p.sendMessage(Messages.PREFIX.toString() + Messages.SIGN_CREATED.toString().replace("%vaultname%", args[0]));
	}
	
	public String name() {
		return "createsign";
	}
	
	public String info() {
		return "Creates a sign for a vault";
	}
	
	public String permission() {
		return "arierarobbery.createsign";
	}
	
	public String format() {
		return "/ar createsign <vault>";
	}
	
	public String[] aliases() {
		return new String[] { "csign", "cs" };
	}
	
	public int argsReq() {
		return 1;
	}
}
