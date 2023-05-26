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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Model.AppointmentModel;
import com.example.pr_pfa2.R;

import java.util.ArrayList;
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
    }

    @Override
    public int getItemCount() {
        return shedul.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Fullname,Phone,Email,Shedule;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname=itemView.findViewById(R.id.fullnameApp);
            Phone=itemView.findViewById(R.id.phoneApp);
            Email=itemView.findViewById(R.id.addressApp);
            Shedule=itemView.findViewById(R.id.sheduleApp);
        }
    }
}
