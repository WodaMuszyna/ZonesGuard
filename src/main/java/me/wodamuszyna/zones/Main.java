package me.wodamuszyna.zones;

import me.wodamuszyna.zones.commands.ZonesCommand;
import me.wodamuszyna.zones.config.Config;
import me.wodamuszyna.zones.database.Database;
import me.wodamuszyna.zones.listeners.BlockListener;
import me.wodamuszyna.zones.listeners.PlayerListener;
import me.wodamuszyna.zones.managers.ZoneManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    public static Main instance;
    public Config config;
    public Database db;
    public static ZoneManager manager;

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(Config.class);
        saveResource("config.yml", false);
        db = new Database(getConfig());
        db.update("CREATE TABLE IF NOT EXISTS `zonesguard_zones` (`name` VARCHAR(32) NOT NULL PRIMARY KEY, `min` VARCHAR(72) NOT NULL, `max` VARCHAR(72) NOT NULL, `flags` LONGBLOB)");
        try {
            getConfig().load(new File(getDataFolder(), "config.yml"));
            this.config = (Config)getConfig().get("zones");
            if (this.config == null) {
                throw new InvalidConfigurationException("Error reading configuration!");
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
        manager = new ZoneManager();
        manager.init();
        getCommand("zg").setExecutor(new ZonesCommand());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        manager.save();
        db.disconnect();
        db = null;
        ConfigurationSerialization.unregisterClass(Config.class);
        HandlerList.unregisterAll();
    }

    public static Main getInstance(){ return instance; }
    public static ZoneManager getManager(){ return manager; }

}
