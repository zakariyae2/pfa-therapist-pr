package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
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
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

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
    String Id;
    FirebaseFirestore db;
    String hour,message,message2,userEmail2,docEmail,phone;


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
        Id = currentUser.getUid();


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
                            phone = documentSnapshot.getString("phone");
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
                hour = "sheduled at 9am";
                Appointment();
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = "sheduled at 10am";
                Appointment();
            }
        });

        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = "sheduled at 11am";
                Appointment();
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = "sheduled at 2pm";
                Appointment();
            }
        });
        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = "sheduled at 3pm";
                Appointment();
            }
        });
        R6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = "sheduled at 4pm";
                Appointment();
            }
        });
    }

    public void Appointment() {
        db = FirebaseFirestore.getInstance();
        Map<String, Object> schedule = new HashMap<>();
        schedule.put("date", message);
        schedule.put("month",message2);
        schedule.put("emailpat",userEmail2);
        schedule.put("emaildoc",docEmail);
        schedule.put("fullName",fullname);
        schedule.put("hour",hour);
        schedule.put("phone",phone);
        schedule.put("userID",Id);
        schedule.put("state","sheduled");


        db.collection("Appointment")
                .whereEqualTo("emailpat",userEmail2)
                .whereEqualTo("state", "sheduled")
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            db.collection("Appointment").document(fullname)
                                    .set(schedule)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Appointment scheduled successfully
                                            Toast.makeText(Appointement.this, "Appointment scheduled successfully", Toast.LENGTH_SHORT).show();
                                            //startActivity(new Intent(getApplicationContext(), Shedule1.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error occurred while scheduling the appointment
                                            Toast.makeText(Appointement.this, "error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Appointment already exists for the selected date and month
                            Toast.makeText(Appointement.this, "you already have appointment", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while querying the database
                        Toast.makeText(Appointement.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });



    }




}
