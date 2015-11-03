package br.neitan96.swordlevelv3.manager;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import br.neitan96.swordlevelv3.util.DValue;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 01/Nov/2015 18:12
 * Created by Neitan96 on 01/11/15.
 */
public class GroupManager implements ConfigLoader{

    protected List<Group> groupList = new ArrayList<>();
    protected DValue<String, String> groupDefault = null;

    public List<Group> getGroupList(){
        return groupList;
    }

    public Group getGroup(String name){
        for (Group group : groupList){
            if(group.getGroupName().equals(name))
                return group;
        }
        return null;
    }

    public Group getGroupConditions(Player player, ItemStack item){
        if(player == null || item == null)
            return null;
        for (Group group : groupList){
            if(group.getConditions().conditionValid(player, item)){

                if(group.getPermission(player) == null)
                    return null;

                if(player.getGameMode() == GameMode.CREATIVE && !group.allowCreative())
                    return null;

                return group;
            }
        }
        if(groupDefault != null && player.hasPermission(groupDefault.getValue1())){
            Group group = getGroup(groupDefault.getValue2());
            if(player.getGameMode() != GameMode.CREATIVE || group.allowCreative())
                return group;
        }
        return null;
    }

    public void addGroup(Group group){
        groupList.add(group);
    }

    public void removeGroup(Group group){
        groupList.remove(group);
    }

    public void removeGroup(String groupName){
        Group group = getGroup(groupName);
        if(group != null)
            removeGroup(group);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        Set<String> groupsNames = section.getKeys(false);
        for (String groupName : groupsNames){
            addGroup(
                    new GroupDefault(section.getConfigurationSection(groupName))
            );
        }
    }

    public void loadDefault(ConfigurationSection section){
        groupDefault = new DValue<>(
                section.getString("Permission"), section.getString("Group")
        );
    }
}
