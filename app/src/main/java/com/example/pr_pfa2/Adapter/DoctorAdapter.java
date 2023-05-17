package com.example.pr_pfa2.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Model.DoctorModel;
import com.example.pr_pfa2.DoctorProfile;
import com.example.pr_pfa2.R;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> list;
    static DoctorModel doctor;


    public interface OnItemClickListener {
        void onItemClick(DoctorModel data);
    }



    public DoctorAdapter(Context context, ArrayList<DoctorModel> list) {
        this.context = context;
        this.list = list;
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


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullName, address;
        RatingBar ratingbar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.fullnameTV);
            address = itemView.findViewById(R.id.addressTV);
            ratingbar = itemView.findViewById(R.id.ratingbar);


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
}
