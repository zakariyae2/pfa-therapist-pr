package com.example.pr_pfa2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pr_pfa2.Adapter.DoctorAdapter;
import com.example.pr_pfa2.Adapter.MessageAdapter;
import com.example.pr_pfa2.Model.DoctorModel;
import com.example.pr_pfa2.Model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages extends AppCompatActivity {
    private String targetID, currentUID, chatID;
    private String targetFullName;
    //private ArrayList<String> participantsIDS;
    EditText messageET;
    ImageView sendMSGIV;
    TextView fullnameMSGTV;
    //private String message;
    MessageModel message;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference chatMessagesRef;

    FirebaseAuth fAuth;
    DocumentReference docRef;

    RecyclerView recyclerview;
    MessageAdapter myAdapter;
    ArrayList<MessageModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);



        //get target ID
        Intent i = getIntent();
        targetID = i.getStringExtra("targetID");




        // current userid

        fAuth = FirebaseAuth.getInstance();
       currentUID = fAuth.getCurrentUser().getUid();

        // generate unique chatID!

        chatID = generateChatId(currentUID, targetID);


        chatMessagesRef = fStore.collection("Chats");
        fullnameMSGTV = findViewById(R.id.fullnameMSGTV);

        //get target users full and display it
        /*
        docRef = fStore.collection("Users").document(targetID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                targetFullName = document.getString("full_name");

                fullnameMSGTV.setText();

            }
        });

         */


        recyclerview = findViewById(R.id.messagesRV);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        list = new ArrayList<>();
        myAdapter = new MessageAdapter(this, list);
        recyclerview.setAdapter(myAdapter);


        docRef = fStore.collection("Users").document(targetID);

        docRef.get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // The document exists, retrieve the full name
                         targetFullName = document.getString("fullName");
                           fullnameMSGTV.setText(targetFullName);
                        // Do something with the full name
                        // ...
                    } else {
                        // The document doesn't exist
                        // Handle the case accordingly
                    }
                } else {
                    // An error occurred while retrieving the document
                    // Handle the error
                }
            }
        });



        //send message

        messageET = findViewById(R.id.sendMSGET);
        sendMSGIV = findViewById(R.id.sendMSGIV);

        sendMSGIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageContent = messageET.getText().toString().trim();
                //sendMessage(message);
                //MessageModel(String messageId, String senderId, String targetId, String messageText)
                Timestamp currentTimeStamp = Timestamp.now();

                message = new MessageModel(currentUID, targetID ,messageContent, currentTimeStamp );
                sendMessage(message);



                }

        });

        //call display messages method


        listenForMessages();





    }


    // generate unique chat id
    private String generateChatId(String user1Id, String user2Id) {
        // Sort the user IDs alphabetically to ensure consistent chat ID

        if (user1Id.compareTo(user2Id) < 0) {
            return user1Id + "_" + user2Id;
        } else {
            return user2Id + "_" + user1Id;
        }

    }



    private void sendMessage(MessageModel message) {
        chatMessagesRef
                .document(chatID)
                .collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> {
                    // Message sent successfully
                    messageET.setText("");
                    Map<String, Object> userInfo = new HashMap<>();

                    userInfo.put("user1ID", currentUID);
                    userInfo.put("user2ID", targetID);
                    userInfo.put("lastMessage", message.getMessageText());
                    /*
                    String[] participantsIDS = new String[] {currentUID,targetID};
                    userInfo.put("participantsIDS", participantsIDS);

                     */
                    chatMessagesRef.document(chatID).set(userInfo);
                    myAdapter.notifyDataSetChanged();
                    //scrollToLastPosition();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while sending the message
                    // Handle the error as per your requirement
                });
    }


    //display messages


    private void listenForMessages() {

        chatMessagesRef
                .document(chatID)
                .collection("messages")
                .orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Error retrieving messages: ", error);
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        // Handle newly added message
                        DocumentSnapshot messageSnapshot = dc.getDocument();
                        // Extract message data using messageSnapshot

                        MessageModel message = messageSnapshot.toObject(MessageModel.class);
                        list.add(message);

                    }
                    myAdapter.notifyDataSetChanged();
                    scrollToLastPosition();
                }


            }
        });



/*
        chatMessagesRef
                .document(chatID)
                .collection("messages")
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> doctors = new ArrayList<>();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            MessageModel doctor = document.toObject(MessageModel.class);

                            list.add(message);

                            //order data based on rating
                           // Collections.sort(list, (item1, item2) -> Float.compare(item2.getRating(), item1.getRating()));

                        }
                        // display the list of doctors in the RecyclerView



                        myAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // handle errors here
                    }
                });

 */


        /*
        Query query = chatMessagesRef
                .document(chatID)
                .collection("messages")
                .orderBy("timestamp"); // Replace "timestamp" with the field name for the message timestamp

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable Exception e) {
                if (e != null) {
                    Log.e("Firestore", "Listen failed.", e);
                    return;
                }

                for (DocumentChange dc : snapshot.getDocumentChanges()) {
                    DocumentSnapshot document = dc.getDocument();
                    String message = document.getString("messageContent"); // Replace "message" with the field name for the message text

                    // Create a TextView to display the message
                    TextView messageTextView = new TextView(MainActivity.this);
                    messageTextView.setText(message);

                    // Add the message to the chat container
                    chatContainer.addView(messageTextView);
                }
            }
        });



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("Users");

        usersRef.whereEqualTo("role", "doctor")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> doctors = new ArrayList<>();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            DoctorModel doctor = document.toObject(DoctorModel.class);
                            doctor.setTargetID(document.getId());


                            list.add(doctor);

                            //order data based on rating
                            Collections.sort(list, (item1, item2) -> Float.compare(item2.getRating(), item1.getRating()));

                        }
                        // display the list of doctors in the RecyclerView



                        myAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // handle errors here
                    }
                });


         */


    }



    //scroll to las message


    private void scrollToLastPosition() {
        if (recyclerview.getAdapter() != null) {
            int lastPosition = recyclerview.getAdapter().getItemCount() - 1;
            recyclerview.scrollToPosition(lastPosition);
            //myAdapter.notifyDataSetChanged();
        }
    }






}