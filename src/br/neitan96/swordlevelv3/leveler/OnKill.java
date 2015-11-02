package br.neitan96.swordlevelv3.leveler;

import br.neitan96.swordlevelv3.antitheft.AntiTheft;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 02/Nov/2015 03:24
 * Created by Neitan96 on 02/11/15.
 */
public class OnKill implements Listener{

    protected final Leveler leveler;

    public OnKill(Leveler leveler){
        this.leveler = leveler;
    }

    @EventHandler
    protected void onKill(EntityDeathEvent event){

        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        if(killer == null)
            return;

        String uuidPlayer = SwordUtil.getUUIDPlayer(killer);
        ItemStack itemInHand = killer.getItemInHand();
        Group group = leveler.getGroup(killer, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(killer);

        if(permission == null)
            return;

        AntiTheft antiTheft = group.getAntiTheft();

        if(antiTheft != null &&  !antiTheft.validAction(uuidPlayer, entity))
            return;

        Leveling leveling = group.getLeveling(permission);

        if(leveling == null)
            return;

        int xpKillMob = leveling.getXpKillMob(entity.getType());

        if(xpKillMob < 1)
            return;

        leveler.leveler(killer, permission, itemInHand, group, xpKillMob);

    }

}
