package com.github.catericka.wsm.utils;


/*
 * Copyright 2017 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.fastasyncworldedit.core.function.RegionMaskTestFunction;
import com.github.catericka.wsm.WorldEditSelectionManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.mask.*;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.visitor.RecursiveVisitor;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

/**
 * This file is part of AddLight
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class StructureBox {
    private final Location location;
    private final int maxXZ;
    private final int maxY;
    private final Set<BlockType> excluded;

    private List<Location> unchecked = null;
    private List<Location> confirmed = null;

    public StructureBox(Location location, Set<BlockType> excluded, int maxX, int maxY, int maxZ) {
        this.location = location;
        this.excluded = excluded;
        this.maxXZ = Math.max(maxX, maxZ);
        this.maxY = maxY;
    }

    public Box getBox() {
        return findBoxFast();
    }

    private Box findBoxFast() {
        EditSession editSession = null;
        final Box box = new Box(location);
        try {
            editSession = WorldEditSelectionManager.worldEditHooker.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()));

            BlockTypeMask mask = new BlockTypeMask(editSession);
            mask.add(excluded);

            RegionFunction regionFunction = (pos) -> {
                box.add(pos.toVector3());
                return false;
            };

            RecursiveVisitor visitor = new RecursiveVisitor(
                    mask.inverse(),
                    regionFunction,
                    maxXZ * 2 + 1,
                    Math.max(location.getBlockY() - maxY, 0),
                    Math.min(location.getBlockY() + maxY, 255),
                    editSession);

            visitor.visit(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()));

            Operations.complete(visitor);
        } finally {
            if (editSession != null)
                editSession.close(); // Make sure edit session is always closed
        }

        return box;
    }
}
