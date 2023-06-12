package com.example.pr_pfa2.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Model.DoctorModel;
import com.example.pr_pfa2.DoctorProfile;
import com.example.pr_pfa2.PayPal;
import com.example.pr_pfa2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> list;
    static DoctorModel doctor;
    String currentUserFullName;
    String currentUserEmail;
    String currentUserId;
    String currentUserPhone;


    /*public interface OnItemClickListener {
        void onItemClick(DoctorModel data);
    }

     */



    public DoctorAdapter(Context context, ArrayList<DoctorModel> list, String currentUserFullName, String currentUserEmail,String currentUserId, String currentUserPhone) {
        this.context = context;
        this.list = list;
        this.currentUserFullName = currentUserFullName;
        this.currentUserEmail = currentUserEmail;
        this.currentUserId = currentUserId;
        this.currentUserPhone = currentUserPhone;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       //DoctorModel doctor = list.get(position);
       doctor = list.get(position);
       holder.fullName.setText(doctor.getFullName());
       holder.address.setText(doctor.getAddress());
       holder.ratingbar.setRating(doctor.getRating());
        holder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DoctorModel doctor = list.get(position);

                // Get the email of the clicked doctor
                Sign(doctor);
                Intent pay = new Intent(v.getContext(), PayPal.class);
                v.getContext().startActivity(pay);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullName, address;
        Button requestButton;
        RatingBar ratingbar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.fullnameTV);
            address = itemView.findViewById(R.id.addressTV);
            ratingbar = itemView.findViewById(R.id.ratingbar);
            requestButton = itemView.findViewById(R.id.request);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DoctorProfile.class);

                    i.putExtra("fullname",doctor.getFullName());
                    i.putExtra("address",doctor.getAddress());
                    i.putExtra("phonenumber", doctor.getPhoneNumber());
                    i.putExtra("email", doctor.getEmail());
                    i.putExtra("qualifications", doctor.getQualifications());
                    i.putExtra("city", doctor.getCity());
                    i.putExtra("rating", ratingbar.getRating());
                    i.putExtra("targetID", doctor.getTargetID());

                    /*
                    i.putExtra("fullname",fullName.getText().toString());
                    i.putExtra("address",address.getText().toString());
                    i.putExtra("phonenumber", doctor.getPhoneNumber());
                    i.putExtra("email", doctor.getEmail());
                    i.putExtra("qualifications", doctor.getQualifications());
                    i.putExtra("city", doctor.getCity());
                    i.putExtra("rating", ratingbar.getRating());
                    i.putExtra("targetID", doctor.getTargetID());

                     */

                    v.getContext().startActivity(i);
                }
            });


        }

    }
    public void Sign(DoctorModel doctor){
        String emaildoc = doctor.getEmail();
        String email = currentUserEmail;
        String fullname = currentUserFullName;
        String Phone = currentUserPhone;
        String Id = currentUserId;


        // Send the email and name to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference requestsRef = db.collection("Mypatients");
        Map<String, Object> request = new HashMap<>();
        request.put("emaildoc", emaildoc);
        request.put("emailpat", email);
        request.put("fullName", fullname);
        request.put("phone", Phone);
        request.put("userId", Id);
        requestsRef.document(emaildoc).set(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Your signed with this doctor", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to sign up", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void filterList(ArrayList <DoctorModel> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
