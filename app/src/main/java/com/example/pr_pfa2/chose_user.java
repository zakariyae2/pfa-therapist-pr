package com.example.pr_pfa2;

/*import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chose_user extends AppCompatActivity implements RecycleViewInterface {
    RecyclerView rec;
    DatabaseReference reference;
    user_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_user);
        rec = findViewById(R.id.recycl1);
        reference=FirebaseDatabase.getInstance().getReference();
        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions <User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(reference.child("Users"),User.class)
                        .build();
        adapter=new user_adapter(options);
        rec.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(int psition) {

    }
}*/