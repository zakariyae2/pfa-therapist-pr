package com.example.pr_pfa2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pr_pfa2.Adapter.DoctorAdapter;
import com.example.pr_pfa2.Model.DoctorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Doctors extends AppCompatActivity {

    ImageView back_image;
    FirebaseFirestore mStore;
    RecyclerView recylcerview;
    DoctorAdapter myAdapter;
    ArrayList<DoctorModel> list;
    TextView moreinfoTV;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    String email;
    String fullname;
    String phone;
    String Id;






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
        myAdapter = new DoctorAdapter(this, list, null, null, null,null);
        recylcerview.setAdapter(myAdapter);

        fAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();

        DocumentReference df = mStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                email = documentSnapshot.getString("email");
                fullname = documentSnapshot.getString("fullName");
                phone = documentSnapshot.getString("phoneNumber");
                Id = documentSnapshot.getString("userID");

                // move adapter initialization here
                myAdapter = new DoctorAdapter(Doctors.this, list, fullname, email, Id, phone);
                recylcerview.setAdapter(myAdapter);
            }
        });



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");


        usersRef.whereEqualTo("role", "doctor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        //List<DoctorModel> doctors = new ArrayList<>();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            DoctorModel doctor = document.toObject(DoctorModel.class);
                            doctor.setTargetID(document.getId());

/*
            String fullname = document.getString("fullName");
            String email = document.getString("email");
            float rating = (float) document.getDouble("rating").doubleValue();
            String address = document.getString("address");
            String qualifications = document.getString("qualifications");

 */


                            /*String qualifications = document.getString("qualifications");
                            doctor.setQualifications(qualifications);
                             */
                            //doctor(String fullName, String email, String phoneNumber, String address, String qualifications, float rating);
                            list.add(doctor);

                            //order data based on rating
                            Collections.sort(list, (item1, item2) -> Float.compare(item2.getRating(), item1.getRating()));

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

        //search for a doctor


        EditText editTextS = findViewById(R.id.searchET);
        editTextS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s){
                filter(s.toString());
            }
        });



    }


    //Filter list
    public void filter(String text){
        ArrayList <DoctorModel> filteredDoctorList = new ArrayList<>();

        // check fullnames containing text entered in search edittext
        for (DoctorModel doctor : list){
            if(doctor.getFullName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
                filteredDoctorList.add(doctor);
            }
        }
        myAdapter.filterList(filteredDoctorList);
    }


}