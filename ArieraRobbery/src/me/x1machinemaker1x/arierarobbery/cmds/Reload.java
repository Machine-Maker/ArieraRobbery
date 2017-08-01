package me.x1machinemaker1x.arierarobbery.cmds;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierarobbery.utils.Messages;
import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class Reload extends SubCommand {
	
	private final Plugin plugin;
	
	public Reload(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void onCommand(Player p, String[] args) {
		switch (args[0]) {
		case "config":
			Configs.getInstance().reloadConfig(ConfigType.CONFIG);
			break;
		case "messages":
			Messages.reloadMessages();
			Messages.setup(plugin);
			break;
		case "vaults":
			Vaults.getInstance().saveVaults();
			Configs.getInstance().reloadConfig(ConfigType.VAULTS);
			Vaults.getInstance().setup(plugin);
			break;
		case "all":
			Configs.getInstance().reloadConfig(ConfigType.CONFIG);
			Messages.reloadMessages();
			Messages.setup(plugin);
			Vaults.getInstance().saveVaults();
			Configs.getInstance().reloadConfig(ConfigType.VAULTS);
			Vaults.getInstance().setup(plugin);
			break;
		default:
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CONFIGURATION.toString());
			return;
		}
		p.sendMessage(Messages.PREFIX.toString() + Messages.CONFIG_RELOADED.toString()
			.replace("%filename%", (args[0].equals("all")) ? "All files": args[0] + ".yml")
			.replace("%has/have%", (args[0].equals("all") ? "have" : "has")));
	}
	
	public String name() {
		return "reload";
	}
	
	public String info() {
		return "Reloads configurations";
	}
	
	public String permission() {
		return "arierarobbery.reload";
	}
	
	public String format() {
		return "/ar reload <config|messages|vaults|all>";
	}
	
	public String[] aliases() {
		return new String[] { "rel", "r" };
	}
	
	public int argsReq() {
		return 1;
	}
}
