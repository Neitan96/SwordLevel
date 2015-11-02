package br.neitan96.swordlevelv3.antitheft;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 20:35
 * Created by Neitan96 on 30/10/15.
 */
public interface AntiTheft extends ConfigLoader{

    int getCountSamePlayer();

    int getTimeSamePlayer();


    int getCountAnyPlayers();

    int getTimeAnyPlayers();


    int getCountMob();

    int getTimeMob();


    int getCountBlock();

    int getTimeBlock();


    boolean validAction(Player player, Player entity);

    boolean validAction(Player player, LivingEntity entity);

    boolean validAction(Player player, Block block);


    void clearCache();


    void loadFromConfig(ConfigurationSection section);
}
