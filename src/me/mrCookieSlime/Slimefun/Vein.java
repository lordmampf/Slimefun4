package me.mrCookieSlime.Slimefun;

import java.util.List;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Vein {
	private static final BlockFace[] faces = { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH_EAST,
			BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };

	public static void calculate(Location origin, Location anchor, Predicate<Material> type, List<Location> list, int max) {
		if (list.size() > max)
			return;
		BlockFace[] arrayOfBlockFace;
		int j = (arrayOfBlockFace = faces).length;
		for (int i = 0; i < j; i++) {
			BlockFace face = arrayOfBlockFace[i];
			Block next = anchor.getBlock().getRelative(face);
			if (type.test(next.getType()) && !list.contains(next.getLocation())) {
				list.add(next.getLocation());
				calculate(origin, next.getLocation(), type, list, max);
			}
		}
	}
}
