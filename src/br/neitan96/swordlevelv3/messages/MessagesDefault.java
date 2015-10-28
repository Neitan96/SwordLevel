package br.neitan96.swordlevelv3.messages;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neitan96 on 28/10/15.
 */
public class MessagesDefault implements SwordMessages{

    protected Map<String, String> messages;

    public MessagesDefault(ConfigurationSection section) {
        loadFromConfig(section);
    }

    @Override
    public String getPrefix() {
        if(messages.containsKey("Prefix")){

            String message = messages.get("Prefix");
            return message.isEmpty() ? null : message;
        }
        return null;
    }

    @Override
    public String getLevelup(int levelNow, int levelUp) {
        if(messages.containsKey("LevelUp")){

            String message = messages.get("LevelUp");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(levelNow))
                    .replace("{1}", String.valueOf(levelUp));
        }
        return null;
    }

    @Override
    public String getXpReward(int xpWin, int xpMissing) {
        if(messages.containsKey("XpReward")){

            String message = messages.get("XpReward");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(xpWin))
                    .replace("{1}", String.valueOf(xpMissing));
        }
        return null;
    }

    @Override
    public String getXpLimitSamePlayer(int playerCount, int time) {
        if(messages.containsKey("XpLimitSamePlayer")){

            String message = messages.get("XpLimitSamePlayer");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(playerCount))
                    .replace("{1}", String.valueOf(time));
        }
        return null;
    }

    @Override
    public String getXpLimitAnyPlyer(int playerCount, int time) {
        if(messages.containsKey("XpLimitAnyPlayer")){

            String message = messages.get("XpLimitAnyPlayer");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(playerCount))
                    .replace("{1}", String.valueOf(time));
        }
        return null;
    }

    @Override
    public String getXpLimitMob(int mobcount, int time) {
        if(messages.containsKey("XpLimitMob")){

            String message = messages.get("XpLimitMob");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(mobcount))
                    .replace("{1}", String.valueOf(time));
        }
        return null;
    }

    @Override
    public String getXpLimitBlock(int blockCount, int time) {
        if(messages.containsKey("XpLimitBlock")){

            String message = messages.get("XpLimitBlock");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(blockCount))
                    .replace("{1}", String.valueOf(time));
        }
        return null;
    }

    @Override
    public String getViewLevel(int levelNow, int xpNow, int xpMissing) {
        if(messages.containsKey("ViewLevel")){

            String message = messages.get("ViewLevel");

            if(message.isEmpty())
                return null;

            return message
                    .replace("{0}", String.valueOf(levelNow))
                    .replace("{1}", String.valueOf(xpNow))
                    .replace("{2}", String.valueOf(xpMissing));
        }
        return null;
    }

    @Override
    public void loadFromConfig(ConfigurationSection section) {

        Map<String, String> messages = new HashMap<>();

        for (String key : section.getKeys(false)) {
            try{

                messages.put(key, section.getString(key));

            }catch (Exception ignored){}
        }

        this.messages = messages;
    }

}
