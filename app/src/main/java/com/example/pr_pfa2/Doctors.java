package com.example.pr_pfa2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class Doctors extends AppCompatActivity {

    ImageView back_image;
    FirebaseFirestore mStore;
    RecyclerView recylcerview;
    DoctorAdapter myAdapter;
    ArrayList<DoctorModel> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        //back to client home page

        back_image = findViewById(R.id.backIMG);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Client.class));
            }
        });

        recylcerview = findViewById(R.id.recyclerview);
        recylcerview.setHasFixedSize(true);
        recylcerview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new DoctorAdapter(this, list);
        recylcerview.setAdapter(myAdapter);









        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");

        usersRef.whereEqualTo("role", "doctor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> doctors = new ArrayList<>();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            DoctorModel doctor = document.toObject(DoctorModel.class);
                            list.add(doctor);
                        }
                        // display the list of doctors in the RecyclerView

                        myAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // handle errors here
                    }
                });

        //documentSnapshot.getString('test');

/*
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            String name = documentSnapshot.getString("name");
                            Log.d("TAG", "Name: " + name);
                        }
                    }
                });


 */










    }
}