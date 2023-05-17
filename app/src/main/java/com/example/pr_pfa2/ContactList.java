package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.pr_pfa2.Adapter.ContactAdapter;
import com.example.pr_pfa2.Adapter.DoctorAdapter;
import com.example.pr_pfa2.Adapter.MessageAdapter;

import com.example.pr_pfa2.Model.ContactModel;
import com.example.pr_pfa2.Model.DoctorModel;
import com.example.pr_pfa2.Model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactList extends AppCompatActivity {

    FirebaseAuth fAuth;
    DocumentReference docRef;

    RecyclerView recyclerview;
    ContactAdapter myAdapter;
    ArrayList<ContactModel> list;
    String currentUserRole;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        //check current user type

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        CollectionReference usersCollectionRef = FirebaseFirestore.getInstance().collection("Users");
        usersCollectionRef.document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String role = document.getString("role");

                        if (role != null && role.equals("doctor")) {
                            // The current user is a doctor
                            currentUserRole = "doctor";

                        } else {
                            // The current user is not a doctor
                            // Add your logic here for non-doctor users
                            currentUserRole = "client";

                        }
                    }
                } else {
                    // Handle any errors that occur while retrieving data
                }
            }
        });




        BottomNavigationView bottomNav = findViewById(R.id.bottomNavViewClientContactList);
        bottomNav.setSelectedItemId(R.id.messages);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle item 1 click
                        // check which activity based on user role
                        if(currentUserRole.equals("doctor")){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), Client.class));
                        }

                        //startActivity(new Intent(getApplicationContext(), Client.class));
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;

                    /*
                    case R.id.messages:
                        // Handle item 2 click
                        startActivity(new Intent(getApplicationContext(), Messages.class));
                        return true;
                        */
                    case R.id.profile:
                        // Handle item 3 click

                        if(currentUserRole.equals("doctor")){
                            startActivity(new Intent(getApplicationContext(), MyProfileDoctor.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), MyProfileClient.class));
                        }

                        return true;


                    // Add more cases for each item in your Bottom Navigation Bar
                }
                return false;
            }
        });


        //setup recycler view get and display contacts

        recyclerview = findViewById(R.id.contactlistRV);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        myAdapter = new ContactAdapter(this, list);
        recyclerview.setAdapter(myAdapter);
        /*
        ContactModel test = new ContactModel("test","test");
        list.add(test);

         */

        String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
/*
        Query query3 = db.collection("Chats")
                .whereIn("user1ID", Arrays.asList(currentUserID))
                .whereIn("user2ID", Arrays.asList(currentUserID));

 */
        Query query4 = db.collection("Chats")
                .whereIn("user1ID", Arrays.asList(currentUserID))
                .whereIn("user2ID", Arrays.asList(currentUserID));

        Query query = db.collection("Chats")
                        .whereEqualTo("user1ID", currentUserID);

        Query query2 = db.collection("Chats")
                .whereEqualTo("user2ID", currentUserID);

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Handle the successful query result
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            ContactModel contact = documentSnapshot.toObject(ContactModel.class);
                            list.add(contact);

                    }

                    myAdapter.notifyDataSetChanged();

                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    // ...
                });

        query2.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Handle the successful query result
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Process each document here
                        // ...
                        ContactModel contact = documentSnapshot.toObject(ContactModel.class);

                        list.add(contact);

                    }

                    myAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    // ...
                });






    }


}