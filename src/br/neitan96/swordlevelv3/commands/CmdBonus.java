package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.storage.level.StorageLevel;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 06/Nov/2015 01:46
 * Created by Neitan96 on 06/11/15.
 */
public class CmdBonus extends CmdSwordLevel{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(!(commandSender instanceof Player)){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.NoConsole"));
            return true;
        }

        Player player = (Player) commandSender;

        ItemStack itemInHand = player.getItemInHand();

        Group group = SwordLevel.getManager().getGroupConditions(player, itemInHand);

        if(group == null){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.Groups.NoView"));
            return true;
        }

        String permission = group.getPermission(player);
        StorageLevel storageLevel = group.getStorageLevel(SwordUtil.getUUIDPlayer(player), itemInHand);

        SwordLevel.log(commandSender,
            group.getBonus(permission).toString(storageLevel.getLevel())
        );

        return true;
    }

}
