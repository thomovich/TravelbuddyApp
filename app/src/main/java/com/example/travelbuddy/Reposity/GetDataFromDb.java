package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;

import com.example.travelbuddy.Models.MediaPlayerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetDataFromDb implements dblookups{
    Connection connection;


    public GetDataFromDb(){
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.connectionclass();
        //connection = DatabaseConnector.getConnection();
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
    public MediaPlayer getsound(String id) {
        String base64 = null;
        Connection con;
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.connectionclass();

        try {
            PreparedStatement pstmt = con.
                    prepareStatement("Select sight_audio from travelbuddy.sight_variants");
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                base64 = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        return mediaPlayerFactory.createPreparedmedia(base64);
    }
}
