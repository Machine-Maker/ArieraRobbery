package me.x1machinemaker1x.arierarobbery.utils;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public enum Messages {
	
	PREFIX("prefix", "&4[&7ArieraJails&4] &e"),
	NO_PERMISSION("no-permission", "&cYou do not have permission!"),
	NOT_PLAYER("not-player", "&cYou must be a player to use that command!"),
	NOT_COMMAND("not-command", "&cThat is not a valid command!"),
	USE_FORMAT("use-format", "&eUse format: %format%"),
	BAD_NAME("bad-name", "&cThat name is already in use!"),
	NO_SELECTION("no-selection", "&cYou must have an area selected!"),
	NO_DOOR_FOUND("no-door-found", "&cNo door was found for this vault!"),
	NOT_CUBOID("not-cuboid", "&cThe selection must be a cuboid!"),
	NOT_VAULT("not-vault", "&cThat is not a valid vault!"),
	NOT_DOOR("not-door", "&cThat is not an iron door!"),
	NOT_PASSCODE("not-number", "&cThat is not a valid passcode. It must be 4 numbers!"),
	NOT_SIGN("not-sign", "&cThat is not a sign block!"),
	
	VAULT_CREATED("vault-created", "&aYou have created the vault %vaultname%!"),
	VAULT_DELETED("vault-deleted", "&aYou have deleted the vault %vaultname%!"),
	DOOR_CREATED("door-created", "&aYou have created a door for vault %vaultname%!"),
	DOOR_DELETED("door-deleted", "&aYou have deleted the door for vault %vaultname%!"),
	SIGN_CREATED("sign-created", "&aYou have created a sign for %vaultname%!"),
	
	NOT_ROBBABLE("not-robbable", "&6This vault is not robbable right now!"),
	ALREADY_STATE("already-state", "&cThat vault is already %state%!"),
	STATE_TOGGLE("state-toggle", "&aYou have %state% vault %vaultname%!"),
	
	LEFT_SIGN("left-sign", "&cYou have left the sign area and must unlock the door again!"),
	
	DOOR_OPENED("door-opened", "&2The vault door was opened! Once you step inside the vault, you have to stay for %minutes% minutes to get $%amount% from every player!"),
	ENTERED_VAULT("entered-vault", "&2You have entered the vault. Your %minutes% minutes starts now!"),
	MINUTES_PASSED("minutes-passed", "&a%minutes% minutes have elapsed!"),
	VAULT_TIME_DONE("vault-time-done", "&aYour time is done! You can leave now."),
	
	CONFIG_RELOADED("config-reloaded", "&a%filename% %has/have% been reloaded!"),
	NOT_CONFIGURATION("not-configuration", "&cThat is not a valid configuration!");
		
	String path;
	String def;
	
	private Messages(String path, String def) {
		this.path = path;
		this.def = def;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getDefault() {
		return this.def;
	}
	
	public void setMessage(String message) {
		this.def = message;
	}
	
	private static File mfile;
	private static FileConfiguration mconfig;
	public static void setup(Plugin plugin) {
		mfile = new File(plugin.getDataFolder(), "messages.yml");
		if (!mfile.exists()) {
			try {
				plugin.getDataFolder().mkdir();
				mfile.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Could not create messages.yml!");
			}
		}
		mconfig = YamlConfiguration.loadConfiguration(mfile);
		for (Messages message : Messages.values())
			if (!mconfig.isSet(message.getPath()))
				mconfig.set(message.getPath(), message.getDefault());
			else
				if (!mconfig.getString(message.getPath()).equals(message.getDefault()))
					message.setMessage(mconfig.getString(message.getPath()));
		try {
			mconfig.save(mfile);
		} catch (Exception e) {
			plugin.getLogger().severe("Could not save messages.yml");
		}
	}
	
	public static void reloadMessages() {
		mconfig = YamlConfiguration.loadConfiguration(mfile);
	}
	
	@Override
	public String toString() {
		return ChatColor.translateAlternateColorCodes('&', this.getDefault());
	}
}
