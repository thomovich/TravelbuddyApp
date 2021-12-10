package com.example.travelbuddy.Reposity;

import android.content.Context;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travelbuddy.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SoundRepository
{
    private static SoundRepository singletonisntance = null;

    private final MutableLiveData <MediaPlayer> media = new MutableLiveData<MediaPlayer>();

    public static SoundRepository getSoundRepositoryInstance(){
        if(singletonisntance == null){
            singletonisntance = new SoundRepository();
        }
        return singletonisntance;
    }

    public void selectMedia(MediaPlayer mediaPlayer){
        media.postValue(mediaPlayer);
    }

    public LiveData<MediaPlayer> getMedia(){
        return media;
    }


    public MediaPlayer getMediaplayer(String media, Context context){
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
        MediaPlayer mediaPlayer = new MediaPlayer();
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        mediaPlayer.setDataSource(new MediaDataSource() {
            @Override
            public long getSize() throws IOException {
                return data.length;
            }

            @Override
            public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
                int length = (int)getSize();
                if (position >= length) return -1; // EOF
                if (position + size > length) // requested more than available
                    size = length - (int)position; // set size to maximum size possible
                // at given position

                System.arraycopy(data, (int) position, buffer, offset, size);
                return size;
            }



            @Override
            public void close() throws IOException {

            }
        });

        /*StringBuffer sbuffer = new StringBuffer();
        InputStream ins = context.getResources().openRawResource(R.raw.base64);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));{
            if(ins != null){
                try {
                    while((base64 = reader.readLine()) != null){
                        sbuffer.append(base64);
                    }
                    base64 = sbuffer.toString();
                    ins.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };*/


        return mediaPlayer;
    }

}
