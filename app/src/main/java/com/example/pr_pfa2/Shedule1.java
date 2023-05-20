package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class Shedule1 extends AppCompatActivity {
    TextView textsh;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule1);
        textsh=(TextView) findViewById(R.id.shedul_txt);
        Intent Re1=getIntent();
        String dt=Re1.getStringExtra("date1");


    }
}

