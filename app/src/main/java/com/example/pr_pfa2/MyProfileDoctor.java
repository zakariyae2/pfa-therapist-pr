package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pr_pfa2.Model.DoctorModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyProfileDoctor extends AppCompatActivity {

    ImageView backIV;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    TextView fullnameTV, addressTV, cityTV, phonenumberTV, emailTV, aboutTV;
    RatingBar ratingbar;
    DoctorModel doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_doctor);

        // goback to mainactivity

        backIV = findViewById(R.id.backMyDPIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });



        //get and display users data


        fullnameTV = findViewById(R.id.fullnameMyDPTV);
        addressTV = findViewById(R.id.addressMyDPTV);
        phonenumberTV = findViewById(R.id.phonenumberMyDPTV);
        emailTV = findViewById(R.id.emailMyDPTV);
        aboutTV = findViewById(R.id.qualificationsMyDPTV);
        cityTV = findViewById(R.id.cityMyDPTV);
        ratingbar = findViewById(R.id.ratingbarMyDPTV);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


            DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    doctor = documentSnapshot.toObject(DoctorModel.class);

                    fullnameTV.setText(doctor.getFullName());
                    addressTV.setText(doctor.getAddress());
                    phonenumberTV.setText(doctor.getPhoneNumber());
                    emailTV.setText(doctor.getEmail());
                    aboutTV.setText(doctor.getQualifications());
                    cityTV.setText(doctor.getCity());
                    ratingbar.setRating(doctor.getRating());

                }
            });




    }
}