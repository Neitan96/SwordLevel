package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.manager.Group;
import br.neitan96.swordlevelv3.util.SwordUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 06/Nov/2015 07:08
 * Created by Neitan96 on 06/11/15.
 */
public abstract class CmdChanger extends CmdSwordLevel{

    protected final String commandName;

    protected CmdChanger(String commandName){
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(returnNoPermission(commandSender, commandName))
            return true;

        if(strings.length < 3 && !(commandSender instanceof Player)){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.NoConsole"));
            return true;
        }

        int num;
        try{
            num = Integer.valueOf(strings[1]);
        }catch (Exception e){
            SwordLevel.log(commandSender, SwordLevel.getMsgs("Warings.NumInvalid"));
            return true;
        }

        Player player = strings.length > 3 ? SwordUtil.getPlayer(strings[3]) : (Player) commandSender;

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

        boolean force = strings.length > 2 && (strings[2].equalsIgnoreCase("s") ||
                strings[3].equalsIgnoreCase("y") ||
                strings[3].equalsIgnoreCase("sim") ||
                strings[3].equalsIgnoreCase("yes") ||
                strings[3].equalsIgnoreCase("true")
        );

        return onCmd(commandSender, command, s, strings, num, player, group, force);
    }


    public abstract boolean onCmd(CommandSender commandSender, Command command, String s, String[] strings,
                                  int num, Player player, Group group, boolean force);
}
