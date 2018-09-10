package com.example.robin.quiethours;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

import static android.content.Context.NOTIFICATION_SERVICE;

public class startAlarm extends Worker {

    private SharedPreferences appSharedPrefs;
    private Boolean vib;
    private AudioManager audioManager;
    NotificationManager notificationManager;
    public static final String b = "420";
    @NonNull
    @Override
    public Result doWork() {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(b,"Default Channel",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
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
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),333,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(),b)
                    .setSmallIcon(R.drawable.ic_volume_off)
                    .setContentTitle("Profile Active")
                    .setContentText("Now you can relax during your work hours")
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build();
            notificationManager.notify(1112,notification);
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
