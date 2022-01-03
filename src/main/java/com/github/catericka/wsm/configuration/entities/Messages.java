package com.github.catericka.wsm.configuration.entities;

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

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Messages implements IConfig {

    public String chatPrefix = "&3[&bWsm&3]&r";

    public List<String> cHelp = Collections.singletonList("Display Wsm help");
    public List<String> cReload = Collections.singletonList("Reload Wsm");
    public String cEnable = "Enable Wsm!";
    public String cDisable = "Disable Wsm!";
    public String cMaxX = "Define maximum maxX selection";
    public String cMaxY = "Define maximum maxY selection";
    public String cMaxZ = "Define maximum maxZ selection";
    public String cExclude = "List of material to exclude";

    public String sReload = "Wsm has been reloaded";

    public String AccesDenied = "You don't have permission to do that!";
    public String ePlayer = "This command can only be executed by a player";
    public String eMaxX = "X length must be positive!";
    public String eMaxY = "Y length must be positive!";
    public String eMaxZ = "Z length must be positive!";

    public String enable = "is enable!";
    public String disable = "is disable!";
    public String eLoad = "Plugin Load FAILED, please fix config file then run /wsm reload";
    public String leftClick = "left click an item with a wooden axe to select a structure!";
    public String maxX = "Maximum length on X axis";
    public String maxY = "Maximum length on Y axis";
    public String maxZ = "Maximum length on Z axis";
    public String exclude = "Excluded materials";

    public Messages() {
    }

    public Messages(Map<String, Object> map) {
        chatPrefix = transColorCode(getOrDefault(map, "chat.prefix", chatPrefix));

        cHelp = getOrDefault(map, "commands.help.cmd", cHelp);
        cReload = getOrDefault(map, "commands.reload.cmd", cReload);
        cEnable = getOrDefault(map, "commands.enable.cmd", cEnable);
        cDisable = getOrDefault(map, "commands.disable.cmd", cDisable);
        cMaxX = getOrDefault(map, "commands.maxx.cmd", cMaxX);
        cMaxY = getOrDefault(map, "commands.maxy.cmd", cMaxY);
        cMaxZ = getOrDefault(map, "commands.maxz.cmd", cMaxZ);
        cExclude = getOrDefault(map, "commands.exclude.cmd", cExclude);

        sReload = getOrDefault(map, "success.reload", sReload);

        AccesDenied = getOrDefault(map, "errors.AccesDenied", AccesDenied);
        ePlayer = getOrDefault(map, "errors.player", ePlayer);
        eMaxX = getOrDefault(map, "errors.maxX", eMaxX);
        eMaxY = getOrDefault(map, "errors.maxY", eMaxY);
        eMaxZ = getOrDefault(map, "errors.maxZ", eMaxZ);

        enable = getOrDefault(map, "messages.enable", enable);
        disable = getOrDefault(map, "messages.disable", disable);
        eLoad = getOrDefault(map, "messages.reload_failed", eLoad);
        leftClick = getOrDefault(map, "messages.leftClick", leftClick);
        maxX = getOrDefault(map, "messages.maxX", maxX);
        maxY = getOrDefault(map, "messages.maxY", maxY);
        maxZ = getOrDefault(map, "messages.maxZ", maxZ);
        exclude = getOrDefault(map, "messages.exclude", exclude);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        setValue(map, "chat.prefix", chatPrefix);

        setValue(map, "commands.help.cmd", cHelp);
        setValue(map, "commands.reload.cmd", cReload);
        setValue(map, "commands.enable.cmd", cEnable);
        setValue(map, "commands.disable.cmd", cDisable);
        setValue(map, "commands.maxx.cmd", cMaxX);
        setValue(map, "commands.maxy.cmd", cMaxY);
        setValue(map, "commands.maxz.cmd", cMaxZ);
        setValue(map, "commands.exclude.cmd", cExclude);

        setValue(map, "success.reload", sReload);

        setValue(map, "errors.AccesDenied", AccesDenied);
        setValue(map, "errors.player", ePlayer);
        setValue(map, "errors.maxX", eMaxX);
        setValue(map, "errors.maxY", eMaxY);
        setValue(map, "errors.maxZ", eMaxZ);

        setValue(map, "messages.enable", enable);
        setValue(map, "messages.disable", disable);
        setValue(map, "messages.reload_failed", eLoad);
        setValue(map, "messages.leftClick", leftClick);
        setValue(map, "messages.maxX", maxX);
        setValue(map, "messages.maxY", maxY);
        setValue(map, "messages.maxZ", maxZ);
        setValue(map, "messages.exclude", exclude);

        return map;
    }

    private String transColorCode(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public String getConfigFileName() {
        return "message.yml";
    }
}
