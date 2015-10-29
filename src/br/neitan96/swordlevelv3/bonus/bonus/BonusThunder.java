package br.neitan96.swordlevelv3.bonus.bonus;

import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by neitan96 on 29/10/15.
 */
public class BonusThunder extends Bonus{

    protected int provability = 100;
    protected boolean multiplierProvability = false;
    protected int levelAllow = 1;

    public BonusThunder(ConfigurationSection section) {
        loadFromConfig(section);
    }

    @Override
    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer) {

        if(level < levelAllow ||
                !SwordUtil.calculateProvability(multiplierProvability ? provability * level : provability))
            return;

        event.getEntity().getLocation().getWorld().strikeLightning(
                event.getEntity().getLocation()
        );
    }

    @Override
    public void applyBonus(BlockBreakEvent event, int level, Player killer) {

    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {
        provability = section.getInt("Provability", provability);
        multiplierProvability = section.getBoolean("MultiplierProvability", multiplierProvability);
        levelAllow = section.getInt("LevelAllow", levelAllow);
    }
}
