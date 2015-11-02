package br.neitan96.swordlevelv3.events.leveler;

import br.neitan96.swordlevelv3.antitheft.AntiTheft;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 02/Nov/2015 03:25
 * Created by Neitan96 on 02/11/15.
 */
public class OnBreak implements Listener{

    protected final Leveler leveler;

    public OnBreak(Leveler leveler){
        this.leveler = leveler;
    }

    @EventHandler
    protected void onBreak(BlockBreakEvent event){

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(player == null)
            return;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();
        Group group = leveler.getGroup(player, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(player);

        if(permission == null)
            return;

        AntiTheft antiTheft = group.getAntiTheft();

        if(antiTheft != null && !antiTheft.validAction(player, block))
            return;

        Leveling leveling = group.getLeveling(permission);

        if(leveling == null)
            return;

        int xpBreakBlock = leveling.getXpBreakBlock(block.getType());

        if(xpBreakBlock < 1)
            return;

        leveler.leveler(player, permission, itemInHand, group, xpBreakBlock);
    }
}
