package com.example.travelbuddy.Reposity;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    private static Connection connection = null;

    static {
        String url = "jdbc:postgresql://rogue.db.elephantsql.com:5432/lkjdrlkx";
        String user = "lkjdrlkx";
        String pass = "mW85orAnV96x8GjJXCf6MSf1ElE-ziSs";
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection successful");
        }
        catch (Exception exception) {
            System.out.println("Connection failed");
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
