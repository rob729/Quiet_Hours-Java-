package com.example.robin.quiethours;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    Switch vibSwitch;
    SharedPreferences appSharedPrefs;
    SharedPreferences.Editor prefsEditor;
    TextView txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Settings");
        vibSwitch = findViewById(R.id.swv);
        txt2 =findViewById(R.id.vmdsp);
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = appSharedPrefs.edit();
        vibSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txt2.setText("Vibrate mode on");
                    prefsEditor.putBoolean("vibrate",true);
                    prefsEditor.apply();

                }
                else{
                    txt2.setText("Silent mode on");
                    prefsEditor.putBoolean("vibrate",false);
                    prefsEditor.apply();
                }
            }
        });

    }
}
