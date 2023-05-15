package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Client extends AppCompatActivity {

    Button logoutButton;
    TextView helloMSG;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String fullname;
    BottomNavigationView bottomNav;


    RelativeLayout doctors_card, profile_card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();

        //get the user's fullname and display it
        DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fullname = documentSnapshot.getString("fullName");
                helloMSG = findViewById(R.id.tvHelloMSG1);
                helloMSG.setText("Hello " + fullname.toUpperCase());

            }
        });



        //signout
        logoutButton = findViewById(R.id.signoutC);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fAuth.signOut();

                Intent i = new Intent(v.getContext(), Login.class);
                startActivity(i);
                finish();

            }
        });



        //goto Doctors activity doctors list where the client can choose a therapist

        doctors_card = findViewById(R.id.cardDoctors);

        doctors_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Doctors.class));
            }
        });



        //goto messages activity

        bottomNav = findViewById(R.id.bottomNavViewClient);
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
                        startActivity(new Intent(getApplicationContext(), MyProfileClient.class));
                        return true;

                    // Add more cases for each item in your Bottom Navigation Bar
                }
                return false;
            }
        });


        //goto myprofile acitivity

        profile_card = findViewById(R.id.cardProfile);
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MyProfileClient.class));
            }
        });


    }
}