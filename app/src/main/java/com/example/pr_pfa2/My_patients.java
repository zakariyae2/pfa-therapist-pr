package com.example.pr_pfa2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Adapter.user_adapter;
import com.example.pr_pfa2.Model.UserModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class My_patients extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserModel> userArrayList;
    user_adapter adapter;
    FirebaseFirestore db;
    Button btnn2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();


        btnn2 =(Button)  findViewById(R.id.btn2);
        recyclerView=(RecyclerView) findViewById(R.id.recycl1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<UserModel>();
        adapter=new user_adapter(My_patients.this,userArrayList);
        recyclerView.setAdapter(adapter);
        EventChangeListener();

        pages();
    }

    private void EventChangeListener() {

        db.collection("Users").whereEqualTo("role","client").orderBy("fullName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("FireStore error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(UserModel.class));
                            }
                            adapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();


                        }

                    }
                });
    }


    public void pages(){
        btnn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(My_patients.this,Add_patients.class);
                startActivity(add);
            }
        });
    }
}

