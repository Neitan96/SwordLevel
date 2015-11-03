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

    public void levelUp(Player player, LivingEntity entity, Block block){

        if(player == null || (entity == null && block == null))
            return;

        String uuidPlayer = SwordUtil.getUUIDPlayer(player);
        ItemStack itemInHand = player.getItemInHand();
        Group group = manager.getGroupConditions(player, itemInHand);

        if(group == null) return;

        String permission = group.getPermission(player);
        AntiTheft antiTheft = group.getAntiTheft();
        Leveling leveling = group.getLeveling(permission);
        StorageLevel storageLevel = group.getStorageLevel(uuidPlayer, itemInHand);
        SwordMessages messages = group.getMessages();
        RewardList reward = group.getReward(permission);
        StorageRank storageRank = group.getStorageRank();

        if(leveling.getLevelMax() > 0 && storageLevel.getLevel() >= leveling.getLevelMax()) return;

        if(antiTheft != null){
            if(block != null && !antiTheft.validAction(player, block)) return;
            if(entity != null && !antiTheft.validAction(player, entity)) return;
        }

        int xpWin = 0;
        int levelWin = 0;

        int levelOld = storageLevel.getLevel();

        int levelNow = levelOld;
        int xpNow = storageLevel.getXp();

        int xpRequired;
        int xpComplete;


        if(entity != null) xpWin += leveling.getXpKillMob(entity.getType());
        if(block != null) xpWin += leveling.getXpBreakBlock(block.getType());

        if(xpWin < 1) return;

        xpNow += xpWin;

        while (xpNow >= (xpRequired = leveling.calculateXPRequired(levelNow))){
            xpNow -= xpRequired;
            levelNow++;
            levelWin++;
        }

        xpComplete = xpRequired-xpNow;

        storageLevel.setXp(xpNow);
        storageLevel.setLevel(levelNow);

        if(messages != null){

            String xpReward = messages.getXpReward(xpWin, xpComplete);
            if(xpReward != null && !xpReward.isEmpty())
                player.sendMessage(messages.getPrefix()+xpReward);

            if(levelWin > 0){
                String levelup = messages.getLevelup(levelOld, levelNow);
                if (levelup != null && !levelup.isEmpty())
                    player.sendMessage(messages.getPrefix() + levelup);
            }

        }

        if(reward != null && levelWin > 0)
            reward.sendRewards(player, levelNow);

        if(storageRank != null)
            storageRank.updateScore(uuidPlayer, levelWin, xpWin, levelNow, xpNow);

    }


    @EventHandler(priority = EventPriority.MONITOR)
    protected void onBreak(BlockBreakEvent event){
        levelUp(event.getPlayer(), null, event.getBlock());
    }


    @EventHandler(priority = EventPriority.MONITOR)
    protected void onKill(EntityDeathEvent event){
        levelUp(event.getEntity().getKiller(), event.getEntity(), null);
    }

}
