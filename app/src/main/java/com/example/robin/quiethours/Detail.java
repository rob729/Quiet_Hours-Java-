package com.example.robin.quiethours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class Detail extends AppCompatActivity {

    Profile p1;
    MaterialDayPicker materialDayPicker;
    TextView name,str,end;
    List<Boolean> d = new ArrayList<>();

    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Details");
        name = findViewById(R.id.txt1);
        str = findViewById(R.id.str);
        end = findViewById(R.id.end);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        materialDayPicker = findViewById(R.id.day_picker);
        Intent intent = getIntent();
        p1 = (Profile) intent.getSerializableExtra("profile");
        if (p1 != null) {
            name.setText(p1.getName());
            str.setText(p1.getShr() + ":" + p1.getSmin());
            end.setText(p1.getEhr() + ":" + p1.getEmin());
        }

        gson = new Gson();
        Type type = new TypeToken<List<Boolean>>() {
        }.getType();
        d = gson.fromJson(p1.getD(),type);
        if (d != null)
            DayPicker(d);
        materialDayPicker.setFocusable(false);

    }

    private void DayPicker(List<Boolean> d){
        if(d.get(0))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.SUNDAY);
        if(d.get(1))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.MONDAY);
        if(d.get(2))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.TUESDAY);
        if(d.get(3))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.WEDNESDAY);
        if(d.get(4))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.THURSDAY);
        if(d.get(5))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.FRIDAY);
        if(d.get(6))
            materialDayPicker.selectDay(MaterialDayPicker.Weekday.SATURDAY);
    }
}
