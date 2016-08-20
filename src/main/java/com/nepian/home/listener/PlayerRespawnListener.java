package com.nepian.home.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.core.utils.ListenerUtil;
import com.nepian.home.HomeManager;

public class PlayerRespawnListener implements Listener {
	
	public static void load(JavaPlugin plugin) {
		ListenerUtil.register(plugin, new PlayerRespawnListener());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Location location = HomeManager.getHome(player);
		
		if (location != null) {
			event.setRespawnLocation(location);
		}
	}
}
