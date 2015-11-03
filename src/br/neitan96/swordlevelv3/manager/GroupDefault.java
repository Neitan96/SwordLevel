package br.neitan96.swordlevelv3.manager;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.antitheft.AntiTheft;
import br.neitan96.swordlevelv3.antitheft.AntiTheftDefault;
import br.neitan96.swordlevelv3.bonus.Bonus;
import br.neitan96.swordlevelv3.bonus.BonusList;
import br.neitan96.swordlevelv3.conditions.Conditions;
import br.neitan96.swordlevelv3.conditions.ConditionsDefault;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.leveling.LevelingDefault;
import br.neitan96.swordlevelv3.messages.MessagesDefault;
import br.neitan96.swordlevelv3.messages.SwordMessages;
import br.neitan96.swordlevelv3.rewards.RewardList;
import br.neitan96.swordlevelv3.rewards.RewardsDefault;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.storage.level.StorageMake;
import br.neitan96.swordlevelv3.storage.ranks.RankerDefault;
import br.neitan96.swordlevelv3.storage.ranks.StorageRank;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 01/Nov/2015 17:45
 * Created by Neitan96 on 01/11/15.
 */
public class GroupDefault implements Group{

    protected String groupName = null;
    protected String[] permissions = new String[0];
    protected Conditions  conditions = null;
    protected StorageMake storageMake = null;
    protected SwordMessages messages = null;
    protected StorageRank storageRank = null;
    protected AntiTheft antiTheft = null;
    protected Map<String, Leveling> levelings = new HashMap<>();
    protected Map<String, Bonus> bonuses = new HashMap<>();
    protected Map<String, RewardList> rewardLists = new HashMap<>();
    protected boolean allowCreative = false;

    public GroupDefault(ConfigurationSection section){
        loadFromConfig(section);
    }

    @Override
    public String getGroupName(){
        return groupName;
    }

    @Override
    public boolean allowCreative(){
        return allowCreative;
    }

    @Override
    public String[] getPermissions(){
        return permissions;
    }

    @Override
    public Conditions getConditions(){
        return conditions;
    }

    @Override
    public StorageLevel getStorageLevel(String player, ItemStack itemStack){
        return storageMake.makeStorage(player, itemStack);
    }

    @Override
    public SwordMessages getMessages(){
        return messages;
    }

    @Override
    public StorageRank getStorageRank(){
        return storageRank;
    }

    @Override
    public AntiTheft getAntiTheft(){
        return antiTheft;
    }

    @Override
    public String getPermission(Player player){
        for (String permission : permissions){
            if(player.hasPermission(permission))
                return permission;
        }

        return null;
    }

    @Override
    public Leveling getLeveling(String permission){
        return levelings.get(permission);
    }

    @Override
    public Bonus getBonus(String permission){
        return bonuses.get(permission);
    }

    @Override
    public RewardList getReward(String permission){
        return rewardLists.get(permission);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){

        groupName = section.getCurrentPath().substring(
                section.getCurrentPath().lastIndexOf(".") + 1
        );
        SwordLevel.log("Lendo configurações do grupo: "+groupName, 1);

        allowCreative = section.getBoolean("AllowCreative", allowCreative);

        if(section.contains("Conditions")){
            conditions = new ConditionsDefault(
                    section.getConfigurationSection("Conditions")
            );
        }else{
            SwordLevel.logError("Grupo inválido, não encontrado Conditions.");
        }

        if(section.contains("Store")){
            storageMake = new StorageMake(
                    section.getConfigurationSection("Store"), groupName
            );
        }else{
            SwordLevel.logError("Grupo inválido, não encontrado Store.");
        }

        if(section.contains("Messages")){
            messages = new MessagesDefault(
                    section.getConfigurationSection("Messages")
            );
        }else{
            SwordLevel.log("Grupo não tem Messages.", 2);
        }

        if(section.contains("Ranks")){
            storageRank = new RankerDefault(
                    section.getConfigurationSection("Ranks"), groupName
            );
        }else{
            SwordLevel.log("Grupo não tem Ranks.", 2);
        }

        if(section.contains("AntiTheft")){
            antiTheft = new AntiTheftDefault(
                    section.getConfigurationSection("AntiTheft")
            );
        }else{
            SwordLevel.log("Grupo não tem AntiTheft.", 2);
        }

        if(section.contains("Permissions")){
            ConfigurationSection subGroups = section.getConfigurationSection("Permissions");
            Set<String> subGroupsNames = subGroups.getKeys(false);
            this.permissions = new String[subGroupsNames.size()];

            int i = 0;
            for (String subGroupName : subGroupsNames){
                String permission = subGroups.getString(subGroupName + ".Permission");
                permissions[i++] = permission;
                SwordLevel.log("Lendo sub-grupo: "+permission, 2);

                if(subGroups.contains(subGroupName+".Leveling"))
                    levelings.put(
                            permission,
                            new LevelingDefault(subGroups.getConfigurationSection(subGroupName+".Leveling"))
                    );
                else SwordLevel.logError("Sub-Grupo inválido, não encontrado Leveling.");
                if(subGroups.contains(subGroupName+".Bonus"))
                    bonuses.put(
                            permission,
                            new BonusList(subGroups.getConfigurationSection(subGroupName+".Bonus"))
                    );
                else SwordLevel.logError("Sub-Grupo inválido, não encontrado Bonus.");
                if(subGroups.contains(subGroupName+".Rewards"))
                    rewardLists.put(
                            permission,
                            new RewardsDefault(subGroups.getConfigurationSection(subGroupName+".Rewards"))
                    );
                else SwordLevel.log("Sub-Grupo não tem Rewards.", 2);
            }

        }else{
            SwordLevel.logError("Grupo inválido, não encontrado Permissions.");
        }
    }
}
