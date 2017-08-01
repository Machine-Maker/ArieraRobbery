package me.x1machinemaker1x.arierarobbery.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.x1machinemaker1x.arierarobbery.cmds.CreateDoor;
import me.x1machinemaker1x.arierarobbery.cmds.CreateSign;
import me.x1machinemaker1x.arierarobbery.cmds.CreateVault;
import me.x1machinemaker1x.arierarobbery.cmds.DeleteDoor;
import me.x1machinemaker1x.arierarobbery.cmds.DeleteSign;
import me.x1machinemaker1x.arierarobbery.cmds.DeleteVault;
import me.x1machinemaker1x.arierarobbery.cmds.DisableVault;
import me.x1machinemaker1x.arierarobbery.cmds.EnableVault;
import me.x1machinemaker1x.arierarobbery.cmds.Reload;
import me.x1machinemaker1x.arierarobbery.cmds.SubCommand;


public class Commands implements CommandExecutor {
	private List<SubCommand> commands = new ArrayList<SubCommand>();

	public void setup(Plugin plugin) {
		commands.add(new CreateVault());
		commands.add(new DeleteVault());
		commands.add(new CreateDoor());
		commands.add(new DeleteDoor());
		commands.add(new CreateSign());
		commands.add(new DeleteSign());
		commands.add(new EnableVault());
		commands.add(new DisableVault());
		commands.add(new Reload(plugin));
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PLAYER.toString());
			return true;
		}
		Player p = (Player) cs;
		if (args.length == 0) {
			for (SubCommand c : this.commands) {
				p.sendMessage(Messages.PREFIX.toString() + c.format() + " (" + aliases(c) + ") - " + c.info());
			}
			return true;
		}
		SubCommand command = get(args[0]);
		if (command == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_COMMAND.toString());
			return true;
		}
		if (!p.hasPermission(command.permission())) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
			return true;
		}
		List<String> a = new ArrayList<String>();
		a.addAll(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		if (args.length != command.argsReq()) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.USE_FORMAT.toString().replace("%format%", command.format()));
			return true;
		}
		try {
			command.onCommand(p, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private String aliases(SubCommand cmd) {
		String fin = "";
		for (String a : cmd.aliases()) {
			fin += a + " | ";
		}
		return fin.substring(0, fin.lastIndexOf(" | "));
	}

	private SubCommand get(String name) {
		for (SubCommand c : commands) {
			if (c.name().equalsIgnoreCase(name))
				return c;
			for (String alias : c.aliases())
				if (name.equalsIgnoreCase(alias))
					return c;
		}
		return null;
	}
}
