package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WssCommandCancel extends Command {
    public WssCommandCancel() {
        super("cancel");
        setPermission(Permissions.ADMIN.toString());
        setPermissionMessage(configManager.messages.AccesDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }
        // Get the player
        final Player player = (Player) sender;
        final WsmApi.WsmPlayer wsmPlayer = WsmApi.getPlayer(player);
        if (wsmPlayer.allTaskDone()) {
            player.sendMessage(configManager.messages.chatPrefix + ChatColor.YELLOW + " All task finished.");
        } else {
            WsmApi.getPlayer(player).cancelTask("[WSM]: Player canceled.");
            player.sendMessage(configManager.messages.chatPrefix + ChatColor.YELLOW + " Search task while be cancel...");
        }
        return true;
    }
}
