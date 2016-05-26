package br.neitan96.swordlevelv3.connector;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Project: SwordLevel
 * Author: Neitan96
 * Since: 30/Out/2015 22:37
 * Created by Neitan96 on 30/10/15.
 */
public interface Connector{

    void openConnection();

    void closeConnection();

    Connection getConnection();

    PreparedStatement getStatement(String query);

}
