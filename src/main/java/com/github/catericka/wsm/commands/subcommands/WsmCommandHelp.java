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
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandHelp extends Command {
    public WsmCommandHelp() {
        super("help");
        this.setDescription(StringUtils.join(configManager.messages.cHelp, "\n"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        String sb = configManager.messages.chatPrefix +
				ChatColor.YELLOW + "/wsm|wss help" + '\n' +
                ChatColor.WHITE + "    display help" + '\n' +
				ChatColor.YELLOW + "/wsm|wss cancel" + '\n' +
                ChatColor.WHITE + "    cancel select task" + '\n' +
                ChatColor.YELLOW + "/wsm|wss disable" + '\n' +
                ChatColor.WHITE + "    disable auto select" + '\n' +
                ChatColor.YELLOW + "/wsm|wss enable" + '\n' +
                ChatColor.WHITE + "    enable auto select" + '\n' +
                ChatColor.YELLOW + "/wsm|wss exclude_add|add <Materials_Enum ...>" + '\n' +
                ChatColor.WHITE + "    exclude blocks when selecting" + '\n' +
                ChatColor.YELLOW + "/wsm|wss exclude_add_hand|addhand" + '\n' +
                ChatColor.WHITE + "    exclude block in main hand when selecting" + '\n' +
                ChatColor.YELLOW + "/wsm|wss exclude_clear|clear <Materials_Enum ...>" + '\n' +
                ChatColor.WHITE + "    clear exclude blocks list" + '\n' +
                ChatColor.YELLOW + "/wsm|wss exclude_list|list <Materials_Enum ...>" + '\n' +
                ChatColor.WHITE + "    list exclude blocks" + '\n' +
                ChatColor.YELLOW + "/wsm|wss maxXZ|xz <size>" + '\n' +
                ChatColor.WHITE + "    set max x-z axis size" + '\n' +
                ChatColor.YELLOW + "/wsm|wss maxY|y <size>" + '\n' +
                ChatColor.WHITE + "    set max y axis size" + '\n' +
                ChatColor.YELLOW + "/wsm|wss reload" + '\n' +
                ChatColor.WHITE + "    reload configuration files" + '\n';
        sender.sendMessage(sb);
        return true;
    }
}
