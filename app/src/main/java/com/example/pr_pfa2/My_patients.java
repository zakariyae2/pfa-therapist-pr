package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class My_patients extends AppCompatActivity {
    ListView list1;
    FirebaseDatabase database;
    DatabaseReference ref1;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    User user1;
    Button btnn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);
        list1=(ListView) findViewById(R.id.ls1);
        user1 = new User();
        btnn2 =(Button)  findViewById(R.id.btn2);
        database=FirebaseDatabase.getInstance();
        ref1=database.getReference("Users");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.patients_info,R.id.userinfo,list);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    user1 = ds.getValue(User.class);
                    list.add(user1.getFirstname()+" "+user1.getLastname()+"\n"+user1.getEmail()+"\n"+user1.getPhone());
                }
                list1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(My_patients.this,Add_patients.class);
                startActivity(add);
            }
        });
    }
}