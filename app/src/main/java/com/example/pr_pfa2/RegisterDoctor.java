package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterDoctor extends AppCompatActivity {

    EditText fullName, email, password,qualifications, phoneNumber, address;
    String city;
    Button registerButton;
    TextView registerET;
    Boolean empty = true;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Spinner spinnerCity = findViewById(R.id.cities_spinner);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.moroccan_cities, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerCity.setAdapter(adapter);




        fullName = findViewById(R.id.et_fullnameD);
        email = findViewById(R.id.et_emailD);
        password = findViewById(R.id.et_passwordD);
        phoneNumber = findViewById(R.id.et_phonenumberD);
        address = findViewById(R.id.et_addressD);
        //city = spinnerCity.getSelectedItem().toString();
        qualifications = findViewById(R.id.et_qualificationsD);
        registerButton = findViewById(R.id.btn_registerD);

        //Spinner spinner = findViewById(R.id.cities_spinner);

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
                checkEmptyField(qualifications);

                if(!empty){
                    //start registration

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    Toast.makeText(RegisterDoctor.this, "Registered successfully", Toast.LENGTH_SHORT ).show();

                                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                                    Map<String, Object> userInfo = new HashMap<>();

                                    userInfo.put("fullName", fullName.getText().toString());
                                    userInfo.put("email", email.getText().toString());
                                    userInfo.put("phoneNumber", phoneNumber.getText().toString());
                                    userInfo.put("address", address.getText().toString());
                                    userInfo.put("city", city);
                                    userInfo.put("rating", 0);
                                    userInfo.put("qualifications", qualifications.getText().toString());
                                    userInfo.put("role", "doctor");


                                    df.set(userInfo);


                                    fAuth.signOut();

                                    Intent i = new Intent(v.getContext(), Login.class);
                                    startActivity(i);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(RegisterDoctor.this, "Registration failed", Toast.LENGTH_SHORT ).show();

                                }
                            });

                }else{
                    Toast.makeText(RegisterDoctor.this, "All fields must be filled", Toast.LENGTH_SHORT ).show();
                }

            }
        });


        //goto login/sign in activty
        registerET = findViewById(R.id.signinD);

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