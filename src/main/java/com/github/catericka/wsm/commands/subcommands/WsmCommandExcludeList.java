package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandExcludeList extends Command {

    public WsmCommandExcludeList() {
        super("exclude_list");
        this.setAliases(Lists.newArrayList("list"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }

        StringBuilder text = new StringBuilder(configManager.messages.chatPrefix).append(ChatColor.GRAY).append(" Exclude list: \n  ");
        WsmApi.getPlayer(player).getExcludeBlockSet().forEach(blockType -> text.append(BukkitAdapter.adapt(blockType).toString()).append(", "));

        player.sendMessage(text.toString());
        return true;
    }
}
