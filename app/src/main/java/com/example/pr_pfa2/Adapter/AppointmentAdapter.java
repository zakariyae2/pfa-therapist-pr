package com.example.pr_pfa2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Appointement;
import com.example.pr_pfa2.Model.AppointmentModel;
import com.example.pr_pfa2.Model.DoctorModel;
import com.example.pr_pfa2.R;
import com.example.pr_pfa2.VideoCall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder>{
    Context context;
    ArrayList<AppointmentModel> shedul;

    public AppointmentAdapter(Context context, ArrayList<AppointmentModel> shedul) {
        this.context = context;
        this.shedul = shedul;
    }


    @NonNull
    @Override
    public AppointmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_app,parent,false);
        return new AppointmentAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MyViewHolder holder, int position) {
        AppointmentModel shed = shedul.get(position);
        holder.Fullname.setText(shed.getFullName());
        holder.Phone.setText(shed.getPhone());
        holder.Email.setText(shed.getEmailpat());
        holder.Shedule.setText(shed.getDate());
        holder.hour.setText(shed.getHour());
        holder.state.setText(shed.getState());

        /*
        holder.startVideoCallBtn.setIsVideoCall(true);
        holder.startVideoCallBtn.setResourceID("zego_uikit_call");
        holder.startVideoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser("3A0VsYHvg1d6zH0DDmvabP51sWr2")));

         */


        //get TargetID

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Appointment")
                        .whereEqualTo("emailpal", shed.getEmailpat())
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                                //startVideoCall
                                String targetUserID = document.getString("userID");

                                //holder.targetUserID = document.getString("userID");


                            }
                        })
                        .addOnFailureListener(e -> {
                            // Handle any errors
                            System.out.println("Error retrieving userID: " + e.getMessage());
                        });

        //start video call

        if(shed.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.startVideoCallBtn.setVisibility(View.GONE);
            holder.startAudioCallBtn.setVisibility(View.GONE);
        }
        holder.startVideoCallBtn.setIsVideoCall(true);
        holder.startVideoCallBtn.setResourceID("zego_uikit_call");
        holder.startVideoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(shed.getUserID())));


        //start audio call

        holder.startAudioCallBtn.setIsVideoCall(false);
        holder.startAudioCallBtn.setResourceID("zego_uikit_call");
        holder.startAudioCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(shed.getUserID())));

        //mark appoinment Done change state to Done

        holder.markAsDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference appointmentRef = db.collection("Appointment");

        // Query to find the document with the matching condition
                Query query = appointmentRef.whereEqualTo("emailpat", shed.getEmailpat());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (DocumentSnapshot document : task.getResult()) {
                                // Update the state field of the document to "Done"
                                document.getReference().update("state", "done")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Update successful
                                                // You can perform any additional actions here
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Update failed
                                                // Handle the failure appropriately
                                            }
                                        });
                            }
                        }
                });

            }
        });



        //Cancel appointemnt

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference appointmentRef = db.collection("Appointment");

                // Query to find the document with the matching condition
                Query query = appointmentRef.whereEqualTo("emailpat", shed.getEmailpat());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot document : task.getResult()) {
                            // Update the state field of the document to "Done"
                            document.getReference().update("state", "canceled")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Update successful
                                            // You can perform any additional actions here
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Update failed
                                            // Handle the failure appropriately
                                        }
                                    });
                        }
                    }
                });

            }
        });



        //change state color

        if(shed.getState().equals("sheduled")){
            holder.state.setTextColor(Color.BLUE);

        }else if(shed.getState().equals("done")){
            holder.state.setTextColor(Color.GREEN);
        }else if(shed.getState().equals("canceled")){
            holder.state.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return shedul.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Fullname,Phone,Email,Shedule,hour, state;
        Button startMeetBtn;
        Button markAsDoneBtn;
        Button cancelBtn;
        ZegoSendCallInvitationButton startVideoCallBtn;
        ZegoSendCallInvitationButton startAudioCallBtn;
        //String targetUserID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname=itemView.findViewById(R.id.fullnameApp);
            Phone=itemView.findViewById(R.id.phoneApp);
            Email=itemView.findViewById(R.id.addressApp);
            Shedule=itemView.findViewById(R.id.sheduleApp);
            hour = itemView.findViewById(R.id.hourApp);
            state = itemView.findViewById(R.id.stateApp);

            //startMeetBtn = itemView.findViewById(R.id.meet);
            startVideoCallBtn = itemView.findViewById(R.id.videoCallBtn);
            startAudioCallBtn = itemView.findViewById(R.id.audioCallBtn);

            markAsDoneBtn = itemView.findViewById(R.id.markAsDoneBtn);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
        }
    }


    public void filterList(ArrayList <AppointmentModel> filteredList){
        shedul = filteredList;
        notifyDataSetChanged();
    }



}
