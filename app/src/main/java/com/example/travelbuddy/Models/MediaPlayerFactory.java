package com.example.travelbuddy.Models;

import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.util.Base64;

import java.io.IOException;

public class MediaPlayerFactory implements MediaPlayer.OnPreparedListener{
    public MediaPlayer createPreparedmedia(String base64){
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
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
