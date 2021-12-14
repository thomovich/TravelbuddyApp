package com.example.travelbuddy.Reposity;

import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;

import com.example.travelbuddy.Models.LanguageVariant;
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
        boolean qrok = false;
        int qrcodeint;
        try{
            qrcodeint = Integer.parseInt(Qrcode);
        } catch (Exception e){
            return  false;
        }

        Connection con;
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.connectionclass();
        // Assume a database connection, conn.
        String preparedstm =
                "select*from travelbuddy.purchases where ticket_id = ?";

        try {
            PreparedStatement stmnt = con.prepareStatement(preparedstm);
            stmnt.setInt(1, qrcodeint);
            ResultSet rs = stmnt.executeQuery();
            con.close();
            String result = null;
            if(rs.next()){
                result = rs.getString("payment_method");
            }

            if(!result.isEmpty()){
                qrok = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return qrok;
    }

    @Override
    public ArrayList<Sight> getcoord(int Qrcode) {

        ArrayList<Sight> sightslist = new ArrayList<>();
        Connection con;
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.connectionclass();
        String preparedstatement = "select travelbuddy.sights.latitude," +
                "travelbuddy.sights.longitude," +
                "travelbuddy.sights.radius_in_meters," +
                "travelbuddy.sights.sight_image," +
                "travelbuddy.sight_variants.sight_name," +
                "travelbuddy.sight_variants.sight_description " +
                "from travelbuddy.sights " +
                "inner join travelbuddy.sight_variants " +
                "on travelbuddy.sight_variants.sight_id = travelbuddy.sights.sight_id " +
                "inner join travelbuddy.purchases " +
                "on travelbuddy.sights.tour_id = travelbuddy.purchases.tour_id " +
                "where travelbuddy.purchases.ticket_id = ?";

        Log.d("d",preparedstatement);

        try {
            PreparedStatement pstmt = con.prepareStatement(preparedstatement);
            pstmt.setInt(1,Qrcode);
            ResultSet rs = pstmt.executeQuery();
            con.close();
            while(rs.next()){
                //final byte[] decodedBytes = Base64.decode(rs.getString("sight_image"), Base64.DEFAULT);
                Sight sights = new Sight(Double.parseDouble(rs.getString("latitude")),
                        Double.parseDouble(rs.getString("longitude")),
                        rs.getInt("radius_in_meters"),
                        0,//rs.getInt(4)
                        new LanguageVariant(rs.getString("sight_name"),
                                rs.getString("sight_description")));
                Log.d("empty array", "array");
                        //Sight(rs.getString("sight_name"),decodedBytes,rs.getString("sight_description"));
                sightslist.add(sights);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        return sightslist;
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
        String preparedstatement = "select travelbuddy.sight_variants.sight_name,\n" +
                "travelbuddy.sight_variants.sight_description,\n" +
                "travelbuddy.sights.sight_image\n" +
                "from travelbuddy.sights\n" +
                "inner join travelbuddy.sight_variants \n" +
                "on travelbuddy.sight_variants.sight_id = travelbuddy.sights.sight_id\n" +
                "inner join travelbuddy.purchases\n" +
                "on travelbuddy.sights.tour_id = travelbuddy.purchases.tour_id\n" +
                "where travelbuddy.purchases.ticket_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(preparedstatement);
            pstmt.setInt(1,Qrcode);
            ResultSet rs = pstmt.executeQuery();
            con.close();
            while(rs.next()){
                final byte[] decodedBytes = Base64.decode(rs.getString("sight_image"), Base64.DEFAULT);
                Sights sights = new Sights(rs.getString("sight_name"),decodedBytes,rs.getString("sight_description"));
                sightslist.add(sights);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return sightslist;
    }

    @Override
    public ArrayList<String> getLanguages(int Qrcode) {
        ArrayList<String> languagecodes = new ArrayList<>();
        Connection con;
        ConnectionManager connectionManager = new ConnectionManager();
        con = connectionManager.connectionclass();
        String preparedstatement = "select distinct travelbuddy.sight_variants.language_code\n" +
                "from travelbuddy.sights\n" +
                "inner join travelbuddy.sight_variants\n" +
                "on travelbuddy.sight_variants.sight_id = travelbuddy.sights.sight_id\n" +
                "inner join travelbuddy.purchases\n" +
                "on travelbuddy.sights.tour_id = travelbuddy.purchases.tour_id\n" +
                "where travelbuddy.purchases.ticket_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(preparedstatement);
            pstmt.setInt(1, Qrcode);
            ResultSet rs = pstmt.executeQuery();
            con.close();
            while (rs.next()){
                Log.d("getting codes", "tag");
                languagecodes.add(rs.getString("language_code"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return languagecodes;
    }
}
