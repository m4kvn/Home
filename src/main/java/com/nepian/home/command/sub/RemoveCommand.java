package com.nepian.home.command.sub;

import java.sql.SQLException;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nepian.core.Messenger;
import com.nepian.core.utils.command.CommandSenderType;
import com.nepian.core.utils.command.SubCommand;
import com.nepian.home.HomeManager;

public class RemoveCommand extends SubCommand {
	private Messenger messenger;
	
	public RemoveCommand(Messenger messenger) {
		super("remove", "rm");
		this.setSenderType(CommandSenderType.PLAYER);
		this.messenger = messenger;
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) throws CommandException {
		try {
			if (HomeManager.removeHome((Player) sender)) {
				messenger.sendSuccess(sender, "ホームを削除しました");
			} else {
				messenger.sendFailed(sender, "ホームが設定されていません");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

}
