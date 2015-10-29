package br.neitan96.swordlevelv3.bonus.bonus;

import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by neitan96 on 28/10/15.
 */
public class BonusDamage extends Bonus{

    protected double damageMin = 0;
    protected double damageMax = 0;
    protected boolean multiplierDamage = false;

    @Override
    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer) {

        double damageMin = this.damageMin;
        double damageMax = this.damageMax;

        if(multiplierDamage){
            damageMin *= level;
            damageMax *= level;
        }

        final double damageEvent = event.getDamage();
        final double damageRandom = SwordUtil.randomDouble(damageMin, damageMax);

        event.setDamage(damageEvent+damageRandom);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {
        damageMin = section.getDouble("DamageMin", damageMin);
        damageMax = section.getDouble("DamageMax", damageMax);
        multiplierDamage = section.getBoolean("MultiplierDamage", multiplierDamage);
    }
}
