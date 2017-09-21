package me.x1machinemaker1x.arierarobbery.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.arierarobbery.utils.Configs;
import me.x1machinemaker1x.arierarobbery.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierarobbery.utils.Serialize;

public class BankVault implements ConfigurationSerializable {
	
	String name = null;
	CuboidSelection sel = null;
	List<Location> door = null;
	String passcode = null;
	String enteredPasscode = null;
	Location sign = null;
	Boolean beingRobbed = false;
	Long timeBeforeCanBeRobbed = null;
	VaultTimer vTimer = null;
	SignTimer sTimer = null;
	
	public BankVault(String name, CuboidSelection sel, List<Location> door, String passcode, Location sign, boolean enable, Long timeBeforeCanBeRobbed) {
		this.name = name;
		this.sel = sel;
		if (door == null) {
			this.door = new ArrayList<Location>();
			this.door.add(null);
			this.door.add(null);
		}
		else
			this.door = door;
		this.passcode = passcode;
		this.enteredPasscode = "$$$$";
		this.sign = sign;
		if (enable)
			this.timeBeforeCanBeRobbed = timeBeforeCanBeRobbed;
		else
			this.timeBeforeCanBeRobbed = Long.MAX_VALUE;
	}
	
	public BankVault(String name, CuboidSelection sel) {
		this(name, sel, null, null, null, false, null);
	}
	
	public String getName() {
		return name;
	}
	
	public CuboidSelection getSel() {
		return sel;
	}
	
	public List<Location> getDoor() {
		return door;
	}
	
	public void setDoor(List<Location> door, String passcode) {
		this.door = door;
		this.passcode = passcode;
	}
	
	public void delDoor() {
		List<Location> door = new ArrayList<Location>();
		door.add(null);
		door.add(null);
		this.door = door;
		this.passcode = null;
	}
	
	public String getPasscode()	{
		return passcode;
	}
	
	public String getEnteredPasscode() {
		return enteredPasscode;
	}
	
	public void setEnteredPasscode(String enteredPasscode) {
		this.enteredPasscode = enteredPasscode;
	}
	
	public Location getSign() {
		return sign;
	}
	
	public void setSign(Location sign) {
		this.sign = sign;
	}
	
	public Long getTime() {
		return timeBeforeCanBeRobbed;
	}
	
	public Boolean isRobbable() {
		return System.currentTimeMillis() > this.timeBeforeCanBeRobbed && this.sign != null && !this.door.contains(null) && !this.beingRobbed;
	}
	
	public void resetRobbable() {
		this.timeBeforeCanBeRobbed = System.currentTimeMillis() + (1000 * 60 * Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("time-between-robs-minutes"));
	}
	
	public Boolean isBeingRobbed() {
		return beingRobbed;
	}
	
	public void setBeingRobbed(Boolean beingRobbed) {
		this.beingRobbed = beingRobbed;
	}
	
	public void enable() {
		this.timeBeforeCanBeRobbed = System.currentTimeMillis();
	}
	
	public void disable() {
		this.timeBeforeCanBeRobbed = Long.MAX_VALUE;
	}
	
	public void setVaultTimer(VaultTimer timer) {
		this.vTimer = timer;
	}
	
	public VaultTimer getVaultTimer() {
		return this.vTimer;
	}
	
	public void setSignTimer(SignTimer timer) {
		this.sTimer = timer;
	}
	
	public SignTimer getSignTimer() {
		return this.sTimer;
	}
	
	public void toggleDoor() {
		Block block = this.getDoor().get(0).getBlock();
		BlockState state = block.getState();
		MaterialData data = state.getData();
		if (data instanceof Door) {
	        Door door = (Door) data;
	        if (door.isTopHalf()) {
	            block = block.getRelative(BlockFace.DOWN);
	            state = block.getState();
	            data = state.getData();
	            door = (Door) data;
	        }
	        door.setOpen(!door.isOpen());
	        state.update(true);
		}
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", this.name);
		map.put("minPoint", Serialize.locationToBase64(this.sel.getMinimumPoint()));
		map.put("maxPoint", Serialize.locationToBase64(this.sel.getMaximumPoint()));
		List<String> doorList = new ArrayList<String>();
		Iterator<Location> iLoc = this.door.iterator();
		while (iLoc.hasNext()) {
			doorList.add(Serialize.locationToBase64(iLoc.next()));
		}
		map.put("door", doorList);
		map.put("passcode", passcode);
		map.put("sign", Serialize.locationToBase64(this.sign));
		map.put("timeBeforeCanBeRobbed", this.timeBeforeCanBeRobbed);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static BankVault deserialize(Map<String, Object> map) {
		String name = String.valueOf(map.get("name"));
		Location minPoint = Serialize.base64ToLocation(String.valueOf(map.get("minPoint")));
		Location maxPoint = Serialize.base64ToLocation(String.valueOf(map.get("maxPoint")));
		CuboidSelection sel = new CuboidSelection(minPoint.getWorld(), minPoint, maxPoint);
		boolean enable = true;
		List<Location> door = new ArrayList<Location>();
		for (Object obj: (List<Object>) map.get("door"))
			door.add(Serialize.base64ToLocation(String.valueOf(obj)));
		if (door.get(0) == null || door.get(1) == null) enable = false;
		String passcode = (map.get("passcode") == null) ? null : String.valueOf(map.get("passcode"));
		Location sign = Serialize.base64ToLocation(String.valueOf(map.get("sign")));
		if (sign == null) enable = false;
		Long timeBeforeCanBeRobbed = Long.valueOf(String.valueOf(map.get("timeBeforeCanBeRobbed")));
		return new BankVault(name, sel, door, passcode, sign, enable, timeBeforeCanBeRobbed);
	}
	
}
