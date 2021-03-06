package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.leveling.Leveling;
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
 * Since: 06/Nov/2015 08:20
 * Created by Neitan96 on 06/11/15.
 */
public class CmdViewPlayer extends CmdSwordLevel{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(returnNoPermission(commandSender, "viewplayer"))
            return true;

        if(strings.length < 2){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.NotFoundPlayer"));
            return true;
        }

        Player player = SwordUtil.getPlayer(strings[1]);

        if(player == null){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.NotFoundPlayer"));
            return true;
        }

        ItemStack itemInHand = player.getItemInHand();

        Group group = SwordLevel.getManager().getGroupConditions(player, itemInHand);

        if(group == null){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.Groups.ItemInvalid"));
            return true;
        }

        String permission = group.getPermission(player);
        StorageLevel storageLevel = group.getStorageLevel(SwordUtil.getUUIDPlayer(player), itemInHand);
        Leveling leveling = group.getLeveling(permission);

        SwordLevel.log(commandSender, group.getGroupName()+":");

        String viewLevel = group.getMessages().getViewLevel(storageLevel.getLevel(), storageLevel.getXp(),
                leveling.calculateXPRequired(storageLevel.getLevel()));

        SwordLevel.log(commandSender, viewLevel);

        SwordLevel.log(commandSender, SwordLevel.getMsgs("Bonus.Start"));
        SwordLevel.log(commandSender,
                group.getBonus(permission).toString(storageLevel.getLevel())
        );

        return true;
    }
}
