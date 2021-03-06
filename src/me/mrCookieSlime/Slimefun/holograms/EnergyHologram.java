package me.mrCookieSlime.Slimefun.holograms;

import me.mrCookieSlime.CSCoreLibPlugin.general.Math.DoubleHandler;
import me.mrCookieSlime.Slimefun.SlimefunStartup;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class EnergyHologram {
	
	public static void update(Block b, double supply, double demand) {
		update(b, demand > supply ? ("�4�l- �c" + DoubleHandler.getFancyDouble(Math.abs(supply - demand)) + " �7J �e\u26A1"): ("�2�l+ �a" + DoubleHandler.getFancyDouble(supply - demand) + " �7J �e\u26A1"));
	}
	
	public static void update(final Block b, final String name) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
			
			@Override
			public void run() {
				ArmorStand hologram = getArmorStand(b);
				hologram.setCustomName(name);
			}
		});
	}
	
	public static void remove(final Block b) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
			
			@Override
			public void run() {
				ArmorStand hologram = getArmorStand(b);
				hologram.remove();
			}
		});
	}
	
	private static ArmorStand getArmorStand(Block b) {
		Location l = new Location(b.getWorld(), b.getX() + 0.5, b.getY() - 0.7F, b.getZ() + 0.5);
		
		for (Entity n: l.getChunk().getEntities()) {
			if (n instanceof ArmorStand) {
				if (n.getCustomName() != null && l.distance(n.getLocation()) < 1) return (ArmorStand) n;
			}
		}
		
		ArmorStand hologram = ArmorStandFactory.createHidden(l);
		return hologram;
	}

}
