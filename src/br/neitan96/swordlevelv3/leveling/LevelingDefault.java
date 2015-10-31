package br.neitan96.swordlevelv3.leveling;

import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 31/Out/2015 18:50
 * Created by Neitan96 on 31/10/15.
 */
public class LevelingDefault implements Leveling{

    protected int xpKillMob = 0;
    protected Map<EntityType, Integer> xpCustomMobs = new HashMap<>();
    protected int xpKillPlayer = 0;
    protected Map<String, Integer> xpCustomPlayers = new HashMap<>();
    protected Map<Material, Integer> xpCustomBlocks = new HashMap<>();

    protected String leveling = null;
    protected int levelMax = Integer.MAX_VALUE;

    @Override
    public int getXpKillMob(){
        return xpKillMob;
    }

    @Override
    public int getXpKillMob(EntityType entityType){
        return xpCustomMobs.containsKey(entityType) ?
                xpCustomMobs.get(entityType) : getXpKillMob();
    }

    @Override
    public int getXpKillPlayer(){
        return xpKillPlayer;
    }

    @Override
    public int getXpKillPlayer(String player){
        return xpCustomPlayers.containsKey(player) ?
                xpCustomPlayers.get(player) : getXpKillPlayer();
    }

    @Override
    public int getXpBreakBlock(Material material){
        return xpCustomBlocks.containsKey(material) ?
                xpCustomBlocks.get(material) : 0;
    }

    @Override
    public int getLevelMax(){
        return levelMax;
    }

    @Override
    public String getXpRequired(){
        return leveling;
    }

    @Override
    public int calculateXPRequired(int level){
        String required = getXpRequired().replace("{level}", String.valueOf(level));
        Object eval = SwordUtil.eval(required);
        return (int) eval;
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        xpKillMob = section.getInt("Kills.Mobs", xpKillMob);

        if(section.contains("Kills.CustomMobs")){

            Set<String> keys =
                    section.getConfigurationSection("Kills.CustomMobs").getKeys(false);

            for (String key : keys){
                EntityType entityType = EntityType.valueOf(key);
                int xp = section.getInt("Kills.CustomMobs." + key);
                xpCustomMobs.put(entityType, xp);
            }

        }

        xpKillPlayer = section.getInt("Kills.Players", xpKillPlayer);

        if(section.contains("Kills.CustomPlayers")){

            Set<String> keys =
                    section.getConfigurationSection("Kills.CustomPlayers").getKeys(false);

            for (String key : keys){
                int xp = section.getInt("Kills.CustomPlayers." + key);
                xpCustomPlayers.put(key, xp);
            }

        }

        if(section.contains("Blocks")){

            Set<String> keys =
                    section.getConfigurationSection("Blocks").getKeys(false);

            for (String key : keys){
                Material material = Material.valueOf(key);
                int xp = section.getInt("Blocks." + key);
                xpCustomBlocks.put(material, xp);
            }

        }

        leveling = section.getString("LevelUp", leveling);
        levelMax = section.getInt("LevelMax", levelMax);

    }
}
