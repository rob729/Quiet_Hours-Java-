package com.example.robin.quiethours;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 2)
public abstract class ProfileDatabase  extends RoomDatabase {
    public abstract ProfileDao getProfileDao();
}
