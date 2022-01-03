package com.github.catericka.wsm;

import com.github.catericka.wsm.commands.WsmCommand;
import com.github.catericka.wsm.configuration.entities.Config;
import com.github.catericka.wsm.configuration.ConfigManager;
import com.github.catericka.wsm.configuration.entities.Messages;
import com.github.catericka.wsm.integrations.WorldEditHooker;
import com.github.catericka.wsm.listeners.PlayerListener;
import com.github.catericka.wsm.listeners.UpdaterListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldEditSelectionManager extends JavaPlugin {
    public static JavaPlugin instance = null;

    /* Configs */
    public static ConfigManager configManager = null;

    /* Plugins */
    public static WorldEditHooker worldEditHooker = null;


    /**
     * Activation du plugin
     */
    @Override
    public void onEnable() {
        /* Instance du plugin */
        instance = this;

        /* register serialization */
        ConfigurationSerialization.registerClass(Config.class);
        ConfigurationSerialization.registerClass(Messages.class);

        /* load config */
        configManager = new ConfigManager(this, getDataFolder());
        configManager.loadAll();
        if (!configManager.isLoadSuccess()) {
            getLogger().warning("Load Configuration failed! Plugin while be disabled until next reload.");
        }

        /* Plugins */
        worldEditHooker = new WorldEditHooker();

        /* register commands */
        getServer().getCommandMap().register("WorldEditSelectionManager", new WsmCommand("wsm"));

        /* register listeners */
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new UpdaterListener(), this);

        /* Enable message */
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "WorldEdit Structure Selector is enabled.");
        getServer().getConsoleSender().sendMessage("WorldEdit version: " + ChatColor.YELLOW + worldEditHooker.getWorldEditVersion());
    }

    /**
     * Désactivation du plugin
     */
    @Override
    public void onDisable() {
//        WsmApi.clearAllTaskQueue();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "WorldEdit Structure Selector is now disabled");
    }
}
