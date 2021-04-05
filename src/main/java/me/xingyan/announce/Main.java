package me.xingyan.announce;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {


    public static Plugin plugin = null;

    public static FileConfiguration config = null;


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        config = getConfig();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Expansion().register();
        }
        saveDefaultConfig();
        Objects.requireNonNull(this.getCommand("announce")).setExecutor(new cmd());
    }


        @Override
        public void onDisable () {
            // Plugin shutdown logic
        }
}
