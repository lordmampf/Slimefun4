package me.mrCookieSlime.Slimefun.Objects.tasks;

import java.util.UUID;

import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class JetpackTask implements Runnable {

	private UUID uuid;
	private double thrust;
	private int id;

	public JetpackTask(Player p, double thrust) {
		this.uuid = p.getUniqueId();
		this.thrust = thrust;
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
			float cost = 0.08F;
			float charge = ItemEnergy.getStoredEnergy(player.getInventory().getChestplate());
			if (charge >= cost) {
				player.getInventory().setChestplate(ItemEnergy.chargeItem(player.getInventory().getChestplate(), -cost));
				PlayerInventory.update(player);

				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, (float) 0.25, 1);
				player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 1, 1);
				player.setFallDistance(0.0f);
				Vector vector = new Vector(0, 1, 0);
				vector.multiply(thrust);
				vector.add(player.getEyeLocation().getDirection().multiply(0.2F));

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
