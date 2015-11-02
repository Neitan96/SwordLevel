package br.neitan96.swordlevelv3;

import br.neitan96.swordlevelv3.connector.Connector;
import br.neitan96.swordlevelv3.connector.ConnectorBase;
import br.neitan96.swordlevelv3.events.leveler.Leveler;
import br.neitan96.swordlevelv3.manager.GroupManager;
import br.neitan96.swordlevelv3.util.SwordUtil;
import br.neitan96.swordlevelv3.util.YamlUTF8;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by neitan96 on 26/10/15.
 */
public class SwordLevel extends JavaPlugin{

    private static SwordLevel instance = null;
    private static YamlUTF8 config = null;
    private static Connector connector = null;

    private static String prefixConsole = null;
    private static String prefixCommands = null;
    private static String prefixErrors = null;

    private static Leveler leveler = null;

    @Override
    public void onEnable(){
        instance = this;

        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists())
            saveResource("config.yml", false);

        try{
            config = new YamlUTF8(configFile);
        }catch (IOException e){
            e.printStackTrace();
        }catch (InvalidConfigurationException e){
            e.printStackTrace();
        }

        if(config == null){
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        ConfigurationSection section = config.getConfigurationSection("Plugin");

        SwordUtil.uuid = section.getBoolean("UUID", false);

        section = section.getConfigurationSection("Prefix");
        prefixConsole = section.getString("PrefixConsole");
        prefixCommands = section.getString("PrefixCommands");
        prefixErrors = section.getString("PrefixErrors");

        leveler = new Leveler(
                new GroupManager()
        );
        leveler.getManager().loadFromConfig(
                config.getConfigurationSection("Grupos")
        );

    }

    @Override
    public void onDisable(){
        HandlerList.unregisterAll(this);
        if(connector != null)
            connector.closeConnection();
    }

    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(prefixConsole+msg);
    }

    public static void log(CommandSender sender, String msg){
        sender.sendMessage(prefixCommands+msg);
    }

    public static void logError(String msg){
        logError(Bukkit.getConsoleSender(), msg);
    }

    public static void logError(CommandSender sender, String msg){
        sender.sendMessage(prefixErrors+msg);
    }

    public static SwordLevel getInstance(){
        return instance;
    }

    public static Connector getConnector(){
        if(connector == null){
            connector = ConnectorBase.makeConnector(
                    config.getConfigurationSection("Sql"));
        }
        return connector;
    }

    public static Leveler getLeveler(){
        return leveler;
    }
}
