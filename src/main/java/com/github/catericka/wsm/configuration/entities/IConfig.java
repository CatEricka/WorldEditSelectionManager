package com.github.catericka.wsm.configuration.entities;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public interface IConfig extends ConfigurationSerializable {
    default <V> V getOrDefault(Map<String, Object> map, String path, V defaultValue) {
        String[] paths = path.split("\\.");
        if (paths.length == 0) {
            return defaultValue;
        }
        else if (paths.length == 1) {
            if (map.get(path) == null) return defaultValue;
            else return (V) map.get(path);
        } else {
            for (int i = 0; i < paths.length - 1; i++) {
                if (map.get(paths[i]) == null) {
                    return defaultValue;
                } else if (map.get(paths[i]) instanceof Map) {
                    map = (Map<String, Object>) map.get(paths[i]);
                } else {
                    return defaultValue;
                }
            }
            return (V) map.get(paths[paths.length - 1]);
        }
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
