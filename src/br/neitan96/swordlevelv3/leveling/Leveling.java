package br.neitan96.swordlevelv3.leveling;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 31/Out/2015 18:32
 * Created by Neitan96 on 31/10/15.
 */
public interface Leveling extends ConfigLoader{

    int getXpKillMob();

    int getXpKillMob(EntityType entityType);

    int getXpKillPlayer();

    int getXpKillPlayer(String player);

    int getXpBreakBlock(Material material);

    int getLevelMax();

    String getXpRequired();

    int calculateXPRequired(int level);

}
