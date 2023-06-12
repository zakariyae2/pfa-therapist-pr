package com.example.pr_pfa2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pr_pfa2.Adapter.AppointmentAdapter;
import com.example.pr_pfa2.Model.AppointmentModel;
import com.example.pr_pfa2.Model.DoctorModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class ListShedule extends AppCompatActivity {
    RecyclerView recyclerView2;
    ArrayList<AppointmentModel> userArrayList;
    AppointmentAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String userEmail2;
    String state;
    Spinner spinnerStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shedule);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail2 = fAuth.getCurrentUser().getEmail();


        spinnerStates = findViewById(R.id.states_spinner);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerStates.setAdapter(adapterSpinner);



/*
         spinnerStates = findViewById(R.id.states_spinner);



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerStates.setAdapter(adapterSpinner);

 */


/*



        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = parent.getItemAtPosition(position).toString();
                // Use the selectedValue as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });


 */




        recyclerView2=(RecyclerView) findViewById(R.id.list_app);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<AppointmentModel>();
        adapter=new AppointmentAdapter(ListShedule.this,userArrayList);
        recyclerView2.setAdapter(adapter);


        EventChangeListener();





        //search for state

        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected item change here
                String selectedItem = parent.getItemAtPosition(position).toString();
                filter(selectedItem);
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });



    }

    private void EventChangeListener() {

        db.collection("Appointment").whereEqualTo("emaildoc",userEmail2).orderBy("date").orderBy("hour")
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



    //Filter list

/*
    public void filter(String state){
        ArrayList <AppointmentModel> filteredAppointmentList = new ArrayList<>();

        // check fullnames containing text entered in search edittext
        for (AppointmentModel appointment : userArrayList){
            if(appointment.getState().equals(state)){
                filteredAppointmentList.add(appointment);

            }
        }

        adapter.filterList(filteredAppointmentList);
    }


 */



    public void filter(String state) {
        ArrayList<AppointmentModel> filteredAppointmentList = new ArrayList<>();

        if (state.isEmpty()) {
            // Show all items
            filteredAppointmentList.addAll(userArrayList);
        } else {
            // Filter based on the selected state
            for (AppointmentModel appointment : userArrayList) {
                if (appointment.getState().equals(state)) {
                    filteredAppointmentList.add(appointment);
                }
            }
        }

        adapter.filterList(filteredAppointmentList);
    }





}
