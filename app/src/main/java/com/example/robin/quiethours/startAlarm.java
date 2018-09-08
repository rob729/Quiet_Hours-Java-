package com.example.robin.quiethours;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

public class startAlarm extends Worker {

    private SharedPreferences appSharedPrefs;
    private Boolean vib;
    private AudioManager audioManager;
    @NonNull
    @Override
    public Result doWork() {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if ((audioManager != null)) {
            if (appSharedPrefs.contains("vibrate")) {
                Toast.makeText(getApplicationContext(), "Alarm triggered", Toast.LENGTH_SHORT).show();
                vib = appSharedPrefs.getBoolean("vibrate", false);
                if (vib) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Log.e("TAG", "vibrate");
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Log.e("TAG", "silent");
                }
            } else {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
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
