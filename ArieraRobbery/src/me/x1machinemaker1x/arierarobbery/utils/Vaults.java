package me.x1machinemaker1x.arierarobbery.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.arierarobbery.objects.BankVault;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;

public class Vaults {
	
	private Vaults() { }
	
	private static Vaults instance = new Vaults();
	
	public static Vaults getInstance() {
		return instance;
	}
	
	private List<BankVault> vaults;
	
	@SuppressWarnings("unchecked")
	public void setup(Plugin plugin) {
		vaults = new ArrayList<BankVault>();
		List<Map<?,?>> vaultMap = Configs.getInstance().getConfig(ConfigType.VAULTS).getMapList("Vaults");
		if (vaultMap == null) {
			plugin.getLogger().info("No vaults were found in vaults.yml so no vaults were loaded in!");
			return;
		}
		for (Map<?, ?> map : vaultMap) {
			vaults.add(BankVault.deserialize((Map<String, Object>) map));
		}
		plugin.getLogger().info((vaults.size() == 1) ? "1 vault was found and successfully loaded in!" : vaults.size() + " vaults were found and successfully loaded in!");
	}
	
	public void saveVaults() {
		Configs.getInstance().getConfig(ConfigType.VAULTS).set("Vaults", null);
		Configs.getInstance().saveConfig(ConfigType.VAULTS);
		if (vaults.isEmpty()) return;
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		for (BankVault vault : vaults) {
			map.add(vault.serialize());
		}
		Configs.getInstance().getConfig(ConfigType.VAULTS).set("Vaults", map);
		Configs.getInstance().saveConfig(ConfigType.VAULTS);
	}
	
	public void addVault(String name, CuboidSelection sel) {
		vaults.add(new BankVault(name, sel));
		saveVaults();
	}
	
	public void delVault(BankVault vault) {
		vaults.remove(vault);
		saveVaults();
	}
	
	public BankVault getVault(String name) {
		if (vaults.isEmpty()) return null;
		for (BankVault vault : vaults)
			if (vault.getName().equals(name))
				return vault;
		return null;
	}
	
	public boolean isSign(Location loc) {
		if (vaults.isEmpty()) return false;
		for (BankVault vault : vaults) {
			if (vault.getSign() == null) continue;
			if (vault.getSign().equals(loc)) return true;
		}
		return false;
	}
	
	public boolean isDoor(Location loc) {
		if (vaults.isEmpty()) return false;
		for (BankVault vault : vaults) 
			if (vault.getDoor().contains(loc)) return true;
		return false;
	}
	
	public void delSign(Location loc) {
		for (BankVault vault : vaults)
			if (vault.getSign().equals(loc))
				vault.setSign(null);
	}
}
