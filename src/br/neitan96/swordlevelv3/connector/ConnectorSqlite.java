package br.neitan96.swordlevelv3.connector;

import br.neitan96.swordlevelv3.SwordLevel;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;

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
    public PreparedStatement getStatement(String sql){
        return super.getStatement(sql.replace("AUTO_INCREMENT", "AUTOINCREMENT"));
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        super.loadFromConfig(section);
        String filename = section.getString("Sqlite.Filename", "SwordLevel.db");
        File file = new File(SwordLevel.getPlugin().getDataFolder(), filename);

        if(!file.exists())
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

        url = "jdbc:sqlite:"+file.getAbsolutePath();

        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
