package dev.xernas.autoskinplugin.events;

import dev.xernas.autoskinplugin.AutoSkinPlugin;
import dev.xernas.autoskinplugin.utils.SkinManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SkinJoinQuitManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("autoskinplugin.customskin")) {
            SkinManager.changeSkin(player, SkinManager.getSkinFromUserName(AutoSkinPlugin.getInstance().getConfig().getString("username")));
        }
    }

}