package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandMaxXZ extends Command {
    public WsmCommandMaxXZ() {
        super("maxXZ");
        this.setAliases(Lists.newArrayList("xz"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }
        // Get the player
        final Player player = (Player) sender;

        // Get maxX from command line
        if (args.length == 0) {
            player.sendMessage(configManager.messages.eMaxXZ);
        } else {
            int maxXZ;
            try {
                maxXZ = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                maxXZ = 32;
            }

            // Define the maxXZ for the player
            WsmApi.getPlayer(player).maxXZ = maxXZ;

            // Message
            player.sendMessage(configManager.messages.maxXZ + " : " + maxXZ);
        }

        // command always handled
        return true;
    }
}
