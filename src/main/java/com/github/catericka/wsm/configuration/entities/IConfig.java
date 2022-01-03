package com.github.catericka.wsm.configuration.entities;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public interface IConfig extends ConfigurationSerializable {
    default <V> V getOrDefault(Map<String, Object> map, String path, V defaultValue) {
        String[] paths = path.split("\\.");
        Object nextMap = map;
        for (String p : paths) {
            nextMap = map.get(p);
            if (nextMap == null)
                return defaultValue;
        }
        return (V) nextMap;
    }

    default <V> void setValue(Map<String, Object> map, String path, V value) {
        String[] paths = path.split("\\.");
        if (paths.length == 1) {
            map.put(path, value);
        } else {
            for (int i = 0; i < paths.length - 1; i++) {
                Map<String, Object> nextMap;
                if (map.get(paths[i]) == null) {
                    nextMap = new HashMap<>();
                    map.put(paths[i], nextMap);
                } else {
                    nextMap = (Map<String, Object>) map.get(paths[i]);
                }
                map = nextMap;
            }
            map.put(paths[paths.length - 1], value);
        }
    }

    String getConfigFileName();
}
