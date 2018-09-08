package com.example.robin.quiethours;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import ca.antonious.materialdaypicker.MaterialDayPicker;

public class add_profile extends AppCompatActivity {

    EditText edtProf, edtStartTime, edtEndTime;
    int shr , smin, emin, ehr;
    TimePickerDialog sTimePicker, eTimePicker;
    List<MaterialDayPicker.Weekday> daysSelected;
    MaterialDayPicker materialDayPicker;
    ArrayList<Profile> al = new ArrayList<>();
    List<Boolean> d = new ArrayList<>();
    String days;
    OneTimeWorkRequest startAlarmRequest,endAlarmRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        edtProf = findViewById(R.id.userToDoEditText);
        edtEndTime = findViewById(R.id.EndTime);
        edtStartTime = findViewById(R.id.StartTime);
        materialDayPicker = findViewById(R.id.day_picker);

        final Drawable cross = getResources().getDrawable(R.drawable.ic_cancel);
        if (cross != null) {
            cross.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(cross);
        }

        FloatingActionButton mfab = findViewById(R.id.makeProfileFab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtProf.getText().toString().equals(""))
                    finish();
                daysSelected = materialDayPicker.getSelectedDays();
                Days(daysSelected);
                Gson gson = new Gson();
                days = gson.toJson(d);
                Log.e("TAG",daysSelected.contains(MaterialDayPicker.Weekday.MONDAY) + "");
                al.add(new Profile(edtProf.getText().toString(),shr,smin,ehr,emin,days));
                ProfileApplication.getDb().getProfileDao().insertProfile(new Profile(edtProf.getText().toString(),shr,smin,ehr,emin,days));
                int i=0;
                Log.e("TAG",d.size()+"");
                while (i<d.size()){
                    if(d.get(i)){
                        SAlarm(i+1);
                        eAlarm(i+1);
                    }
                    ++i;
                }
                finish();
            }
        });

        edtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                sTimePicker = new TimePickerDialog(add_profile.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        edtStartTime.setText(i + ":" + i1);
                        shr=i;
                        smin=i1;

                    }
                }, hour, minute, false);
                sTimePicker.show();
            }
        });

        edtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                eTimePicker = new TimePickerDialog(add_profile.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        edtEndTime.setText(i + ":" + i1);
                        ehr=i;
                        emin=i1;

                    }
                }, hour, minute, false);
                eTimePicker.show();
            }
        });
    }

    private void Days(List<MaterialDayPicker.Weekday> daysSelected){
        if(daysSelected.contains(MaterialDayPicker.Weekday.SUNDAY))
            d.add(0,true);
        else
            d.add(0,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.MONDAY))
            d.add(1,true);
        else
            d.add(1,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.TUESDAY))
            d.add(2,true);
        else
            d.add(2,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.WEDNESDAY))
            d.add(3,true);
        else
            d.add(3,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.THURSDAY))
            d.add(4,true);
        else
            d.add(4,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.FRIDAY))
            d.add(5,true);
        else
            d.add(5,false);
        if(daysSelected.contains(MaterialDayPicker.Weekday.SATURDAY))
            d.add(6,true);
        else
            d.add(6,false);

    }

    private void SAlarm(int dayOfWeek){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        c.set(Calendar.HOUR_OF_DAY, shr);
        c.set(Calendar.MINUTE, smin);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);


        if(c.getTimeInMillis() < System.currentTimeMillis()) {
            c.add(Calendar.DAY_OF_YEAR, 7);
        }
        startAlarmRequest = new OneTimeWorkRequest
                .Builder(startAlarm.class)
                .setInitialDelay(c.getTimeInMillis()-System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance().enqueue(startAlarmRequest);
    }

    private  void eAlarm(int dayOfWeek){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        c.set(Calendar.HOUR_OF_DAY, ehr);
        c.set(Calendar.MINUTE, emin);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        if(c.getTimeInMillis() < System.currentTimeMillis()) {
            c.add(Calendar.DAY_OF_YEAR, 7);
        }
        endAlarmRequest = new OneTimeWorkRequest
                .Builder(endAlarm.class)
                .setInitialDelay(c.getTimeInMillis()-System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance().enqueue(endAlarmRequest);
    }

}
