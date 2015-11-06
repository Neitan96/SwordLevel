package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 04/Nov/2015 13:12
 * Created by Neitan96 on 04/11/15.
 */
public abstract class CmdSwordLevel implements CommandExecutor{

    protected static Map<String, Map<String, Object>> commands = null;

    protected CmdSwordLevel(){
        if(commands == null)
            commands = SwordLevel.getInstance().getDescription().getCommands();
    }

    protected Map<String, Object> getInfosCommands(String command){
        return commands.get(command);
    }

    protected boolean returnNoPermission(CommandSender sender, String commandName){
        if(commands.containsKey(commandName)
                && commands.get(commandName).containsKey("permission")
                && !sender.hasPermission(commands.get(commandName).get("permission").toString())){
            SwordLevel.logError(sender, SwordLevel.getMsgs("Warings.NoPermission"));
            return true;
        }
        return false;
    }
}
