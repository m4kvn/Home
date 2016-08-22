package com.nepian.home.command;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

import com.nepian.core.utils.CommandUtil;
import com.nepian.core.utils.command.CommandSenderType;
import com.nepian.core.utils.command.MainCommand;
import com.nepian.core.utils.player.GetOfflinePlayer;
import com.nepian.home.HomeManager;
import com.nepian.home.command.sub.RemoveCommand;
import com.nepian.home.command.sub.SetCommand;

public class HomeCommand extends MainCommand {
	private static final String COMMAND_NAME = "home";

	public HomeCommand(JavaPlugin plugin) {
		super(plugin, COMMAND_NAME);
		this.setSenderType(CommandSenderType.PLAYER);
		this.setSubCommand(new SetCommand(this.messenger));
		this.setSubCommand(new RemoveCommand(this.messenger));
	}
	
	public static void load(JavaPlugin plugin) {
		HomeCommand command = new HomeCommand(plugin);
		CommandUtil.registerCommand(plugin, COMMAND_NAME, command);
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		
		Player player = (Player) sender;
		Location location = null;
		
		if (args.length == 1) {
			String name = args[0];
			OfflinePlayer p = GetOfflinePlayer.fromPlayerName(name);
			
			if (p == null) {
				messenger.sendFailed(sender, "プレイヤー(" + name + ")が見つかりません");
				return;
			}
			
			location = HomeManager.getHome(p);
		} else {
			location = HomeManager.getHome(player);
		}
		
		if (location == null) {
			messenger.sendFailed(sender, "ホームが設定されていません");
		} else {
			player.teleport(location, TeleportCause.PLUGIN);
		}
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}
}
