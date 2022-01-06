package com.github.catericka.wsm.listeners;

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

import com.github.catericka.wsm.WsmApi;
import com.github.catericka.wsm.configuration.Permissions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.github.catericka.wsm.WorldEditSelectionManager.configManager;

public class PlayerListener implements Listener {
    @EventHandler()
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!configManager.isLoadSuccess()) return;
        WsmApi.remove(event.getPlayer());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
		if (!configManager.isLoadSuccess()) return;

		// Get the player
        final Player player = event.getPlayer();

        if (!Permissions.has(player, Permissions.ADMIN)) {
            player.sendMessage(configManager.messages.chatPrefix + " " + configManager.messages.AccessDenied);
            return;
        }

        if (!WsmApi.isEnable(player)) return;

        if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && isWoodenAxe(player.getInventory().getItemInMainHand().getType())
                && Permissions.has(player, Permissions.ADMIN)) {
            // Clicked location
            Block block = event.getClickedBlock();
            if (block != null) {
                // Clicked not void
                Location clickedLoc = block.getLocation();

                // Cancel event
                event.setCancelled(true);

                // Select structure
                WsmApi.select(player, clickedLoc);
            }
        }
    }

    private boolean isWoodenAxe(Material type) {
        return type == Material.WOODEN_AXE;
    }
}
