package br.neitan96.swordlevelv3.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public static double randomDouble(double min, double max){
        return min + (max - min) * new Random().nextDouble();
    }

    public static int randomInt(int min, int max){
        return new Random().nextInt(max-min)+min;
    }

    public static boolean calculateProvability(int provability){
        return new Random().nextInt(100) < provability;
    }

    public static List<Damageable> getEntitiesDistance(Location location, double distance){
        final List<Damageable> entities = new ArrayList<>();
        final List<Entity> entitiesWorld = location.getWorld().getEntities();

        for (Entity entitieWorld : entitiesWorld) {
            if(entitieWorld instanceof Damageable &&
                    entitieWorld.getLocation().distance(location) <= distance)
                entities.add((Damageable) entitieWorld);
        }

        return entities;
    }

}
