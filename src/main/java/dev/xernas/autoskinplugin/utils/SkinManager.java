package dev.xernas.autoskinplugin.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.xernas.autoskinplugin.AutoSkinPlugin;
import dev.xernas.autoskinplugin.Skin;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Base64;

public class SkinManager {

    private static final AutoSkinPlugin plugin = AutoSkinPlugin.getInstance();
    public static void changeSkin(Player p, Skin skin) {
        CraftPlayer craftPlayer = (CraftPlayer) p;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.hidePlayer(plugin, p);
                    }
                }, 0
        );


        gameProfile.getProperties().clear();
        gameProfile.getProperties().put(skin.getName(), getSkinProperty(skin));
        p.sendMessage(new String(Base64.getDecoder().decode(skin.getValue())));


        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.showPlayer(plugin, p);
                    }
                }, 1500
        );
    }

    private static Property getSkinProperty(Skin skin) {
        return new Property(skin.getName(), skin.getValue(), skin.getSignature());
    }

    public static Skin getMojangSkin(String uuid) {
        JSONObject json = HttpUtils.getRequest("https://sessionserver.mojang.com/session/minecraft/profile/"+ uuid + "?unsigned=false");
        JSONArray properties = (JSONArray) json.get("properties");
        JSONObject realProperties = (JSONObject) properties.get(0);
        String name = "textures";
        String value = (String) realProperties.get("value");
        String rawSignature = (String) realProperties.get("signature");
        String signature = rawSignature.replace("\\/", "/");
        return new Skin(name, value, signature);
    }
    public static String getUUIDFromName(String name) {
        JSONObject json = HttpUtils.getRequest("https://api.mojang.com/users/profiles/minecraft/" + name);
        String id = (String) json.get("id");
        return id;
    }

    public static Skin getSkinFromUserName(String name) {
        String UUID = getUUIDFromName(name);
        return getMojangSkin(UUID);
    }
}
