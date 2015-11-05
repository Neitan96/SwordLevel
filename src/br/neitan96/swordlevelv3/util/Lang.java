package br.neitan96.swordlevelv3.util;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 04/Nov/2015 13:45
 * Created by Neitan96 on 04/11/15.
 */
public class Lang{

    protected Map<String, String[]> messages = new HashMap<>();

    public Lang(){}

    public Lang(Map<String, String[]> messages){
        this.messages = messages;
    }

    public Lang(ConfigurationSection section){
        loadMsgs(null, section);
    }

    protected void loadMsgs(String prefix, ConfigurationSection section){
        for (String path : section.getKeys(false)){
            String fullPath = (prefix != null ? prefix+"." : "") + path;

            if(section.isConfigurationSection(path))
                loadMsgs(fullPath, section.getConfigurationSection(path));

            else if(section.isString(path))
                messages.put(fullPath, new String[]{section.getString(path)});

            else if(section.isList(path)){
                List<String> stringList = section.getStringList(path);
                messages.put(fullPath, stringList.toArray(new String[stringList.size()]));
            }
        }
    }

    public String[] getMsgs(String path){
        return messages.get(path);
    }

    public String[] getMsgs(String path, String... binds){
        String[] msgs = getMsgs(path);
        if(msgs != null){
            for (int i = 1; i < binds.length; i+=2){
                for (int j = 0; j < msgs.length; j++){
                    msgs[j] = msgs[j].replace("{"+binds[i-1]+"}", binds[i]);
                }
            }

            return msgs;
        }
        return new String[0];
    }

}
