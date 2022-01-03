package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.WorldEditSelectionManager;
import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.command.Command;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandLengthX extends Command {
    public WsmCommandLengthX() {
        super("maxX");
        this.setAliases(Lists.newArrayList("x"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccesDenied);
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
            player.sendMessage(configManager.messages.eMaxX);
        } else {
            int maxX;
            try {
                maxX = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                maxX = 32;
            }

            // Define the maxX for the player
            WsmApi.getPlayer(player).maxX = maxX;

            // Message
            player.sendMessage(configManager.messages.maxX + " : " + maxX);
        }

        // command always handled
        return true;
    }
}
