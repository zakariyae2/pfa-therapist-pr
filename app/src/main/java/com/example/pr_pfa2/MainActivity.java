package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

import android.widget.RelativeLayout;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;


public class MainActivity extends AppCompatActivity {
    RelativeLayout R1;
    RelativeLayout R2;
    RelativeLayout R3;
    RelativeLayout R4;
    ImageButton btni1;
    ImageButton btni2;
    ImageButton btni3;
    ImageButton btni4;
    String userEmail2;
    Button logoutButton;
    TextView helloMSG;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    String fullname;
    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //startService("2");

        R1=(RelativeLayout) findViewById(R.id.bord1);
        R2=(RelativeLayout) findViewById(R.id.bord2);
        R3=(RelativeLayout) findViewById(R.id.bord3);
        R4=(RelativeLayout) findViewById(R.id.bord4);
        btni1=(ImageButton) findViewById(R.id.ac1);
        btni2=(ImageButton) findViewById(R.id.calen);
        btni3=(ImageButton) findViewById(R.id.prof);
        btni4=(ImageButton) findViewById(R.id.appo);
        logoutButton = findViewById(R.id.btnout1);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = fAuth.getCurrentUser();
        userEmail2 = fAuth.getCurrentUser().getEmail();

        DocumentReference df = fStore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fullname = documentSnapshot.getString("fullName");
                helloMSG = findViewById(R.id.txt1);
                helloMSG.setText("Hello " + fullname.toUpperCase());

            }
        });



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




        logoutButton = findViewById(R.id.btnout1);


        //goto my profile

        //nav bar Doctor MainAcitivity


        bottomNav = findViewById(R.id.bottomNavViewDoctor);
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
                        startActivity(new Intent(getApplicationContext(), MyProfileDoctor.class));
                        return true;

                    // Add more cases for each item in your Bottom Navigation Bar
                }
                return false;
            }
        });

        pages();

    }
    public void pages() {
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act1=new Intent(MainActivity.this,My_patients.class);
                startActivity(act1);
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act4=new Intent(MainActivity.this,DoctorCalendar.class);
                startActivity(act4);
            }
        });
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act4=new Intent(v.getContext(), MyProfileClient.class);
                startActivity(act4);
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListShedule.class));

            }
        });



        btni1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb1=new Intent(MainActivity.this,My_patients.class);
                startActivity(imb1);
            }
        });
        btni2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imb5=new Intent(MainActivity.this,DoctorCalendar.class);
                startActivity(imb5);
            }
        });
        btni4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListShedule.class));
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(v.getContext(), Login.class);
                startActivity(i);
                finish();

            }
        });
    }
    public void rule(){
        fStore.collection("Mypatients").whereEqualTo("emaildoc",userEmail2)
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
                        Toast.makeText(MainActivity.this, "there is no patients", Toast.LENGTH_SHORT).show();
                    }
                });


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




}