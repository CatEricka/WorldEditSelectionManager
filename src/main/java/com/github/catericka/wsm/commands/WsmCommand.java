package com.github.catericka.wsm.commands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.commands.subcommands.*;
import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommand extends Command {
    private final Map<String, Command> subCommands = new HashMap<>();
    private final List<String> subCommandsList = new ArrayList<>();

    public WsmCommand(String name) {
        super(name);
        setUsage("/wsm|wss disable|enable|exclude|help|maxXZ|maxY|reload");
        setAliases(List.of("wss"));
        setDescription("auto select WorldEdit cube selection");
        setPermission(Permissions.ADMIN.toString());

        if (configManager.isLoadSuccess()) {

            addSubCommand(new WsmCommandDisable());
            addSubCommand(new WsmCommandEnable());
            addSubCommand(new WssCommandCancel());
            addSubCommand(new WsmCommandExcludeAdd());
            addSubCommand(new WsmCommandExcludeAddHand());
            addSubCommand(new WsmCommandExcludeClear());
            addSubCommand(new WsmCommandExcludeList());
            addSubCommand(new WsmCommandHelp());
            addSubCommand(new WsmCommandMaxXZ());
            addSubCommand(new WsmCommandMaxY());
            addSubCommand(new WsmCommandReload());
        } else {
            addSubCommand(new WsmCommandReload());
        }

		subCommandsList.addAll(subCommands.keySet());
    }

    private void addSubCommand(Command cmd) {
        subCommands.put(cmd.getName(), cmd);
        cmd.getAliases().forEach(alias -> subCommands.put(alias, cmd));
    }
    private Command getSubCommand(String name) {
    	return subCommands.get(name);
	}

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermissionSilent(sender)) {
            sender.sendMessage(configManager.messages.chatPrefix + " " + configManager.messages.AccessDenied);
            return true;
        }

        if (args.length == 0) {
            if (sender instanceof Player) {
                return onPlayerExecute((Player) sender, commandLabel, args);
            } else {
                return onConsoleExecute(sender, commandLabel, args);
            }
        } else {
            //dispatch
			commandLabel = args[0];
			if (args.length > 1) {
                args = Arrays.copyOfRange(args, 1, args.length);
            } else {
			    args = new String[]{};
            }

            Command executor = getSubCommand(commandLabel);
			if (executor == null)
			    return false;

            if (!configManager.isLoadSuccess()) {
				// Plugin load Failed, only allow reload command
				if (executor instanceof WsmCommandReload) {
					return executor.execute(sender, commandLabel, args);
				} else {
					sender.sendMessage(ChatColor.RED + configManager.messages.eLoad);
					return true;
				}
			} else {
				return executor.execute(sender, commandLabel, args);
			}
        }
    }

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			return subCommandsList;
		} else if (args.length == 2) {
            String subCommandName = args[1];
            if (subCommandsList.contains(subCommandName)) {
                return getSubCommand(subCommandName)
                        .tabComplete(sender, alias, args);
            }
        }
        return super.tabComplete(sender, alias, args);
	}

	public boolean onPlayerExecute(@NotNull Player player, @NotNull String commandLabel, @NotNull String[] args) {
        player.sendMessage(configManager.messages.chatPrefix + " " + ChatColor.AQUA + (WsmApi.isEnable(player) ? "on" : "off"));
        player.sendMessage(configManager.messages.maxXZ + " : " + ChatColor.AQUA + WsmApi.getMaxXZ(player));
        player.sendMessage(configManager.messages.maxY + " : " + ChatColor.AQUA + WsmApi.getMaxY(player));
        return true;
    }

    public boolean onConsoleExecute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        sender.sendMessage(ChatColor.RED + configManager.messages.ePlayer);
        return false;
    }
}
