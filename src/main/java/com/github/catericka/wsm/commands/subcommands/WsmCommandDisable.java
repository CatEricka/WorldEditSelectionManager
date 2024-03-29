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

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandDisable extends Command {

    public WsmCommandDisable() {
        super("disable");
        this.setAliases(Lists.newArrayList("off", "0"));
        this.setDescription(configManager.messages.cDisable);
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }
        // Disable Wss for the player and cancel search task
        WsmApi.disable(player);

        // Message
        sender.sendMessage(configManager.messages.chatPrefix + " " + configManager.messages.disable);

        return true;
    }
}
