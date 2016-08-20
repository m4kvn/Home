package com.nepian.home;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.core.utils.LocationUtil;
import com.nepian.core.utils.Util;
import com.nepian.core.utils.sqlite.SQLite;

public class HomeManager {
	private static Map<UUID, Location> homes;
	private static SQLite sqlite;
	private static JavaPlugin plugin;

	public static void load(JavaPlugin javaPlugin) {
		homes = Util.newHashMap();
		plugin = javaPlugin;
		sqlite = new SQLite(plugin, "homes.db");
		
		read();
	}
	
	public static void close() {
		if (sqlite != null) {
			sqlite.close();
		}
	}

	public static void setHome(Player player) throws SQLException {
		UUID uuid = player.getUniqueId();
		Location location = player.getLocation();
		byte[] bytes = LocationUtil.asByteArray(location);
		PreparedStatement ps = null;
		
		if (hasHome(uuid)) {
			ps = sqlite.getPreparedStatement(SQLiteTokens.UPDATE);
			ps.setBytes(1, bytes);
			ps.setString(2, uuid.toString());
		} else {
			ps = sqlite.getPreparedStatement(SQLiteTokens.ADD);
			ps.setString(1, uuid.toString());
			ps.setBytes(2, bytes);
		}
		ps.executeUpdate();
		
		homes.put(uuid, location);
	}
	
	public static Location getHome(OfflinePlayer player) {
		return homes.get(player.getUniqueId());
	}
	
	public static boolean removeHome(OfflinePlayer player) throws SQLException {
		UUID uuid = player.getUniqueId();
		
		if (hasHome(uuid)) {
			PreparedStatement ps = sqlite.getPreparedStatement(SQLiteTokens.REMOVE);
			
			ps.setString(1, uuid.toString());
			ps.executeUpdate();
			homes.remove(uuid);
			
			return true;
		}
		
		return false;
	}

	private static void read() {
		sqlite.executeUpdate(SQLiteTokens.CREATE);
		try {
			PreparedStatement ps = sqlite.getPreparedStatement(SQLiteTokens.SELECT_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UUID uuid = UUID.fromString(rs.getString(1));
				byte[] bytes = rs.getBytes(2);
				Location location = LocationUtil.fromByteArray(bytes);
				homes.put(uuid, location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static boolean hasHome(UUID uuid) {
		return homes.containsKey(uuid);
	}
}
