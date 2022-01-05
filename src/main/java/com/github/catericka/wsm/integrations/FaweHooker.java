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

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.FaweAPI;
import com.fastasyncworldedit.core.internal.exception.FaweException;
import com.github.catericka.wsm.utils.Box;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import org.bukkit.entity.Player;

import static com.github.catericka.wsm.WorldEditSelectionManager.instance;

public class FaweHooker {
    public WorldEdit getWorldEditInstance() {
        return WorldEdit.getInstance();
    }

    public Fawe getFaweInstance() {
        return Fawe.get();
    }
    
    public void cancelEditSession(EditSession editSession, String reason) {
        try {
            FaweAPI.cancelEdit(editSession, TextComponent.of(reason));
        } catch (FaweException e) {
            instance.getLogger().info("manually cancel EditSession task by FaweAPI::cancelEdit");
        }
    }

    public String getWorldEditVersion() {
        return WorldEdit.getVersion();
    }

    public void select(Player player, Box box) {
        if(player == null) throw new IllegalArgumentException("Null player not allowed");
        if(!player.isOnline()) throw new IllegalArgumentException("Offline player not allowed");

        final BukkitPlayer worldEditPlayer = BukkitAdapter.adapt(player);
        final LocalSession session = getWorldEditInstance().getSessionManager().get(worldEditPlayer);
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

            session.getRegionSelector(worldEditPlayer.getWorld()).explainPrimarySelection(worldEditPlayer, session, box.getLowerBlockVector3());
            session.getRegionSelector(worldEditPlayer.getWorld()).explainSecondarySelection(worldEditPlayer, session, box.getUpperBlockVector3());
        }
    }
}
