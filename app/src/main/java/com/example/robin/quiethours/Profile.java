package com.example.robin.quiethours;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "profile_table")
public class Profile implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    int shr , smin, ehr, emin;
    String d;




    public Profile(String name, int shr, int smin, int ehr, int emin, String d) {
        this.name = name;
        this.shr = shr;
        this.smin = smin;
        this.ehr = ehr;
        this.emin = emin;
        this.d = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShr() {
        return shr;
    }

    public void setShr(int shr) {
        this.shr = shr;
    }

    public int getSmin() {
        return smin;
    }

    public void setSmin(int smin) {
        this.smin = smin;
    }

    public int getEhr() {
        return ehr;
    }

    public void setEhr(int ehr) {
        this.ehr = ehr;
    }

    public int getEmin() {
        return emin;
    }

    public void setEmin(int emin) {
        this.emin = emin;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }


}
