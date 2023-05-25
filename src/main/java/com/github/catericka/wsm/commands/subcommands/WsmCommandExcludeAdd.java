package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.google.common.collect.Lists;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WsmCommandExcludeAdd extends Command {

    public WsmCommandExcludeAdd() {
        super("exclude_add");
        this.setAliases(Lists.newArrayList("add"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " nothing changed.");
            return true;
        }

        // Get material from command line
		WsmApi.WsmPlayer wsmPlayer = WsmApi.getPlayer(player);
        for (String s : args) {
            Material material = Material.getMaterial(s);
            if (material != null) {
                wsmPlayer.addExcludeBlock(BukkitAdapter.asBlockType(material));
                player.sendMessage(configManager.messages.chatPrefix + ChatColor.GREEN + " " + s + " added to exclude.");
            } else {
                player.sendMessage(configManager.messages.chatPrefix + ChatColor.RED + " " + s + " is not a material.");
            }
        }

        return true;
    }
}
