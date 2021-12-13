package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;
import android.util.Base64;

import com.example.travelbuddy.Models.MediaPlayerFactory;
import com.example.travelbuddy.Models.Sight;
import com.example.travelbuddy.Models.Sights;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetDataFromDb implements dblookups{
    Connection connection;
    private static GetDataFromDb singleinstance = null;


    public GetDataFromDb(){
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.connectionclass();
    }

    public static GetDataFromDb getSingleinstance(){
        if(singleinstance == null){
            singleinstance = new GetDataFromDb();
        }
        return singleinstance;
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
    public ArrayList<Sight> getcoord(String Qrcode) {
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
            con.close();

            if(rs.next()){
                base64 = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        return mediaPlayerFactory.createPreparedmedia(base64);
    }

    @Override
    public ArrayList<Sights> getSights(int Qrcode) {
        ArrayList<Sights> sightslist = new ArrayList<>();
        Connection con;
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.connectionclass();
        String preparedstatement = "select travelbuddy.tours.tour_image, travelbuddy.tour_variants.tour_name, travelbuddy.tour_variants.tour_description\n" +
                "from travelbuddy.purchases\n" +
                "inner join travelbuddy.tours on travelbuddy.tours.tour_id = travelbuddy.purchases.tour_id\n" +
                "inner join travelbuddy.tour_variants on travelbuddy.tours.tour_id = travelbuddy.tour_variants.tour_id\n" +
                "where travelbuddy.purchases.ticket_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(preparedstatement);
            pstmt.setInt(1,Qrcode);
            ResultSet rs = pstmt.executeQuery();
            con.close();
            while(rs.next()){
                final byte[] decodedBytes = Base64.decode(rs.getString("tour_image"), Base64.DEFAULT);
                Sights sights = new Sights(rs.getString("tour_name"),decodedBytes,rs.getString("tour_description"));
                sightslist.add(sights);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return sightslist;
    }
}
