package com.github.catericka.wsm.commands;
/*
 * Copyright 2017 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.commands.subcommands.*;
import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommand extends Command {
    private Map<String, Command> subCommands = new HashMap<>();
    private List<String> subCommandsList = new ArrayList<>();

    public WsmCommand(String name) {
        super(name);
        setUsage("/wss disable|enable|exclude|help|maxX|maxY|maxZ|reload");
        setDescription("auto select WorldEdit cube selection");
        setPermission(Permissions.ADMIN.toString());

        if (configManager.isLoadSuccess()) {

            addSubCommand(new WsmCommandDisable());
            addSubCommand(new WsmCommandEnable());
            addSubCommand(new WsmCommandExclude());
            addSubCommand(new WsmCommandHelp());
            addSubCommand(new WsmCommandLengthX());
            addSubCommand(new WsmCommandLengthY());
            addSubCommand(new WsmCommandLengthZ());
            addSubCommand(new WsmCommandReload());
        } else {
            addSubCommand(new WsmCommandReload());
        }

		subCommandsList.addAll(subCommands.keySet());
    }

    private void addSubCommand(Command cmd) {
        subCommands.put(cmd.getName(), cmd);
        cmd.getAliases().forEach(alias -> {
        	subCommands.put(alias, cmd);
		});
    }
    private Command getSubCommand(String name) {
    	return subCommands.get(name);
	}

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
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
		} else {
			return super.tabComplete(sender, alias, args);
		}
	}

	public boolean onPlayerExecute(@NotNull Player player, @NotNull String commandLabel, @NotNull String[] args) {
        player.sendMessage(configManager.messages.chatPrefix + " " + ChatColor.AQUA + (WsmApi.isEnable(player) ? "on" : "off"));
        player.sendMessage(configManager.messages.maxX + " : " + ChatColor.AQUA + WsmApi.getMaxX(player));
        player.sendMessage(configManager.messages.maxY + " : " + ChatColor.AQUA + WsmApi.getMaxY(player));
        player.sendMessage(configManager.messages.maxZ + " : " + ChatColor.AQUA + WsmApi.getMaxZ(player));
        return true;
    }

    public boolean onConsoleExecute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }
}
