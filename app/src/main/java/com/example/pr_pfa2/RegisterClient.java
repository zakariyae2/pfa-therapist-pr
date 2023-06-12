package com.example.pr_pfa2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterClient extends AppCompatActivity {

    EditText fullName, email, password, phoneNumber, address;
    String city;
    Button registerButton;
    TextView registerET;
    Boolean empty = true;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Spinner spinnerCity = findViewById(R.id.cities_spinnerC);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moroccan_cities, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerCity.setAdapter(adapter);



        fullName = findViewById(R.id.et_fullnameC);
        email = findViewById(R.id.et_emailC);
        password = findViewById(R.id.et_passwordC);
        phoneNumber = findViewById(R.id.et_phonenumberC);
        address = findViewById(R.id.et_addressC);
        //city = spinnerCity.getSelectedItem().toString();
        registerButton = findViewById(R.id.btn_registerC);

        //Spinner spinner = findViewById(R.id.cities_spinnerC);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                // Use the selectedValue as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkEmptyField(fullName);
                checkEmptyField(email);
                checkEmptyField(password);
                checkEmptyField(phoneNumber);
                checkEmptyField(address);

                if(!empty){
                    //start registration

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    Toast.makeText(RegisterClient.this, "Registered successfully", Toast.LENGTH_SHORT ).show();

                                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                                    Map<String, Object> userInfo = new HashMap<>();

                                    userInfo.put("userID",user.getUid());
                                    userInfo.put("fullName", fullName.getText().toString());
                                    userInfo.put("email", email.getText().toString());
                                    userInfo.put("phoneNumber", phoneNumber.getText().toString());
                                    userInfo.put("address", address.getText().toString());
                                    userInfo.put("city", city);
                                    userInfo.put("role", "client");

                                    df.set(userInfo);


                                    fAuth.signOut();

                                    Intent i = new Intent(v.getContext(), Login.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(RegisterClient.this, "Registration failed", Toast.LENGTH_SHORT ).show();

                                }
                            });

                }else{
                    Toast.makeText(RegisterClient.this, "All fields must be filled", Toast.LENGTH_SHORT ).show();
                }

            }
        });


        //goto login/sign in activty
        registerET = findViewById(R.id.signinC);

        registerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Login.class));
            }
        });



    }


    public boolean checkEmptyField(EditText textField){
        if(textField.getText().toString().isEmpty())
            empty = true;
        else empty= false;

        return empty;
    }


}