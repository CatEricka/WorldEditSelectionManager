package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandLengthZ extends Command
{
	public WsmCommandLengthZ()
	{
		super("maxZ");
		setAliases(Lists.newArrayList("z"));
		setPermission(Permissions.ADMIN.toString());
		setPermissionMessage(configManager.messages.AccesDenied);
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(configManager.messages.ePlayer);
			return true;
		}
		// Get the player
		final Player player = (Player) sender;

		// Get maxZ from command line
		if (args.length == 0) {
			player.sendMessage( configManager.messages.eMaxZ);
		} else {
			int maxZ;
			try {
				maxZ = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				maxZ = configManager.config.maxZ;
			}

			// Define the maxZ for the player
			WsmApi.getPlayer(player).maxZ = maxZ;

			// Message
			player.sendMessage(configManager.messages.maxZ + " : " + maxZ);
		}

		// command always handled
		return true;
	}
}
