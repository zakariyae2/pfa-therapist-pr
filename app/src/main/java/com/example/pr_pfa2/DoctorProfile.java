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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfile extends AppCompatActivity {

    TextView fullnameTV, addressTV, cityTV, phonenumberTV, emailTV, aboutTV;
    ImageView backIV;
    RatingBar ratingbar;
    ImageView messageIV;
FirebaseFirestore fStore;
DoctorModel doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Retrieve data from intent
        DoctorModel data = getIntent().getParcelableExtra("data");
        Intent i = getIntent();

        /*
        String fullname = i.getStringExtra("fullname");
        String address = i.getStringExtra("address");
        String phonenumber = i.getStringExtra("phonenumber");
        String email = i.getStringExtra("email");
        String qualifications = i.getStringExtra("qualifications");
        String city = i.getStringExtra("city");
        float rating = i.getFloatExtra("rating", 0.0f);

 */
        String targetID = i.getStringExtra("targetID");

        //show user data
/*
        fStore = FirebaseFirestore.getInstance();

        DocumentReference docRef = fStore.collection("Users").document(targetID);
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

 */


        String fullname = i.getStringExtra("fullname");
        String address = i.getStringExtra("address");
        String phonenumber = i.getStringExtra("phonenumber");
        String email = i.getStringExtra("email");
        String qualifications = i.getStringExtra("qualifications");
        String city = i.getStringExtra("city");
        float rating = i.getFloatExtra("rating", 0.0f);



        fullnameTV = findViewById(R.id.fullnameDPTV);
        addressTV = findViewById(R.id.addressDPTV);
        phonenumberTV = findViewById(R.id.phonenumberDPTV);
        emailTV = findViewById(R.id.emailDPTV);
        aboutTV = findViewById(R.id.qualificationsDPTV);
        cityTV = findViewById(R.id.cityDPTV);
        ratingbar = findViewById(R.id.ratingbarDPTV);

        fullnameTV.setText(fullname);
        addressTV.setText(address);
        phonenumberTV.setText(phonenumber);
        emailTV.setText(email);
        aboutTV.setText(qualifications);
        cityTV.setText(targetID);
        ratingbar.setRating(rating);


        // show doctors info
/*
        DocumentReference docRef = fStore.collection("Users").document(targetID);
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

 */




        //goback to Doctors Activity

        backIV = findViewById(R.id.backDPIV);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Doctors.class));
            }
        });



       //goto message activity

        messageIV = findViewById(R.id.messageIV);
        messageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Messages.class);
                i.putExtra("targetID", targetID);
                startActivity(i);
            }
        });








    }
}