package br.neitan96.swordlevelv3.connector;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 31/Out/2015 00:04
 * Created by Neitan96 on 31/10/15.
 */
public class ConnectorMySql extends ConnectorBase{

    public ConnectorMySql(ConfigurationSection section){
        super(section);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        super.loadFromConfig(section);
        section = section.getConfigurationSection("MySql");
        url = "jdbc:mysql://"+section.getString("Host")+
                (section.contains("Port") ? ":" + section.getString("Port") : "")+
                section.getString("Database");
        user = section.getString("User");
        password = section.getString("Password");
    }
}
