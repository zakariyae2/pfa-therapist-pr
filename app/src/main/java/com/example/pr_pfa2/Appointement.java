package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Appointement extends AppCompatActivity {
    RelativeLayout R1;
    RelativeLayout R2;
    RelativeLayout R3;
    RelativeLayout R4;
    RelativeLayout R5;
    RelativeLayout R6;
    TextView date;
    TextView helloMSG;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String fullname;
    FirebaseFirestore db;
    String hour,message,message2,userEmail2,docEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement);
        R1 = (RelativeLayout) findViewById(R.id.btn_9pm);
        R2 = (RelativeLayout) findViewById(R.id.btn_10pm);
        R3 = (RelativeLayout) findViewById(R.id.btn_11pm);
        R4 = (RelativeLayout) findViewById(R.id.btn_2am);
        R5 = (RelativeLayout) findViewById(R.id.btn_3am);
        R6 = (RelativeLayout) findViewById(R.id.btn_4am);
        date = (TextView) findViewById(R.id.dat);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail2 = currentUser.getEmail();


        DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fullname = documentSnapshot.getString("fullName");
                helloMSG = findViewById(R.id.chapp);
                helloMSG.setText("Hello " + fullname.toUpperCase() + "\nplease chose your Appointment");

            }
        });
        fStore.collection("Mypatients").whereEqualTo("emailpat", userEmail2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            docEmail = documentSnapshot.getString("emaildoc");
                            // Do something with the emaildoc value
                        }
                    }
                });


                shedule();

        Intent shed=getIntent();
        message=shed.getStringExtra("date");
        message2=shed.getStringExtra("dateMonth");

    }


    public void shedule() {
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re1 = new Intent(Appointement.this, Shedule1.class);
                hour = "sheduled at 9am";
                Appointment();
                startActivity(Re1);
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re2 = new Intent(Appointement.this, Shedule1.class);
                hour = "sheduled at 10am";
                Appointment();
                startActivity(Re2);
            }
        });

        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re3=new Intent(Appointement.this,Shedule1.class);
                hour = "sheduled at 11am";
                Appointment();
                startActivity(Re3);
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re4=new Intent(Appointement.this,Shedule1.class);
                hour = "sheduled at 2pm";
                Appointment();
                startActivity(Re4);
            }
        });
        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re5=new Intent(Appointement.this,Shedule1.class);
                hour = "sheduled at 3pm";
                Appointment();
                startActivity(Re5);
            }
        });
        R6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Re6=new Intent(Appointement.this,Shedule1.class);
                hour = "sheduled at 4pm";
                Appointment();
                startActivity(Re6);
            }
        });
    }

    public void Appointment() {
        db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("date", message);
        data.put("month",message2);
        data.put("schedule",hour);
        data.put("emailpat",userEmail2);
        data.put("emaildoc",docEmail);
        data.put("hour",hour);
        data.put("state","sheduled");


        db.collection("Appointment").document(fullname)
                .set(data);

    }
}
