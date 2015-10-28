package br.neitan96.swordlevelv3.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by neitan96 on 28/10/15.
 */
public class SwordUtil {

    public static boolean uuid = true;

    public static String getUUIDPlayer(OfflinePlayer player){
        if(!uuid)
            return player.getName();
        else{
            try {
                final Method getUniqueId = player.getClass().getMethod("getUniqueId");
                final Object uuid = getUniqueId.invoke(player);
                return uuid.toString();
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
                return player.getName();
            }
        }
    }

    public static Player getPlayer(String uuid){
        Object uuidplayer = SwordUtil.uuid ? UUID.fromString(uuid) : uuid;
        try {
            final Method playerMethod = Bukkit.class.getMethod("getPlayer", uuidplayer.getClass());
            return (Player) playerMethod.invoke(null, uuidplayer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
