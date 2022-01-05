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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Config implements IConfig {
    /* ConnectedBlock */
    public int maxXZ = 32;
    public int maxY = 32;
    public boolean debug = false;

    public Config() {
    }

    public Config(Map<String, Object> map) {
        maxXZ = getOrDefault(map, "selection.maxXZ", maxXZ);
        maxY = getOrDefault(map, "selection.maxY", maxY);
        debug = getOrDefault(map, "debug", debug);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        setValue(map, "selection.maxXZ", maxXZ);
        setValue(map, "selection.maxY", maxY);
        setValue(map, "debug", debug);
        return map;
    }

    @Override
    public String getConfigFileName() {
        return "config.yml";
    }
}
