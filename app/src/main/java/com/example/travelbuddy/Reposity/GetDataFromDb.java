package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GetDataFromDb implements dblookups{
    Connection connection;

    public GetDataFromDb(){
        connection = DatabaseConnector.getConnection();
    }
    @Override
    public boolean checkqr(String Qrcode) {

        // Assume a database connection, conn.
        PreparedStatement stmnt = null;
        ResultSet rs = null;
        try
        {
            // Create the PreparedStatement
            stmnt = connection.prepareStatement("select * from Ticket");

            // Execute the query to obtain the ResultSet
            rs = stmnt.executeQuery();
        }
        catch(Exception ex)
        {
            System.err.println("Database exception: " + ex);
        }


        return true;
    }

    @Override
    public ArrayList<String> getcoord(String Qrcode) {
        return null;
    }

    @Override
    public ArrayList<MediaPlayer> getsound(String Qrcode) {
        return null;
    }
}
