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

import com.nepian.core.utils.Util;
import com.nepian.core.utils.sqlite.SQLite;
import com.nepian.home.utils.SQLiteUtil;
import com.nepian.home.utils.location.LocationUtil;

public class HomeManager {
	private static Map<UUID, Location> homes;
	private static SQLite sqlite;
	private static JavaPlugin plugin;

	public static void load(JavaPlugin javaPlugin) {
		homes = Util.newMap();
		plugin = javaPlugin;
		sqlite = new SQLite(plugin, "homes.db");
		
		read();
	}

	/**
	 * プレイヤーのホームを設定する
	 * @param player 対象のプレイヤー
	 * @throws SQLException ホーム設定の失敗
	 */
	public static void setHome(Player player) throws SQLException {
		UUID uuid = player.getUniqueId();
		Location location = player.getLocation();
		byte[] bytes = LocationUtil.getByteObject(location);
		PreparedStatement ps = null;
		
		if (hasHome(player)) {
			ps = sqlite.getPreparedStatement(SQLiteTokens.UPDATE);
			ps.setBytes(1, bytes);
		} else {
			ps = sqlite.getPreparedStatement(SQLiteTokens.ADD);
			ps.setString(1, uuid.toString());
			ps.setBytes(2, bytes);
		}
		ps.executeUpdate();
		homes.put(uuid, location);
	}
	
	/**
	 * ホームに設定しているLocationインスタンスを取得する
	 * @param player 対象のプレイヤー
	 * @return ホームが設定されていない場合nullを返す
	 */
	public static Location getHome(OfflinePlayer player) {
		return homes.get(player.getUniqueId());
	}

	/**
	 * SQLiteからホームを読み込む
	 */
	private static void read() {
		SQLiteUtil.executeUpdate(sqlite, SQLiteTokens.CREATE);
		try {
			PreparedStatement ps = sqlite.getPreparedStatement(SQLiteTokens.SELECT_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UUID uuid = UUID.fromString(rs.getString(1));
				Location location = LocationUtil.createLocation(rs.getBytes(2), plugin);
				homes.put(uuid, location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * プレイヤーがホームを設定しているか調べる
	 * @param player 対象のプレイヤー
	 * @return ホームを設定していない場合はfalseを返す
	 */
	private static boolean hasHome(OfflinePlayer player) {
		try {
			PreparedStatement ps = sqlite.getPreparedStatement(SQLiteTokens.SELECT_UUID);
			ps.setString(1, player.getUniqueId().toString());
			ResultSet results = ps.executeQuery();
			if (results.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
