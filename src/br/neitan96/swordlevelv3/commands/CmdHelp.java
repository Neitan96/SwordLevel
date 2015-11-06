package br.neitan96.swordlevelv3.commands;

import br.neitan96.swordlevelv3.SwordLevel;
import br.neitan96.swordlevelv3.storage.ranks.RankType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 04/Nov/2015 13:10
 * Created by Neitan96 on 04/11/15.
 */
public class CmdHelp extends CmdSwordLevel{

    protected Map<String, String[]> commandsHelpers = new HashMap<>();
    protected Map<String, String> commandsPermissions = new HashMap<>();

    protected Map<String, CommandExecutor> subscommands = new HashMap<>();

    public CmdHelp(){

        subscommands.put("view", new CmdView());
        subscommands.put("bonus", new CmdBonus());
        subscommands.put("toplevelups", new CmdRankViews(RankType.LEVELS_UPS, "toplevelups"));
        subscommands.put("topxpwin", new CmdRankViews(RankType.XP_GAINED, "topxpwin"));
        subscommands.put("toplevelmax", new CmdRankViews(RankType.LEVEL_MAX, "toplevelmax"));

        for (Map.Entry<String, Map<String, Object>> commandInfos : commands.entrySet()){

            Object permission = commandInfos.getValue().get("permission");

            commandsPermissions.put(commandInfos.getKey(),
                    permission != null ? String.valueOf(permission): null);

            List<String> binds = new ArrayList<>();

            binds.add("command");
            binds.add(commandInfos.getKey());

            for (Map.Entry<String, Object> infos : commandInfos.getValue().entrySet()){
                binds.add(infos.getKey());
                binds.add(infos.getValue().toString());
            }

            String[] binds2 = binds.toArray(new String[binds.size()]);

            commandsHelpers.put(commandInfos.getKey(), SwordLevel.getLang().getMsgs("Help.Format", binds2));

        }

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length > 0)
            return subscommands.containsKey(strings[0])
                    && subscommands.get(strings[0]).onCommand(commandSender, command, s, strings);

        List<String> commands = new ArrayList<>();

        for (Map.Entry<String, String> commandPerm : commandsPermissions.entrySet()){
            if(commandPerm.getValue() == null || commandSender.hasPermission(commandPerm.getValue()))
                commands.add(commandPerm.getKey());
        }

        if(commands.size() < 1){
            commandSender.sendMessage(SwordLevel.getLang().getMsgs("Help.NoCommand"));

        }else{
            commandSender.sendMessage(SwordLevel.getLang().getMsgs("Help.Start"));

            for (String commandName : commands)
                commandSender.sendMessage(commandsHelpers.get(commandName));

            commandSender.sendMessage(SwordLevel.getLang().getMsgs("Help.End"));
        }

        return true;
    }
}
