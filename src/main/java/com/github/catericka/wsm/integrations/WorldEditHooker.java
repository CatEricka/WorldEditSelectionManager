package com.github.catericka.wsm.integrations;

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

import com.github.catericka.wsm.utils.Box;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class WorldEditHooker {
    final private WorldEdit worldEdit;
    final private String worldEditVersion;

    public WorldEditHooker() {
        // Hard Depend
        this.worldEdit = WorldEdit.getInstance();
        worldEditVersion = WorldEdit.getVersion();
    }

    public WorldEdit getInstance() {
        return worldEdit;
    }

    public String getWorldEditVersion() {
        return worldEditVersion;
    }

    public void select(Player player, Box box) {
        if(player == null) throw new IllegalArgumentException("Null player not allowed");
        if(!player.isOnline()) throw new IllegalArgumentException("Offline player not allowed");

        final BukkitPlayer worldEditPlayer = BukkitAdapter.adapt(player);
        final LocalSession session = worldEdit.getSessionManager().get(worldEditPlayer);
        session.setCUISupport(true);
        session.dispatchCUISetup(worldEditPlayer);

        if(box == null) {
            session.getRegionSelector(worldEditPlayer.getWorld()).clear();
            session.dispatchCUISelection(worldEditPlayer);
        }
        else {

            CuboidRegionSelector selector = new CuboidRegionSelector(worldEditPlayer.getWorld());
            selector.selectPrimary(box.getLowerBlockVector3(), ActorSelectorLimits.forActor(worldEditPlayer));
            selector.selectSecondary(box.getUpperBlockVector3(), ActorSelectorLimits.forActor(worldEditPlayer));

            session.setRegionSelector(worldEditPlayer.getWorld(), selector);

            player.sendMessage(configManager.messages.chatPrefix + ChatColor.GRAY + " Structure selection done.");
            session.getRegionSelector(worldEditPlayer.getWorld()).explainPrimarySelection(worldEditPlayer, session, box.getLowerBlockVector3());
            session.getRegionSelector(worldEditPlayer.getWorld()).explainSecondarySelection(worldEditPlayer, session, box.getUpperBlockVector3());
        }
    }
}
