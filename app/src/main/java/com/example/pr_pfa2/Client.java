package com.example.pr_pfa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

import java.util.HashMap;
import java.util.Map;

public class Client extends AppCompatActivity {

    Button logoutButton;
    TextView helloMSG;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String fullname;
    String userEmail3;
    String uid;
    BottomNavigationView bottomNav;



    RelativeLayout doctors_card, profile_card,appointment_card, myAppointments_card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail3 = currentUser.getEmail();
        uid=currentUser.getUid();

        //get the user's fullname and display it
        DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fullname = documentSnapshot.getString("fullName");
                helloMSG = findViewById(R.id.tvHelloMSG1);
                helloMSG.setText("Hello " + fullname.toUpperCase());

            }
        });


        //get current user fullname

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String currentUserID = firebaseAuth.getCurrentUser().getUid();

        db.collection("Users")
                .document(currentUserID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullName");
                        // Do something with the full name
                        //System.out.println("Full Name: " + fullName);
                        //start video Call service
                        startService(FirebaseAuth.getInstance().getCurrentUser().getUid(), fullName);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    System.out.println("Error retrieving full name: " + e.getMessage());
                });







        //signout
        logoutButton = findViewById(R.id.signoutC);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fAuth.signOut();

                Intent i = new Intent(v.getContext(), Login.class);
                startActivity(i);
                finish();

            }
        });



        //goto Doctors activity doctors list where the client can choose a therapist

        doctors_card = findViewById(R.id.cardDoctors);

        doctors_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Doctors.class));
            }
        });


        //goto MyAppointments Actiivty

        myAppointments_card = findViewById(R.id.cardMyAppointments);

        myAppointments_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyAppointments.class));
            }
        });


        //goto messages activity

        bottomNav = findViewById(R.id.bottomNavViewClient);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                   /* case R.id.home:
                        // Handle item 1 click
                        startActivity(new Intent(getApplicationContext(), Client.class));
                        return true;

                    */
                    case R.id.messages:
                        // Handle item 2 click
                        startActivity(new Intent(getApplicationContext(), ContactList.class));
                        return true;
                    case R.id.profile:
                        // Handle item 3 click
                        startActivity(new Intent(getApplicationContext(), MyProfileClient.class));
                        return true;

                    // Add more cases for each item in your Bottom Navigation Bar
                }
                return false;
            }
        });

        //goto appointment activity

        appointment_card = findViewById(R.id.cardAppointment);
        appointment_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent App=new Intent(Client.this,CalendarAppointment.class);
                fStore.collection("Mypatients").whereEqualTo("emailpat", userEmail3)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // The query result is not empty
                                    startActivity(App);
                                } else {
                                    // The query result is empty
                                    Toast.makeText(Client.this, "You are not signed with a therapist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                                Toast.makeText(Client.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        //goto myprofile acitivity

        profile_card = findViewById(R.id.cardProfile);
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MyProfileClient.class));
            }
        });



        //start videoCall Service
        //startService(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //startService("1");


    }



    //start videoCall Service
    public void startService(String userID, String fullname) {
        Application application = getApplication(); // Android's application context
        long appID = 1060828632;   // yourAppID
        String appSign = "2e33564f73ba01f471f648122a3f65295dbb67e0fb5f6d79e2fdd96e94a6f3ac";  // yourAppSign
        //String userID = ; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName = fullname;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName, callInvitationConfig);
    }




    /*
    public void rule(){
        fStore.collection("Mypatients").whereEqualTo("emailpat",userEmail2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        startActivity(new Intent(getApplicationContext(), CalendarAppointment.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(Client.this, "you are not signed with a therapist", Toast.LENGTH_SHORT).show();
                    }
                });
    }
     */
}