package com.project0.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectManager{
    private static final String URL = "jdbc:sqlite:../../database/expense_manager.db"; // check later

   public static Connection getConnection() throws SQLException{
    return DriverManager.getConnection(URL);
   }
}