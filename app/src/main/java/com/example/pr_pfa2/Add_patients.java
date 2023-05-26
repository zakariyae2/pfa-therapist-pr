package com.example.pr_pfa2;

import android.content.Intent;
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
import com.google.android.play.core.integrity.v;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_patients extends AppCompatActivity {
    EditText fullName,phone,email,address;
    String city2, password;
    Button btnn2;
    FirebaseFirestore db;
    Boolean empty = true;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        setContentView(R.layout.activity_add_patients);
        password = "123456";
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
        checkEmptyField(fullName);
        checkEmptyField(email);
        checkEmptyField(phone);
        checkEmptyField(address);

        if(!empty){
            //start registration

            fAuth.createUserWithEmailAndPassword(email.getText().toString(), password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Add_patients.this, "Patient added", Toast.LENGTH_SHORT ).show();

                            DocumentReference df = fStore.collection("Users").document(user.getUid());

                            Map<String, Object> userInfo = new HashMap<>();

                            userInfo.put("userID",user.getUid());
                            userInfo.put("fullName", fullName.getText().toString());
                            userInfo.put("email", email.getText().toString());
                            userInfo.put("phoneNumber", phone.getText().toString());
                            userInfo.put("address", address.getText().toString());
                            userInfo.put("city", city2);
                            userInfo.put("role", "client");

                            df.set(userInfo);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Add_patients.this, "Registration failed", Toast.LENGTH_SHORT ).show();

                        }
                    });

        }else{
            Toast.makeText(Add_patients.this, "All fields must be filled", Toast.LENGTH_SHORT ).show();
        }

    }
    public boolean checkEmptyField(EditText textField){
        if(textField.getText().toString().isEmpty())
            empty = true;
        else empty= false;

        return empty;
    }
}

