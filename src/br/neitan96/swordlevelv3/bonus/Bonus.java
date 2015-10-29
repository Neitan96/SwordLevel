package br.neitan96.swordlevelv3.bonus;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by neitan96 on 28/10/15.
 */
public abstract class Bonus implements ConfigLoader{

    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer){

    }

    public void applyBonus(EntityDeathEvent event, int level, Player killer){

    }

    public void applyBonus(BlockBreakEvent event, int level, Player killer){

    }

}
