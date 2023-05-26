package com.example.pr_pfa2;
/*
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.pr_pfa2.Adapter.request_adapter;
import com.example.pr_pfa2.Model.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class patientrequest extends AppCompatActivity {
    RecyclerView recyclerView2;
    ArrayList<RequestModel> requestArrayList;
    request_adapter adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    FirebaseFirestore mStore;
    String userEmail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientrequest);



        fAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();





        recyclerView2=(RecyclerView) findViewById(R.id.recyclreq2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        requestArrayList = new ArrayList<RequestModel>();
        adapter=new request_adapter(patientrequest.this,requestArrayList);
        recyclerView2.setAdapter(adapter);
        EventChangeListener();

    }

    private void EventChangeListener() {


        db.collection("requests").whereEqualTo("emaildoc",userEmail)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                requestArrayList.add(dc.getDocument().toObject(RequestModel.class));
                            }
                            adapter.notifyDataSetChanged();


                        }

                    }
                });
    }


}

 */