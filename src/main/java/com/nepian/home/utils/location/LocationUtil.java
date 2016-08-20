package com.nepian.home.utils.location;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationUtil {

	/**
	 * Locationをのバイト配列で取得する
	 * @param location 対象のロケーション
	 * @return Locationのバイト配列
	 */
	public static byte[] getByteObject(final Location location) {
		try {
			ByteArrayOutputStream byteos = new ByteArrayOutputStream();
			ObjectOutputStream objos = new ObjectOutputStream(byteos);
			objos.writeObject(new LocationSerializable(location));
			objos.close();
			byteos.close();
			return byteos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * byte配列からLocationのインスタンスを生成する
	 * @param bytes 読み込みたいLocationのbyte配列
	 * @return 読み込んだLocationをインスタンスとして返す
	 */
	public static Location createLocation(final byte[] bytes, final JavaPlugin plugin) {
		try {
			ByteArrayInputStream byteis = new ByteArrayInputStream(bytes);
			ObjectInputStream objis = new ObjectInputStream(byteis);
			LocationSerializable ls = (LocationSerializable) objis.readObject();
			byteis.close();
			objis.close();
			return ls.getLocation(plugin);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
