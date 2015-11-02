package br.neitan96.swordlevelv3.leveler;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.manager.GroupManager;
import br.neitan96.swordlevelv3.messages.SwordMessages;
import br.neitan96.swordlevelv3.rewards.RewardList;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.storage.ranks.StorageRank;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 02/Nov/2015 03:24
 * Created by Neitan96 on 02/11/15.
 */
public class Leveler{

    protected final GroupManager manager;
    protected final OnBreak onBreak;
    protected final OnKill onKill;

    public Leveler(GroupManager manager){
        this.manager = manager;
        this.onBreak = new OnBreak(this);
        this.onKill = new OnKill(this);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(onBreak, SwordLevel.getInstance());
        pluginManager.registerEvents(onKill, SwordLevel.getInstance());
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

        if(storageLevel == null)
            return;

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

            String xpReward = messages.getXpReward(xpWin, xpRequired);

            if(xpReward != null && !xpReward.isEmpty())
                player.sendMessage(messages.getPrefix()+xpReward);

            if(levelWin > 0){

                String levelup = messages.getLevelup(levelNow - levelWin, levelNow);
                if(levelup != null && !levelup.isEmpty())
                    player.sendMessage(messages.getPrefix()+levelup);

            }

        }

        RewardList reward = group.getReward(permission);

        if(reward != null)
            reward.sendRewards(player, levelNow);

        StorageRank storageRank = group.getStorageRank();

        if(storageRank != null)
            storageRank.updateScore(uuidPlayer, levelWin, xpWin, levelNow, xpNow);
    }

}
