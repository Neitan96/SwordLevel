package br.neitan96.swordlevelv3.bonus.bonus;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.util.DamageAmor;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by neitan96 on 28/10/15.
 */
public class BonusDamage extends Bonus{

    protected double damageMin = 0;
    protected double damageMax = 0;
    protected boolean multiplierDamage = false;
    protected boolean ignoreAmor = false;

    public BonusDamage(ConfigurationSection section){
        loadFromConfig(section);
    }

    @Override
    public void applyBonus(EntityDamageByEntityEvent event, int level, Player killer) {

        double damageMin = this.damageMin;
        double damageMax = this.damageMax;

        if(multiplierDamage){
            damageMin *= level;
            damageMax *= level;
        }

        double damageEvent = event.getDamage();
        double damageRandom = SwordUtil.randomDouble(damageMin, damageMax);

        if(!ignoreAmor && event.getEntity() instanceof Player){
            Player entity = (Player) event.getEntity();
            PlayerInventory inventory = entity.getInventory();
            damageRandom =  DamageAmor.reduceDamage(inventory, damageRandom);

            ItemStack[] playerSet = {
                    inventory.getHelmet(), inventory.getChestplate(),
                    inventory.getLeggings(), inventory.getBoots()};

            for (ItemStack itemStack : playerSet){
                itemStack.setDurability((short) (itemStack.getDurability()+damageRandom));
            }

        }

        event.setDamage(damageEvent+damageRandom);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {
        damageMin = section.getDouble("DamageMin", damageMin);
        damageMax = section.getDouble("DamageMax", damageMax);
        multiplierDamage = section.getBoolean("MultiplierDamage", multiplierDamage);
        ignoreAmor = section.getBoolean("IgnoreAmor", ignoreAmor);
    }

    @Override
    public String[] toString(int level){

        double damageMin = this.damageMin;
        double damageMax = this.damageMax;

        if(multiplierDamage){
            damageMin *= level;
            damageMax *= level;
        }

        return SwordLevel.getMsgs("Bonus.Damage", "damageMin", String.valueOf(damageMin), "damageMax", String.valueOf(damageMax));
    }
}
