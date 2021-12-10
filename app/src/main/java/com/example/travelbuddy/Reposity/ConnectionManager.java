package com.example.travelbuddy.Reposity;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    Connection con;
    String url = "jdbc:postgresql://rogue.db.elephantsql.com:5432/lkjdrlkx";
    String user = "lkjdrlkx";
    String pass = "mW85orAnV96x8GjJXCf6MSf1ElE-ziSs";

    public Connection connectionclass(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con = null;

        try{
          Class.forName("org.postgresql.Driver");
          con = DriverManager.getConnection(url, user, pass);
        }catch (Exception e){
            e.printStackTrace();
        }

        return con;
    }
}
