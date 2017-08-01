package me.x1machinemaker1x.arierarobbery.utils;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configs {
	
	private Configs() { }
	
	private static Configs instance = new Configs();
	
	public static Configs getInstance() {
		return instance;
	}
	
	private HashMap<File, FileConfiguration> configs;
	
	public void setup(Plugin plugin) {
		configs = new HashMap<File, FileConfiguration>();
		configs.put(new File(plugin.getDataFolder(), "config.yml"), null);
		configs.put(new File(plugin.getDataFolder(), "vaults.yml"), null);
		for (File f : configs.keySet()) {
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (Exception e) {
					plugin.getLogger().severe("Could not create " + f.getName() + "!");
				}
			}
			if (f.getName().equals("config.yml"))
				ConfigType.CONFIG.setFile(f);
			else if (f.getName().equals("vaults.yml"))
				ConfigType.VAULTS.setFile(f);
			configs.put(f, YamlConfiguration.loadConfiguration(f));
		}
	}
	
	public FileConfiguration getConfig(ConfigType type) {
		return configs.get(type.getFile());
	}
	
	public void reloadConfig(ConfigType type) {
		configs.put(type.getFile(), YamlConfiguration.loadConfiguration(type.getFile()));
	}
	
	public void saveConfig(ConfigType type) {
		try {
			configs.get(type.getFile()).save(type.getFile());
		} catch (Exception e) {
			Bukkit.getLogger().severe("[ArieraJail] Could not save " + type.getFile().getName() + "!");
		}
	}
	
	public enum ConfigType {
		CONFIG,
		VAULTS;
		
		File f;
		
		public File getFile() {
			return this.f;
		}
		
		public void setFile(File f) {
			this.f = f;
		}
	}
}
