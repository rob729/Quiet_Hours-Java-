package com.example.robin.quiethours;

import android.app.Application;
import android.arch.persistence.room.Room;

public class ProfileApplication extends Application {

    static ProfileDatabase profileDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        profileDatabase = Room.databaseBuilder(getApplicationContext(),
                ProfileDatabase.class,
                "profile.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    public  static  ProfileDatabase getDb(){
        return profileDatabase;
    }
}
