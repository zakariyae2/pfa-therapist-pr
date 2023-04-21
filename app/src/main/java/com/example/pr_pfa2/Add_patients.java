package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_patients extends AppCompatActivity {
    EditText firstname,lastname,phone,email;
    Button btnn2;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_add_patients);
        firstname=(EditText) findViewById(R.id.editfn);
        lastname=(EditText) findViewById(R.id.editln);
        phone=(EditText) findViewById(R.id.editph);
        email=(EditText) findViewById(R.id.editem);
        btnn2=(Button) findViewById(R.id.btn3);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference();
        btnn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser();
            }
        });
    }
    public void writeNewUser(){
        User user=new User(firstname.getText().toString(),
                lastname.getText().toString(),
                phone.getText().toString(),
                email.getText().toString());
        ref.child("Users").child(user.getFirstname()).setValue(user);
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
    }

}
