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
    public int maxX = 32;
    public int maxY = 32;
    public int maxZ = 32;

    public Config() {
    }

    public Config(Map<String, Object> map) {
        maxX = getOrDefault(map, "selection.maxX", maxX);
        maxY = getOrDefault(map, "selection.maxY", maxY);
        maxZ = getOrDefault(map, "selection.maxZ", maxZ);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        setValue(map, "selection.maxX", maxX);
        setValue(map, "selection.maxY", maxY);
        setValue(map, "selection.maxZ", maxZ);
        return map;
    }

    @Override
    public String getConfigFileName() {
        return "config.yml";
    }
}
