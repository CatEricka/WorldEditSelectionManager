package com.github.catericka.wsm.commands.subcommands;

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WsmCommandExclude extends Command
{

	public WsmCommandExclude()
	{
		super("exclude");
		this.setAliases(Lists.newArrayList("e"));
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

		if(args.length == 0) {
			WsmApi.getPlayer(player).setDefaultExcludeBlocks();
			return true;
		}

		// Get material from command line
		Set<BlockType> blockTypes = new HashSet<>();
		for (String s : args) {
			Material material = Material.getMaterial(s);
			if (material != null) {
				blockTypes.add(BukkitAdapter.asBlockType(material));
				player.sendMessage(s + " add to exclude");
			} else {
				player.sendMessage(s + " is not a material");
			}
		}

		// Define the material for the player
		WsmApi.getPlayer(player).excludeBlocks.addAll(blockTypes);

		return true;
	}
}
