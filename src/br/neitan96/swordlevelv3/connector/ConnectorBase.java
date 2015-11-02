package br.neitan96.swordlevelv3.connector;

import br.neitan96.swordlevelv3.util.ConfigLoader;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 22:39
 * Created by Neitan96 on 30/10/15.
 */
public abstract class ConnectorBase implements Connector, ConfigLoader{

    public static final String sqlCreateRanks =
            "CREATE TABLE IF NOT EXISTS `{TableRanks}` (" +
            "`{ColumnID}` BIGINT NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, " +
            "`{ColumnPlayer}` VARCHAR(255), " +
            "`{ColumnGroup}` VARCHAR(128), " +
            "`type` VARCHAR(16), " +
            "`score` BIGINT DEFAULT 0" +
            ")";

    public static final String sqlCreateLevel =
            "CREATE TABLE IF NOT EXISTS `{TableLevel}` (" +
            "`{ColumnID}` BIGINT NOT NULL PRIMARY KEY UNIQUE AUTO_INCREMENT, " +
            "`{ColumnPlayer}` VARCHAR(255), " +
            "`{ColumnGroup}` VARCHAR(128), " +
            "`xp` BIGINT DEFAULT 0, " +
            "`level` BIGINT DEFAULT 0" +
            ")";

    public static ConnectorBase makeConnector(ConfigurationSection section){
        ConnectorBase connector = null;

        if(section != null){
            String mode = section.getString("StoreMode", "SqlLite");
            switch (mode.toLowerCase()){
                case "mysql":
                    connector = new ConnectorMySql(section);
                    break;
                case "postgresql":
                    connector = new ConnectorPostgreSQL(section);
                    break;
            }
        }

        if(connector == null)
            connector = new ConnectorSqlite(section);

        return connector;
    }

    protected Connection connection;

    protected String url = null;
    protected String user = null;
    protected String password = null;

    protected Map<String, String> binds = new HashMap<>();

    public ConnectorBase(ConfigurationSection section){
        loadFromConfig(section);
        openConnection();
    }

    public Map<String, String> getBinds(){
        return binds;
    }

    public void addBind(String key, String value){
        binds.put(key, value);
    }

    public void setBinds(Map<String, String> binds){
        this.binds = binds;
    }

    protected void createTables(){
        try{
            getStatement(sqlCreateLevel).executeUpdate();
            getStatement(sqlCreateRanks).executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection(){
        try{
            if(user != null && password != null){
                connection = DriverManager.getConnection(url, user, password);
            }else {
                connection = DriverManager.getConnection(url);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection(){
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            connection = null;
        }
    }

    @Override
    public Connection getConnection(){
        if(connection == null)
            openConnection();
        return connection;
    }

    @Override
    public PreparedStatement getStatement(String sql){

        if(connection == null)
            return null;

        for (Map.Entry<String, String> entry : binds.entrySet())
            sql = sql.replace("{"+entry.getKey()+"}", entry.getValue());

        try{
            return connection.prepareStatement(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void loadFromConfig(ConfigurationSection section){
        addBind("TableRanks", section.getString("TableRanks"));
        addBind("TableLevel", section.getString("TableLevel"));

        addBind("ColumnID", "id");
        addBind("ColumnPlayer", "player");
        addBind("ColumnGroup", "group");
    }

}