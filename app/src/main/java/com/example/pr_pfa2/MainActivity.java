package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.RelativeLayout;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;


public class MainActivity extends AppCompatActivity {
    RelativeLayout R1;
    RelativeLayout R2;
    RelativeLayout R4;
    RelativeLayout R5;
    ImageButton btni1;
    ImageButton btni2;
    ImageButton btni4;
    ImageButton btni5;
    String userEmail2;
    Button logoutButton;
    TextView helloMSG;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String fullname;
    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        R1=(RelativeLayout) findViewById(R.id.bord1);
        R2=(RelativeLayout) findViewById(R.id.bord2);
        R4=(RelativeLayout) findViewById(R.id.bord4);
        R5=(RelativeLayout) findViewById(R.id.bord5);
        btni1=(ImageButton) findViewById(R.id.ac1);
        btni2=(ImageButton) findViewById(R.id.ac2);
        btni4=(ImageButton) findViewById(R.id.appo);
        btni5=(ImageButton) findViewById(R.id.calen);
        logoutButton = findViewById(R.id.btnout1);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail2 = fAuth.getCurrentUser().getEmail();

        DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fullname = documentSnapshot.getString("fullName");
                helloMSG = findViewById(R.id.txt1);
                helloMSG.setText("Hello " + fullname.toUpperCase());

            }
        });


        logoutButton = findViewById(R.id.btnout1);


        //goto my profile

        //nav bar Doctor MainAcitivity


        bottomNav = findViewById(R.id.bottomNavViewDoctor);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   /* case R.id.home:
                        // Handle item 1 click
                        startActivity(new Intent(getApplicationContext(), Client.class));
                        return true;

                    */
                    case R.id.messages:
                        // Handle item 2 click
                        startActivity(new Intent(getApplicationContext(), ContactList.class));
                        return true;
                    case R.id.profile:
                        // Handle item 3 click
                        startActivity(new Intent(getApplicationContext(), MyProfileDoctor.class));
                        return true;

                    // Add more cases for each item in your Bottom Navigation Bar
                }
                return false;
            }
        });

        pages();

    }
    public void pages() {
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act1=new Intent(MainActivity.this,My_patients.class);
                startActivity(act1);
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2=new Intent(MainActivity.this,patientrequest.class);
                startActivity(act2);
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListShedule.class));

            }
        });

        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act4=new Intent(MainActivity.this,DoctorCalendar.class);
                startActivity(act4);
            }
        });


        btni1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb1=new Intent(MainActivity.this,My_patients.class);
                startActivity(imb1);
            }
        });
        btni2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb2 = new Intent(MainActivity.this, patientrequest.class);
                startActivity(imb2);
            }
        });
        btni4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListShedule.class));
            }
        });

        btni5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb5=new Intent(MainActivity.this,DoctorCalendar.class);
                startActivity(imb5);
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(v.getContext(), Login.class);
                startActivity(i);
                finish();

            }
        });
    }
    public void rule(){
        fStore.collection("Mypatients").whereEqualTo("emaildoc",userEmail2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        startActivity(new Intent(getApplicationContext(), CalendarAppointment.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "there is no patients", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}