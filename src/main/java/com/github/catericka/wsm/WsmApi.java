package com.github.catericka.wsm;

import com.github.catericka.wsm.utils.Box;
import com.github.catericka.wsm.utils.SearchTask;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.github.catericka.wsm.WorldEditSelectionManager.*;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WsmApi {
    public static class WsmPlayer {
        public boolean enable = false;
        public int maxXZ;
        public int maxY;
        final private Set<BlockType> excludeBlocks = Collections.synchronizedSet(new HashSet<>());
        private Future<Box> taskRunning;

        public WsmPlayer() {
            maxXZ = configManager.config.maxXZ;
            maxY = configManager.config.maxY;
            setDefaultExcludeBlocks();
        }

        public WsmPlayer(boolean enable) {
            this.enable = enable;
            maxXZ = configManager.config.maxXZ;
            maxY = configManager.config.maxY;
            setDefaultExcludeBlocks();
        }

        public WsmPlayer(int maxXZ, int maxY) {
            this.maxXZ = maxXZ;
            this.maxY = maxY;
            setDefaultExcludeBlocks();
        }

        public void setDefaultExcludeBlocks() {
            excludeBlocks.clear();
            excludeBlocks.add(BukkitAdapter.asBlockType(Material.AIR));
        }

        protected void addTask(Future<Box> task) {
            if (!allTaskDone()) {
                throw new IllegalArgumentException("Other task running");
            }
            taskRunning = task;
        }

        protected void taskDone() {
            taskRunning = null;
        }

        public boolean allTaskDone() {
            return taskRunning == null;
        }

        public void cancelTask() {
            if (taskRunning != null) {
                taskRunning.cancel(true);
            }
            taskRunning = null;
        }

        public void addExcludeBlock(BlockType blockType) {
            excludeBlocks.add(blockType);
        }

        public void addExcludeBlocks(Set<BlockType> blockTypeSet) {
            excludeBlocks.addAll(blockTypeSet);
        }

        public void clearExcludeBlocks() {
            setDefaultExcludeBlocks();
        }

        /**
         * @return do not modify manually return set
         */
        public Set<BlockType> getExcludeBlockSet() {
            return excludeBlocks;
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

    public static int getMaxXZ(Player player) {
        return getPlayer(player).maxXZ;
    }

    public static int getMaxY(Player player) {
        return getPlayer(player).maxY;
    }


    public static void select(Player player, final Location location) {
        final WsmPlayer wsmPlayer = getPlayer(player);

        if (wsmPlayer != null)
            select(player, location, wsmPlayer.excludeBlocks, wsmPlayer.maxXZ, wsmPlayer.maxY);
    }

    public static void select(final Player player, final Location location, final Set<BlockType> excluded, final int lengthXZ, final int lengthY) {
        WsmPlayer wsmPlayer = getPlayer(player);
        if (!wsmPlayer.allTaskDone()) {
            player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Task Running...");
            return;
        }

        player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Selecting structure ...");
        SearchTask searchTask = new SearchTask(location, excluded, lengthXZ, lengthY);
        Future<Box> future = searchTask.runSearchTaskAsync();

        wsmPlayer.addTask(future);
        UUID playerUniqueId = player.getUniqueId();
        instance.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> {
            try {
                Box box = future.get();

                // Create the WorldEdit selection sync
                instance.getServer().getScheduler().runTask(instance, () -> {
                    Player maybePlayer = instance.getServer().getPlayer(playerUniqueId);
                    // If player offline
                    if (maybePlayer == null || !maybePlayer.isOnline()) return;

                    maybePlayer.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Structure selection done.");
                    faweHooker.select(maybePlayer, box);
                    getPlayer(maybePlayer).taskDone();
                });
            } catch (InterruptedException | ExecutionException | CancellationException e) {
                // task cancel
                instance.getServer().getScheduler().runTask(instance, () -> {
                    Player maybePlayer = instance.getServer().getPlayer(playerUniqueId);
                    // If player offline
                    if (maybePlayer == null || !maybePlayer.isOnline()) return;
                    maybePlayer.sendMessage(configManager.messages.chatPrefix + ChatColor.RED + " Search task canceled.");
                });
            }
        }, delay);
    }

    public static void clearAllTaskQueue() {
        players.forEach(((uuid, wsmPlayer) -> wsmPlayer.cancelTask()));
    }
}
