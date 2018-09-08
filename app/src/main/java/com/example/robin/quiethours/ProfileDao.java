package com.example.robin.quiethours;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profile_table")
    List<Profile> getAllProfile();

    @Insert
    void insertProfile(Profile... profiles);

    @Update
    void updateProfile(Profile... profiles);

    @Delete
    void deleteProfile(Profile... profiles);

}
