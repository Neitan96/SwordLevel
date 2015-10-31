package br.neitan96.swordlevelv3.connector;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 31/Out/2015 00:18
 * Created by Neitan96 on 31/10/15.
 */
public class ConnectorPostgreSQL extends ConnectorBase{

    public ConnectorPostgreSQL(ConfigurationSection section){
        super(section);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        super.loadFromConfig(section);
        section = section.getConfigurationSection("PostgreSQL");
        url = "jdbc:postgresql://"+section.getString("Host")+
                (section.contains("Port") ? ":" + section.getString("Port") : "")+
                section.getString("Database");
        user = section.getString("User");
        password = section.getString("Password");
    }
}
