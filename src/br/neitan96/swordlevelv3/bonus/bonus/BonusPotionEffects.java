package br.neitan96.swordlevelv3.bonus.bonus;

import br.neitan96.swordlevelv3.bonus.Bonus;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neitan96 on 28/10/15.
 */
public class BonusPotionEffects extends Bonus{

    protected SwordEffect[] effects = new SwordEffect[0];

    @Override
    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer) {
        for (SwordEffect effect : effects) {
            effect.applyBonus(event, level, killer);
        }
    }

    @Override
    public void applyBonus(BlockBreakEvent event, int level, Player killer) {

    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {
        List<SwordEffect> effects = new ArrayList<>();
        for (String effect : section.getKeys(false)) {
            effects.add(
                    new SwordEffect(section.getConfigurationSection(effect))
            );
        }
        this.effects = effects.toArray(new SwordEffect[effects.size()]);
    }
}
