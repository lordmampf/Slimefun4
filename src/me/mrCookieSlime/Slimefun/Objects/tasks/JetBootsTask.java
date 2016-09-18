package me.mrCookieSlime.Slimefun.Objects.tasks;

import java.text.DecimalFormat;
import java.util.UUID;

import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class JetBootsTask implements Runnable {

	private UUID uuid;
	private double speed;
	private int id;

	public JetBootsTask(Player p, double speed) {
		this.uuid = p.getUniqueId();
		this.speed = speed;
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
			float cost = 0.075F;
			float charge = ItemEnergy.getStoredEnergy(player.getInventory().getBoots());
			double accuracy = Double.valueOf(new DecimalFormat("##.##").format(speed - 0.7).replace(",", "."));
			if (charge >= cost) {
				player.getInventory().setBoots(ItemEnergy.chargeItem(player.getInventory().getBoots(), -cost));
				PlayerInventory.update(player);

				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, (float) 0.25, 1);
				player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 1, 1);
				player.setFallDistance(0.0f);
				double gravity = 0.04;
				double offset = SlimefunStartup.chance(100, 50) ? accuracy : -accuracy;
				Vector vector = new Vector(player.getEyeLocation().getDirection().getX() * speed + offset, gravity,
						player.getEyeLocation().getDirection().getZ() * speed - offset);

				player.setVelocity(vector);
			} else
				cancel(player);
		}
	}

	private void cancel(Player pPlayer) {
		if (pPlayer != null)
			Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, () -> pPlayer.setAllowFlight(false), 20L * 30);
		Bukkit.getScheduler().cancelTask(id);
	}

}
