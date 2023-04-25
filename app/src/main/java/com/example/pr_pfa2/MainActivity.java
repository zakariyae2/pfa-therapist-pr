package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout R1;
    RelativeLayout R4;
    RelativeLayout R5;
    ImageButton im1;
    ImageButton im4;
    ImageButton im5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        R1=(RelativeLayout) findViewById(R.id.bord1);
        R4=(RelativeLayout) findViewById(R.id.bord4);
        R5=(RelativeLayout) findViewById(R.id.bord5);
        im1 =(ImageButton)  findViewById(R.id.ac1);
        im4 =(ImageButton)  findViewById(R.id.appo);
        im5=(ImageButton) findViewById(R.id.calen);
        pages();
    }



    public void pages(){
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act1=new Intent(MainActivity.this,My_patients.class);
                startActivity(act1);
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act4=new Intent(MainActivity.this,Appointement.class);
                startActivity(act4);
            }
        });
        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act5=new Intent(MainActivity.this,clendar.class);
                startActivity(act5);
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb1=new Intent(MainActivity.this,My_patients.class);
                startActivity(imb1);
            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb4=new Intent(MainActivity.this,Appointement.class);
                startActivity(imb4);
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb5=new Intent(MainActivity.this,clendar.class);
                startActivity(imb5);
            }
        });
    }
}