package com.github.catericka.wsm.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.mask.BlockTypeMask;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.visitor.RecursiveVisitor;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;

import java.util.Set;
import java.util.concurrent.Future;

import static com.github.catericka.wsm.WorldEditSelectionManager.faweHooker;

public class SearchTask {
    private EditSession editSession;
    private RecursiveVisitor visitor;
    private final Box box;
    private Future<Box> future;

    private boolean called;

    public SearchTask(Location location, Set<BlockType> excluded, int maxXZ, int maxY) {
        box = new Box(location);

        editSession = faweHooker.getWorldEditInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()));

        BlockTypeMask mask = new BlockTypeMask(editSession);
        mask.add(excluded);

        RegionFunction regionFunction = (pos) -> {
            box.add(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
            return false;
        };

        visitor = new RecursiveVisitor(
                mask.inverse(),
                regionFunction,
                maxXZ * 2 + 1,
                Math.max(location.getBlockY() - maxY, 0),
                Math.min(location.getBlockY() + maxY, 255),
                editSession);

        visitor.visit(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public Future<Box> runSearchTaskAsync() {
        if (called) {
            throw new IllegalStateException("Cannot call SearchTask::runSearchTaskAsync twice");
        }
        called = true;

        future = faweHooker.getFaweInstance().getQueueHandler().async(() -> {
            try {
                Operations.complete(visitor);
            } finally {
                editSession.close();
            }
            return box;
        });
        return future;
    }

    public Future<Box> getFuture() {
        return future;
    }

    public void cancelSearchTask(String reason) {
        future.cancel(true);
        faweHooker.cancelEditSession(editSession, reason);
    }
}
