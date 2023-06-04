package com.example.pr_pfa2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pr_pfa2.Adapter.AppointmentAdapter;
import com.example.pr_pfa2.Model.AppointmentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAppointments extends AppCompatActivity {

    RecyclerView recyclerView2;
    ArrayList<AppointmentModel> userArrayList;
    AppointmentAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String userEmail2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail2 = fAuth.getCurrentUser().getEmail();


        recyclerView2=(RecyclerView) findViewById(R.id.list_app_client);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<AppointmentModel>();
        adapter=new AppointmentAdapter(getApplicationContext(),userArrayList);
        recyclerView2.setAdapter(adapter);
        EventChangeListener();

    }

    private void EventChangeListener() {

        db.collection("Appointment").whereEqualTo("userID",currentUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){

                            Log.e("FireStore error",error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(AppointmentModel.class));
                            }
                            adapter.notifyDataSetChanged();



                        }

                    }
                });
    }



}