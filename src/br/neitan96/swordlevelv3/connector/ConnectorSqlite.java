package br.neitan96.swordlevelv3.connector;

import br.neitan96.swordlevelv3.SwordLevel;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 23:58
 * Created by Neitan96 on 30/10/15.
 */
public class ConnectorSqlite extends ConnectorBase{

    public ConnectorSqlite(ConfigurationSection section){
        super(section);
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        super.loadFromConfig(section);
        String filename = section.getString("Sqlite.Filename", "SwordLevel.db");
        File file = new File(SwordLevel.getInstance().getDataFolder(), filename);
        url = "jdbc:sqlite:"+file.getAbsolutePath();
    }
}
