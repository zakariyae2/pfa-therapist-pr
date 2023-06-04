package com.example.pr_pfa2.Adapter;

import static android.view.Gravity.START;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pr_pfa2.Messages;
import com.example.pr_pfa2.Model.ContactModel;
import com.example.pr_pfa2.Model.MessageModel;
import com.example.pr_pfa2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    Context context;
    ArrayList<ContactModel> list;
    String targetFullName;
    static ContactModel contact;




    public interface OnItemClickListener {
        void onItemClick(ContactModel data);
    }



    public ContactAdapter(Context context, ArrayList<ContactModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        //v = inflater.inflate(R.layout.item_message_right, parent, false);


        itemView = inflater.inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        contact = list.get(position);
        String targetID;

        // Add a flag to track if the callback has been executed


        if (contact.getUser1ID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            targetID = contact.getUser2ID();
        } else {
            targetID = contact.getUser1ID();
        }

        holder.lastmsgTV.setText(contact.getLastMessage());

        DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(targetID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String email = documentSnapshot.getString("email");
                    String fullName = documentSnapshot.getString("fullName");
                    holder.fullnameTV.setText(fullName);

                    //holder.lastmsgTV.setText(targetID);

                    // Set the flag to true to indicate that the callback has been executed
                    //isCallbackExecuted = true;

            }
        });


/*
        if(contact.getUser1ID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            targetID = contact.getUser2ID();
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(targetID);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String email;

                    email = documentSnapshot.getString("email");
                    holder.fullnameTV.setText(email);

                }
            });

            holder.lastmsgTV.setText(targetID);
        }else{
            targetID = contact.getUser1ID();
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(targetID);
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String email;

                    email = documentSnapshot.getString("email");
                    holder.fullnameTV.setText(email);

                }
            });

            holder.lastmsgTV.setText(targetID);
        }



 */


        //holder.lastmsgTV.setText(targetID);




/*
        if(contact.getUser1ID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(contact.getUser2ID());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                     holder.fullnameTV.setText(documentSnapshot.getString("email"));
                     holder.lastmsgTV.setText(contact.getUser2ID());

                }
            });
        }else{
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(contact.getUser1ID());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    holder.fullnameTV.setText(documentSnapshot.getString("email"));
                    holder.lastmsgTV.setText(contact.getUser1ID());

                }
            });

       }

 */

       // holder.fullnameTV.setText(targetFullName);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                    Intent i = new Intent(v.getContext(), Messages.class);
                    if(contact.getUser1ID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    i.putExtra("targetID",contact.getUser2ID());
                    }else{
                        i.putExtra("targetID",contact.getUser1ID());
                    }

                    v.getContext().startActivity(i);

 */

                Intent i = new Intent(v.getContext(), Messages.class);
                i.putExtra("targetID",targetID);
                //i.putExtra("targetID",targetID);

                v.getContext().startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullnameTV;
        TextView lastmsgTV;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullnameTV = itemView.findViewById(R.id.contactFNTV);
            lastmsgTV = itemView.findViewById(R.id.lastMessageTV);





            /*
            if(true){
                messageTV = itemView.findViewById(R.id.messageRTV);
                messageTimestampTV = itemView.findViewById(R.id.messagetimestampRTV);
            }else{
                messageTV = itemView.findViewById(R.id.messageLTV);
                messageTimestampTV = itemView.findViewById(R.id.messagetimestampLTV);
            }

             */


/*
                messageLTV = itemView.findViewById(R.id.messageLTV);
                messageTimestampLTV = itemView.findViewById(R.id.messagetimestampLTV);

 */


            /*
            messageLL = itemView.findViewById(R.id.messageLL);
            messageCV = itemView.findViewById(R.id.messageCV);

             */

            /*

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DoctorProfile.class);
                    i.putExtra("fullname",fullName.getText().toString());
                    i.putExtra("address",address.getText().toString());
                    i.putExtra("phonenumber", doctor.getPhoneNumber());
                    i.putExtra("email", doctor.getEmail());
                    i.putExtra("qualifications", doctor.getQualifications());
                    i.putExtra("city", doctor.getCity());
                    i.putExtra("rating", ratingbar.getRating());
                    i.putExtra("targetID", doctor.getTargetID());

                    v.getContext().startActivity(i);
                }
            });

             */

        }

    }


/*
    public Boolean checkCurrentUser(){

        if(message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return true;
        }else return false;
    }

 */


}




