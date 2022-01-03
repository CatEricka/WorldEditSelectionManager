package com.github.catericka.wsm.commands.subcommands;

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


import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandHelp extends Command
{
	public WsmCommandHelp()
	{
		super("help");
		this.setDescription(StringUtils.join(configManager.messages.cHelp,"\n"));
		this.setPermission(Permissions.ADMIN.toString());
		this.setPermissionMessage(configManager.messages.AccesDenied);
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
		sender.sendMessage(ChatColor.YELLOW + "/wss help");
		sender.sendMessage(ChatColor.WHITE + "    display help");
		sender.sendMessage(ChatColor.YELLOW + "/wss disable");
		sender.sendMessage(ChatColor.WHITE + "    disable auto select");
		sender.sendMessage(ChatColor.YELLOW + "/wss enable");
		sender.sendMessage(ChatColor.WHITE + "    enable auto select");
		sender.sendMessage(ChatColor.YELLOW + "/wss exclude <Materials_Enum ...>");
		sender.sendMessage(ChatColor.WHITE + "    exclude block when selecting");
		sender.sendMessage(ChatColor.YELLOW + "/wss maxX|x <size>");
		sender.sendMessage(ChatColor.WHITE + "    set max x-axial size");
		sender.sendMessage(ChatColor.YELLOW + "/wss maxY|y <size>");
		sender.sendMessage(ChatColor.WHITE + "    set max y-axial size");
		sender.sendMessage(ChatColor.YELLOW + "/wss maxZ|z <size>");
		sender.sendMessage(ChatColor.WHITE + "    set max z-axial size");
		sender.sendMessage(ChatColor.YELLOW + "/wss reload");
		sender.sendMessage(ChatColor.WHITE + "    reload configuration files");
		return true;
	}
}
