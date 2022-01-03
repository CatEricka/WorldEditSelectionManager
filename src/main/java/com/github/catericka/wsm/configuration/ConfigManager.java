package com.github.catericka.wsm.configuration;

import com.github.catericka.wsm.WorldEditSelectionManager;
import com.github.catericka.wsm.configuration.entities.Config;
import com.github.catericka.wsm.configuration.entities.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final WorldEditSelectionManager plugin;

    private final File MessageFile;
    private final File ConfigFile;

    private final String RootPath = "root";

//  TODO  private final File selectionsStorageDir;

    private YamlConfiguration messageYaml;
    private YamlConfiguration configYaml;

    private boolean loadSuccess = false;

    public Config config = null;
    public Messages messages = null;

    public ConfigManager(WorldEditSelectionManager plugin, File dir) {
        this.plugin = plugin;
        MessageFile = new File(plugin.getDataFolder(), "message.yml");
        ConfigFile = new File(plugin.getDataFolder(), "config.yml");
//      TODO  selectionsStorageDir = new File(plugin.getDataFolder(), "selections");

        loadSuccess = false;
    }

    public boolean loadAll() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            if (!MessageFile.exists()) {
                MessageFile.createNewFile();
            }
            if (!ConfigFile.exists()) {
                ConfigFile.createNewFile();
            }
//          TODO selectionsStorageDir.mkdir()
//            if (!selectionsStorageDir.isDirectory()) {
//                selectionsStorageDir.mkdir();
//            }

            configYaml =  YamlConfiguration.loadConfiguration(ConfigFile);
            config = (Config) configYaml.get(RootPath);
            if (config == null) {
                config = new Config();
                configYaml.set(RootPath, config);
                configYaml.save(ConfigFile);
            }
            messageYaml =  YamlConfiguration.loadConfiguration(MessageFile);
            messages = (Messages) messageYaml.get(RootPath);
            if (messages == null) {
                messages = new Messages();
                messageYaml.set(RootPath, messages);
                messageYaml.save(MessageFile);
            }

        } catch (IOException e) {
            loadSuccess = false;
            e.printStackTrace();
            return false;
        }

        loadSuccess = true;
        return true;
    }

    public void saveAll() {
        // TODO
    }

    public boolean isLoadSuccess() {
        return loadSuccess;
    }
}
