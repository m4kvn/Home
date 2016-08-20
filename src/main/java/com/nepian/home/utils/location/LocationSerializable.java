package com.nepian.home.utils.location;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationSerializable implements Serializable {
	private static final long serialVersionUID = 1L;
	private UUID worldUid;
	private int x;
	private int y;
	private int z;
	private float yaw;
	private float pitch;

	public LocationSerializable(Location location) {
		this.worldUid = location.getWorld().getUID();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	
	public Location getLocation(JavaPlugin plugin) {
		World world = plugin.getServer().getWorld(worldUid);
		return new Location(world, x, y, z, yaw, pitch);
	}
}
