package dev.xernas.autoskinplugin;

import dev.xernas.autoskinplugin.events.SkinJoinQuitManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AutoSkinPlugin extends JavaPlugin {

    private static AutoSkinPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();
        Bukkit.getPluginManager().registerEvents(new SkinJoinQuitManager(), this);
        this.getCommand("autoskin");
    }

    @Override
    public void onDisable() {
    }

    private void setupConfig() {

        File file = new File(getDataFolder() + File.separator + "config.yml");

        if (!file.exists()) {
            getConfig().addDefault("username", "Notch");

            getConfig().options().copyDefaults(true);
            saveConfig();
        } else {
            checkConfig();
            saveConfig();
            reloadConfig();

        }
    }

    public void checkConfig() {
        if(getConfig().get("username") == null) {
            getConfig().set("username", "Notch");
            saveConfig();
            reloadConfig();
        }
    }

    public static AutoSkinPlugin getInstance() {
        return instance;
    }
}
