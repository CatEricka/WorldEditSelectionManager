package com.github.catericka.wsm;

import com.github.catericka.wsm.utils.SearchTask;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

import static com.github.catericka.wsm.WorldEditSelectionManager.*;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WsmApi {
    public static class WsmPlayer {
        public boolean enable = false;
        public int maxX;
        public int maxY;
        public int maxZ;
        final public Set<BlockType> excludeBlocks = Collections.synchronizedSet(new HashSet<>());
        final public List<Operation> taskQueue = Collections.synchronizedList(new ArrayList<>());

        public WsmPlayer() {
            maxX = configManager.config.maxX;
            maxY = configManager.config.maxY;
            maxZ = configManager.config.maxZ;
            setDefaultExcludeBlocks();
        }

        public WsmPlayer(boolean enable) {
            this.enable = enable;
            maxX = configManager.config.maxX;
            maxY = configManager.config.maxY;
            maxZ = configManager.config.maxZ;
            setDefaultExcludeBlocks();
        }

        public WsmPlayer(int maxX, int maxY, int maxZ) {
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
            setDefaultExcludeBlocks();
        }

        public void setDefaultExcludeBlocks() {
            excludeBlocks.clear();
            excludeBlocks.add(BukkitAdapter.asBlockType(Material.AIR));
        }

        public void cancelTask() {
            // TODO right way to cancel search task
            taskQueue.forEach(Operation::cancel);
            taskQueue.clear();
        }
    }


    private final static Map<UUID, WsmPlayer> players = new HashMap<>();      // List of players
    private final static long delay = 10;                   // Delay(in ticks) before executing asynchronous tasks


    // Get the player
    public static WsmPlayer getPlayer(Player player) {
        if (player == null) return null;

        // Get the player if exist
        WsmPlayer wsmPlayer = players.get(player.getUniqueId());

        // If not create it
        if (wsmPlayer == null)
            players.put(player.getUniqueId(), new WsmPlayer());

        return players.get(player.getUniqueId());
    }

    // Enable player to create a chest preview
    public static void enable(Player player) {
        getPlayer(player).enable = true;
    }

    // Disable player from creating a chest preview
    public static void disable(Player player) {
        getPlayer(player).cancelTask();
        getPlayer(player).enable = false;
    }

    // Remove all players from the player list
    public static void remove(Player player) {
        getPlayer(player).cancelTask();
        players.remove(player.getUniqueId());
    }

    public static void removeAll() {
        clearAllTaskQueue();
        players.clear();
    }

    // Get player info
    public static boolean isEnable(Player player) {
        return getPlayer(player).enable;
    }

    public static int getMaxX(Player player) {
        return getPlayer(player).maxX;
    }

    public static int getMaxY(Player player) {
        return getPlayer(player).maxY;
    }

    public static int getMaxZ(Player player) {
        return getPlayer(player).maxZ;
    }


    public static void select(Player player, final Location location) {
        final WsmPlayer wsmPlayer = getPlayer(player);

        if (wsmPlayer != null)
            select(player, location, wsmPlayer.excludeBlocks, wsmPlayer.maxX, wsmPlayer.maxY, wsmPlayer.maxZ);
    }

    public static void select(final Player player, final Location location, final Set<BlockType> excluded, final int lengthX, final int lengthY, final int lengthZ) {
        WsmPlayer wsmPlayer = getPlayer(player);
        if (wsmPlayer.taskQueue.size() != 0) {
            player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Task Running...");
            return;
        }

        player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Selecting structure ...");

        SearchTask searchTask = new SearchTask(location, excluded, lengthX, lengthY, lengthZ);
        wsmPlayer.taskQueue.add(searchTask.getOperation());

        instance.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> {

            // async task
            searchTask.runSearch();

            // Create the WorldEdit selection
            instance.getServer().getScheduler().runTask(instance, () -> {
                if (searchTask.isTaskComplete()) {
                    player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Structure selection done.");
                } else {
                    player.sendMessage(configManager.messages.chatPrefix + ChatColor.RED + " Operation canceled.");
                }
                worldEditHooker.select(player, searchTask.getBox());
                getPlayer(player).taskQueue.clear();
            });
        }, delay);
    }

    public static void clearAllTaskQueue() {
        // TODO right way to cancel search task
        players.forEach(((uuid, wsmPlayer) -> wsmPlayer.cancelTask()));
    }
}
