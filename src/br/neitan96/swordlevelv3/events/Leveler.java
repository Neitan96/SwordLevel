package br.neitan96.swordlevelv3.events;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.antitheft.AntiTheft;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.manager.GroupManager;
import br.neitan96.swordlevelv3.messages.SwordMessages;
import br.neitan96.swordlevelv3.rewards.RewardList;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.storage.ranks.StorageRank;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 02/Nov/2015 03:24
 * Created by Neitan96 on 02/11/15.
 */
public class Leveler implements Listener{

    protected final GroupManager manager;

    public Leveler(GroupManager manager){
        this.manager = manager;

        Bukkit.getPluginManager().registerEvents(this, SwordLevel.getInstance());
    }

    public GroupManager getManager(){
        return manager;
    }

    public Group getGroup(Player player, ItemStack itemStack){
        return manager.getGroupConditions(player, itemStack);
    }

    public void leveler(Player player, String permission, ItemStack itemInHand, Group group, int xpWin){

        Leveling leveling = group.getLeveling(permission);

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);

        StorageLevel storageLevel = group.getStorageLevel(uuidPlayer, itemInHand);

        int levelNow = storageLevel.getLevel();
        int levelWin = 0;
        int xpNow = storageLevel.getXp()+xpWin;

        int xpRequired;

        while ((xpRequired = leveling.calculateXPRequired(levelNow)) <= xpNow){
            levelWin++;
            xpNow -= xpRequired;
        }

        levelNow += levelWin;

        storageLevel.setXp(xpNow);

        if(levelWin > 0)
            storageLevel.setLevel(levelNow);

        SwordMessages messages = group.getMessages();

        if(messages != null){

            String xpReward = messages.getXpReward(xpWin, xpRequired-xpNow);

            if(xpReward != null && !xpReward.isEmpty())
                player.sendMessage(messages.getPrefix()+xpReward);

            if(levelWin > 0){

                String levelup = messages.getLevelup(levelNow - levelWin, levelNow);
                if(levelup != null && !levelup.isEmpty())
                    player.sendMessage(messages.getPrefix()+levelup);

            }

        }

        RewardList reward = group.getReward(permission);

        if(reward != null && levelWin > 0)
            reward.sendRewards(player, levelNow);

        StorageRank storageRank = group.getStorageRank();

        if(storageRank != null)
            storageRank.updateScore(uuidPlayer, levelWin, xpWin, levelNow, xpNow);
    }


    @EventHandler
    protected void onBreak(BlockBreakEvent event){

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(player == null)
            return;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();
        Group group = manager.getGroupConditions(player, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(player);
        AntiTheft antiTheft = group.getAntiTheft();

        if(antiTheft != null && !antiTheft.validAction(player, block))
            return;

        Leveling leveling = group.getLeveling(permission);

        int xpBreakBlock = leveling.getXpBreakBlock(block.getType());

        if(xpBreakBlock < 1)
            return;

        leveler(player, permission, itemInHand, group, xpBreakBlock);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    protected void onKill(EntityDeathEvent event){

        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        if(killer == null)
            return;

        String uuidPlayer = SwordUtil.getUUIDPlayer(killer);
        ItemStack itemInHand = killer.getItemInHand();
        Group group = manager.getGroupConditions(killer, itemInHand);

        if(group == null)
            return;

        String permission = group.getPermission(killer);
        AntiTheft antiTheft = group.getAntiTheft();

        if(antiTheft != null &&  !antiTheft.validAction(killer, entity))
            return;

        Leveling leveling = group.getLeveling(permission);

        int xpKillMob = leveling.getXpKillMob(entity.getType());

        if(xpKillMob < 1)
            return;

        leveler(killer, permission, itemInHand, group, xpKillMob);

    }

}
