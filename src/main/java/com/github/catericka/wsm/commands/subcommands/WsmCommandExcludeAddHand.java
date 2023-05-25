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

public class WsmCommandExcludeAddHand extends Command {

    public WsmCommandExcludeAddHand() {
        super("exclude_add_hand");
        this.setAliases(Lists.newArrayList("addhand"));
        this.setPermission(Permissions.ADMIN.toString());
        this.setPermissionMessage(configManager.messages.AccessDenied);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage(configManager.messages.ePlayer);
            return true;
        }

        Material material = player.getInventory().getItemInMainHand().getType();
        WsmApi.getPlayer(player).addExcludeBlock(BukkitAdapter.asBlockType(material));
        player.sendMessage(configManager.messages.chatPrefix + ChatColor.GREEN + " " + material + " added to exclude.");

        return true;
    }
}
