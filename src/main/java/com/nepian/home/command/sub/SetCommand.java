package com.nepian.home.command.sub;

import java.sql.SQLException;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.core.Messenger;
import com.nepian.core.utils.command.CommandSenderType;
import com.nepian.core.utils.command.SubCommand;
import com.nepian.home.HomeManager;

public class SetCommand extends SubCommand {
	private Messenger messenger;
	
	public SetCommand(Messenger messenger) {
		super("set");
		this.setSenderType(CommandSenderType.PLAYER);
		this.messenger = messenger;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		Player player = (Player) sender;
		
		try {
			HomeManager.setHome(player);
			messenger.sendSuccess(sender, "この場所をホームに設定しました");
		} catch (SQLException e) {
			messenger.sendFailed(sender, "ホームの設定に失敗しました");
			messenger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

}
