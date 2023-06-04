package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        //check if user is already logged in

        FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(currentUser != null) {
                    checkRole(currentUser.getUid());
                }else{
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        }, 3000);




    }

    private void checkRole(String uid) {

        DocumentReference df = mStore.collection("Users").document(uid);
        //extract data
        //get data users colleciton of the document with uid the data of that user is stored in

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.getString("role").equals("client")){
                    Intent i = new Intent(getApplicationContext(), Client.class);
                    startActivity(i);
                    finish();
                }
                if(documentSnapshot.getString("role").equals("doctor")){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

                //admin
                /*
                if(documentSnapshot.getString("role").equals("admin")){
                    Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
                    startActivity(i);
                    finish();
                }
                */


            }
        });
    }
}