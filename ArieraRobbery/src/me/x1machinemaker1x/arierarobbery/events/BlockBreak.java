package me.x1machinemaker1x.arierarobbery.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Door;

import me.x1machinemaker1x.arierarobbery.utils.Vaults;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getState() instanceof Sign) {
			if (!Vaults.getInstance().isSign(e.getBlock().getLocation())) return;
			e.setCancelled(true);
		}
		else if (e.getBlock().getState().getData() instanceof Door) {
			if (!Vaults.getInstance().isDoor(e.getBlock().getLocation())) return;
			e.setCancelled(true);
		}
	}
}
