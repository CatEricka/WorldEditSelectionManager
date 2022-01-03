package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WsmCommandLengthY extends Command {
    public WsmCommandLengthY() {
        super("maxY");
        this.setAliases(Lists.newArrayList("y"));
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

        // Get maxY from command line
        if (args.length == 0) {
            player.sendMessage(configManager.messages.eMaxY);
        } else {
            int maxY;
            try {
                maxY = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                maxY = configManager.config.maxY;
            }

            // Define the maxY for the player
            WsmApi.getPlayer(player).maxY = maxY;

            // Message
            player.sendMessage(configManager.messages.maxY + " : " + maxY);
        }

        // command always handled
        return true;
    }
}
