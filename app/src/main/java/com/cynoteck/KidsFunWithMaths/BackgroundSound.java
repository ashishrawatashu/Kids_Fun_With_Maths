package com.cynoteck.KidsFunWithMaths;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BackgroundSound extends Service {
    MediaPlayer allMediaPlayer;
    private final Binder binder = new MyServiceBinder();

    private final static int MAX_VOLUME = 10;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        allMediaPlayer = MediaPlayer.create(this, R.raw.sound);
        allMediaPlayer.setLooping(true); // Set looping
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 0.7) / Math.log(MAX_VOLUME)));
        allMediaPlayer.setVolume(volume, volume);
        Log.e("media1", String.valueOf(allMediaPlayer));


    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("media11", String.valueOf(allMediaPlayer));
        allMediaPlayer.start();
        return startId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allMediaPlayer.release();
    }

    public boolean isPlaying(){
        return allMediaPlayer.isPlaying();

    }

    public void onPlay(){
        allMediaPlayer.start();
    }

    public void onPause(){
        allMediaPlayer.pause();
    }



    public void dataParse(String value){
        allMediaPlayer = MediaPlayer.create(this, R.raw.sound);
        if(value.equals("play")){
            if(!allMediaPlayer.isPlaying())
            {
                allMediaPlayer.start();
            }
        }else  if(value.equals("pause")) {
            if(allMediaPlayer.isPlaying())
            {
                allMediaPlayer.pause();
            }
        }else {
            if(allMediaPlayer.isPlaying())
            {
                allMediaPlayer.stop();
            }
        }
    }

    public class MyServiceBinder extends Binder {
        public BackgroundSound getService()
        {
            return BackgroundSound.this;
        }
    }
}
