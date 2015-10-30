package br.neitan96.swordlevelv3.bonus;

import br.neitan96.swordlevelv3.bonus.bonus.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neitan96 on 28/10/15.
 */
public class BonusList extends Bonus {

    private static Map<String, Class> bonusLoader = new HashMap<>();

    public static void registeDefaultBonus(){
        registerBonus("Damage", BonusDamage.class);
        registerBonus("DamageArea", BonusDamageArea.class);
        registerBonus("PotionEffects", BonusPotionEffects.class);
        registerBonus("Thunder", BonusThunder.class);
        registerBonus("DropBlock", BonusDropBlock.class);
        registerBonus("DropMob", BonusDropMob.class);
    }

    public static void registerBonus(String name, Class aClass){
        bonusLoader.put(name, aClass);
    }

    protected Bonus[] bonus = new Bonus[0];

    public BonusList(ConfigurationSection section) {
        loadFromConfig(section);
    }

    @Override
    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer) {
        for (Bonus bonus : this.bonus) {
            bonus.applyBonus(event, level, killer);
        }
    }

    @Override
    public void applyBonus(BlockBreakEvent event, int level, Player killer) {
        for (Bonus bonus : this.bonus) {
            bonus.applyBonus(event, level, killer);
        }
    }

    @Override
    public void applyBonus(EntityDeathEvent event, int level, Player killer) {
        for (Bonus bonus : this.bonus) {
            bonus.applyBonus(event, level, killer);
        }
    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {
        List<Bonus> bonusList = new ArrayList<>();
        for (String key : section.getKeys(false)) {

            if(bonusLoader.size() == 0)
                registeDefaultBonus();

            if(bonusLoader.containsKey(key)){
                final Class aClass = bonusLoader.get(key);
                try {

                    @SuppressWarnings("unchecked")
                    final Constructor constructor = aClass.getConstructor(ConfigurationSection.class);
                    final Object bonusInstance = constructor.newInstance(section.getConfigurationSection(key));
                    bonusList.add((Bonus) bonusInstance);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        bonus = bonusList.toArray(new Bonus[bonusList.size()]);
    }
}