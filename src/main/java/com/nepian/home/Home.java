package com.nepian.home;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.home.command.HomeCommand;

public class Home extends JavaPlugin {

	@Override
	public void onEnable() {
		if (!isLoadedCore()) return;
		
		HomeManager.load(this);
		HomeCommand.load(this);
	}
	
	private boolean isLoadedCore() {
		if (getServer().getPluginManager().getPlugin("Core") == null) {
			getLogger().log(Level.WARNING, "前提プラグイン(Core)が読み込まれていません");
			getLogger().log(Level.WARNING, "このプラグインを終了します");
			getServer().getPluginManager().disablePlugin(this);
			return false;
		} else {
			return true;
		}
	}
}
