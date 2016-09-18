package me.mrCookieSlime.Slimefun.Objects.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.mrCookieSlime.Slimefun.SlimefunStartup;

public class ParachuteTask implements Runnable {

	private UUID uuid;
	private int id;

	public ParachuteTask(Player p) {
		this.uuid = p.getUniqueId();
		p.setAllowFlight(true);
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		Player player = Bukkit.getPlayer(uuid);
		if (player == null)
			cancel(player);
		else if (player.isDead())
			cancel(player);
		else if (!player.isSneaking())
			cancel(player);
		else {
			Vector vector = new Vector(0, 1, 0);
			vector.multiply(-0.1);
			player.setVelocity(vector);
			player.setFallDistance(0.0f);
			if (!player.isSneaking())
				cancel(player);
		}
	}

	private void cancel(Player pPlayer) {
		if (pPlayer != null)
			Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, () -> pPlayer.setAllowFlight(false), 20L * 30);
		Bukkit.getScheduler().cancelTask(id);
	}

}
