package com.example.pr_pfa2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button loginButton;
    TextView tvClientRegister, tvDoctorRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // Initialize Firebase Auth and Store
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();



        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                checkRole(authResult.getUser().getUid());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT ).show();
                            }
                        });

            }
        });


        //check if user is already logged in

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            checkRole(currentUser.getUid());
        }


        tvClientRegister = findViewById(R.id.tv_client_register);
        tvDoctorRegister = findViewById(R.id.tv_doctor_register);


        //goto client registration
        tvClientRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), RegisterClient.class));
            }
        });

        //goto doctor registration
        tvDoctorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), RegisterDoctor.class));
            }
        });

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

/*


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        //checkRole(currentUser.getUid);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

 */

}