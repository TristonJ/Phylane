package com.lvadt.phylane.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;

import com.lvadt.phylane.R;

/**
 * Created by Triston on 4/29/2014.
 */
public class Sound {

    int sFiles[] = new int[10];
    int sounds[] = new int[10]; // Max of 10 loaded sounds
    SoundPool sp = null;
    int resource = 0;

    private class BackgroundSoundService extends Service {
        private final String TAG = null;
        MediaPlayer player;

        public IBinder onBind(Intent arg0) {

            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            player = MediaPlayer.create(this, R.raw.maintheme);
            player.setLooping(true); // Set looping
            player.setVolume(50,50);

        }

        /**
         * Starts the playing of a sound
         * @param intent the intent
         * @param flags any flags
         * @param startId start id
         * @return always 1
         */
        public int onStartCommand(Intent intent, int flags, int startId) {
            player.start();
            return 1;
        }
        }

    /**
     * Loads an array of sounds
     * @param c the context
     * @param files an array of sound files
     */
    public void loadSounds(Context c, int files[]){
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        if(files.length <= 10) {
            sFiles = files;
            for (int i = 0; i < files.length; i++) {
                sounds[i] = sp.load(c, files[i], 1);
            }
        }
    }

    /**
     * Plays a sound from an already loaded file
     * @param file the id of the file to be played
     */
    public void playSound(int file){
        for(int i = 0; i < sFiles.length; i++) {
            if (sFiles[i] == file)
                sp.play(sounds[i], 1, 1, 0, 0, 1);
        }
    }

    /**
     * Plays the sound of a file that is not loaded
     * @param c the context
     * @param file the sound file
     */
    public void playSound(Context c, int file){
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int sound = sp.load(c, file, 1);

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sp.play(sound, 1, 1, 0, 0, 1);
            }
        });
    }

    /**
     * Plays music
     * @param c the context
     * @param file the music file to be played
     */
    public void playMusic(Context c, int file){
        Intent i = new Intent(c, BackgroundSoundService.class);
        resource = file;
        c.startService(i);
    }

    /**
     * Stops the playing of music
     */
    public void stopMusic(){

    }
}
