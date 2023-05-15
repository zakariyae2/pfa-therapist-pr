package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pr_pfa2.Model.DoctorModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfileClient extends AppCompatActivity {

    ImageView backIV;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    TextView fullnameTV, addressTV, cityTV, phonenumberTV, emailTV;
    DoctorModel client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_client);


        // goback to mainactivity

        backIV = findViewById(R.id.backMyCPIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Client.class));
            }
        });



        //get and display users data


        fullnameTV = findViewById(R.id.fullnameMyCPTV);
        addressTV = findViewById(R.id.addressMyCPTV);
        phonenumberTV = findViewById(R.id.phonenumberMyCPTV);
        emailTV = findViewById(R.id.emailMyCPTV);
        cityTV = findViewById(R.id.cityMyCPTV);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                client = documentSnapshot.toObject(DoctorModel.class);

                fullnameTV.setText(client.getFullName());
                addressTV.setText(client.getAddress());
                phonenumberTV.setText(client.getPhoneNumber());
                emailTV.setText(client.getEmail());
                cityTV.setText(client.getCity());

            }
        });


        // nav bar logic
/*
        View bottomNav = findViewById(R.id.bottomNavView);
        bottomNav.setSelectedItemId(R.id.profile);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   case R.id.home:
                        // Handle item 1 click
                        startActivity(new Intent(getApplicationContext(), Client.class));
                        return true;

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


 */



    }
}