package com.lvadt.phylane.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Triston on 4/29/2014.
 */
public class Sound {

    static MediaPlayer mp;
    public static void playSound(Context c, int file){

    }

    public static void playMusic(Context c, int file){
        mp.create(c, file);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });
        mp.start();
    }
}
