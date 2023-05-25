package com.github.catericka.wsm.utils;

import com.fastasyncworldedit.core.internal.exception.FaweException;
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
import java.util.function.Consumer;

import static com.github.catericka.wsm.WorldEditSelectionManager.faweHooker;

public class SearchTask {
    final private EditSession editSession;
    final private RecursiveVisitor visitor;
    private final Box box;
    private Consumer<Box> consumerIfSuccess;
    private Consumer<String> consumerIfFailure;

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
                // minY
                Math.max(location.getBlockY() - maxY, location.getWorld().getMinHeight()),
                // maxY
                Math.min(location.getBlockY() + maxY, location.getWorld().getMaxHeight()),
                editSession);

        visitor.visit(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public void SetAsyncCallbackIfSuccess(Consumer<Box> consumer) {
        consumerIfSuccess = consumer;
    }

    public void SetAsyncCallbackIfFailure(Consumer<String> consumer) {
        consumerIfFailure = consumer;
    }

    public void runSearchTaskAsyncWithCallback() {
        faweHooker.getFaweInstance().getQueueHandler().async(() -> {
            try {
                Operations.complete(visitor);
            } catch (FaweException e) {
                consumerIfFailure.accept(e.getMessage());
                return;
            } finally {
                editSession.close();
            }
            consumerIfSuccess.accept(box);
        });
    }

    public void cancelSearchTask(String reason) {
        faweHooker.cancelEditSession(editSession, reason);
    }
}
