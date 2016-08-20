package com.nepian.home;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.core.Messenger;
import com.nepian.home.command.HomeCommand;
import com.nepian.home.listener.PlayerRespawnListener;

public class Home extends JavaPlugin {
	private static final String CORE_VERSION = "1.0";

	@Override
	public void onEnable() {
		if (!isLoadedCore()) return;
		HomeManager.load(this);
		HomeCommand.load(this);
		PlayerRespawnListener.load(this);
	}
	
	@Override
	public void onDisable() {
		HomeManager.close();
	}
	
	private final boolean isLoadedCore() {
		PluginManager pm = getServer().getPluginManager();
		Logger logger = getLogger();
		
		if (pm.getPlugin("Core") != null) {
			String version = pm.getPlugin("Core").getDescription().getVersion();
			
			if (CORE_VERSION.equals(version)) {
				new Messenger(this).success("Core-" + CORE_VERSION + "と接続");
				
				return true;
			} else {
				logger.log(Level.WARNING, "前提プラグイン(Core)のバージョンが一致しません");
				logger.log(Level.WARNING, "Core-" + CORE_VERSION + "が必要です");
				logger.log(Level.WARNING, "現在のバージョン: " + version);
			}
		} else {
			logger.log(Level.WARNING, "前提プラグイン(Core)が読み込まれていません");
		}
		
		logger.log(Level.WARNING, "このプラグインを終了します");
		pm.disablePlugin(this);
		
		return false;
	}
}
