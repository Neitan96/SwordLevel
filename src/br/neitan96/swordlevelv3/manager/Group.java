package br.neitan96.swordlevelv3.manager;

import br.neitan96.swordlevelv3.antitheft.AntiTheft;
import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.conditions.Conditions;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.messages.SwordMessages;
import br.neitan96.swordlevelv3.rewards.RewardList;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.storage.ranks.StorageRank;
import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 01/Nov/2015 04:45
 * Created by Neitan96 on 01/11/15.
 */
public interface Group extends ConfigLoader{

    String getGroupName();

    String[] getPermissions();

    Conditions getConditions();

    StorageLevel getStorageLevel(String player, ItemStack itemStack);

    SwordMessages getMessages();

    StorageRank getStorageRank();

    AntiTheft getAntiTheft();


    String getPermission(Player player);


    Leveling getLeveling(String permission);

    Bonus getBonus(String permission);

    RewardList getReward(String permission);

}
