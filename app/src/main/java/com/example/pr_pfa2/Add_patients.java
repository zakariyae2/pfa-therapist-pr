package com.example.pr_pfa2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_patients extends AppCompatActivity {
    EditText fullName,phone,email,address;
    String city2;
    Button btnn2;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_add_patients);
        fullName=(EditText) findViewById(R.id.editfn);
        phone=(EditText) findViewById(R.id.editph);
        email=(EditText) findViewById(R.id.editem);
        address=(EditText) findViewById(R.id.editadd);
        btnn2=(Button) findViewById(R.id.btn3);
        Spinner spinnerCity2 = findViewById(R.id.cities_spinnerC2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moroccan_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity2.setAdapter(adapter);
        city2 = spinnerCity2.getSelectedItem().toString();
        btnn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });
    }
    public void writeNewUser(){
        String FullName = fullName.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String Address = address.getText().toString();
        db=FirebaseFirestore.getInstance();
        Map<String,Object> user = new HashMap<>();
        user.put("fullName",FullName);
        user.put("phoneNumber",Phone);
        user.put("email",Email);
        user.put("Address",Address);
        user.put("city",city2);
        user.put("role", "client");



        db.collection("Users").document(Email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Add_patients.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_patients.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

