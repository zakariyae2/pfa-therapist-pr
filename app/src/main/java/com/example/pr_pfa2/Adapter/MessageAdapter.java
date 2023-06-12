package com.example.pr_pfa2.Adapter;

import static android.view.Gravity.END;
import static android.view.Gravity.START;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pr_pfa2.Model.MessageModel;
import com.example.pr_pfa2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    Context context;
    ArrayList<MessageModel> list;
    static MessageModel message;
    View itemView;


    public interface OnItemClickListener {
        void onItemClick(MessageModel data);
    }



    public MessageAdapter(Context context, ArrayList<MessageModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //v = inflater.inflate(R.layout.item_message_right, parent, false);


            itemView = inflater.inflate(R.layout.item_message_right, parent, false);

        return new MyViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //DoctorModel doctor = list.get(position);
        message = list.get(position);
        Timestamp ts = message.getTimestamp();
        String senderId = message.getSenderId();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Date date = new Date(String.valueOf(ts.toDate()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStampString = dateFormat.format(date);

            holder.messageTV.setText(message.getMessageText());
            holder.messageTimestampTV.setText(timeStampString);

        if (message != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                // Set layout params for the right-aligned message views
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                // Update the layout params for the text views in the view holder
                //holder.linearLayoutRight.setLayoutParams(layoutParams);
                holder.messageTV.setLayoutParams(layoutParams);
                holder.linearLayoutRight.setGravity(END);
                //holder.messageTV.setBackgroundColor(Color.GREEN);
                Drawable customBackground = context.getResources().getDrawable(R.drawable.background_msg_right);
                holder.messageTV.setBackground(customBackground);
                holder.messageTimestampTV.setGravity(END);
                layoutParams.gravity = END;

            } else {
                // Set layout params for the left-aligned message views
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                // Update the layout params for the text views in the view holder
                //holder.linearLayoutRight.setLayoutParams(layoutParams);
                holder.messageTV.setLayoutParams(layoutParams);
                holder.linearLayoutRight.setGravity(START);
                //holder.messageTV.setBackgroundColor(Color.GRAY);
                Drawable customBackground = context.getResources().getDrawable(R.drawable.background_msg_left);
                holder.messageTV.setBackground(customBackground);
                holder.messageTimestampTV.setGravity(START);
                layoutParams.gravity = Gravity.START;
            }
        } else {
            // Handle the case when message or currentUser is null
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView messageTV;
        TextView messageTimestampTV;
        LinearLayout linearLayoutLeft, linearLayoutRight;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            messageTV = itemView.findViewById(R.id.messageTV);
            messageTimestampTV = itemView.findViewById(R.id.messagetimestampTV);
            linearLayoutRight = itemView.findViewById(R.id.messageRLL);
            linearLayoutLeft = itemView.findViewById(R.id.messageLLL);



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




