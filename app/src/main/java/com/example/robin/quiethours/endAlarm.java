package com.example.robin.quiethours;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class endAlarm extends Worker {
    AudioManager audioManager;
    @NonNull
    @Override
    public Result doWork() {
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if ((audioManager != null)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Log.e("TAG","Normal");
        }
        try {
            //Make a network call
            return Result.SUCCESS;
        } catch (Exception e) {

            //either return Result.RETRY or Result.FAILURE

            return Result.FAILURE;
        }
    }
}
