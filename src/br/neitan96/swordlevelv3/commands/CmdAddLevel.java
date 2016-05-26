package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.leveling.Leveling;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 06/Nov/2015 07:16
 * Created by Neitan96 on 06/11/15.
 */
public class CmdAddLevel extends CmdChanger{

    protected CmdAddLevel(){
        super("addlevel");
    }

    @Override
    public boolean onCmd(CommandSender commandSender, Command command, String s,
                         String[] strings, int num, Player player, Group group, boolean force){

        StorageLevel storageLevel = group.getStorageLevel(SwordUtil.getUUIDPlayer(player), player.getItemInHand());

        int newLevel = storageLevel.getLevel()+num;

        if(!force){
            Leveling leveling = group.getLeveling(group.getPermission(player));
            newLevel = newLevel > leveling.getLevelMax() ? leveling.getLevelMax() : newLevel;
        }

        storageLevel.setLevel(newLevel);

        SwordLevel.log(commandSender, SwordLevel.getMsgs("Groups.LevelUpdate"));

        return true;
    }
}
