package com.example.pr_pfa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.Model.UserModel;
import com.example.pr_pfa2.R;

import java.util.ArrayList;

public class user_adapter extends RecyclerView.Adapter<user_adapter.MyViewHolder>{
    Context context;
    ArrayList<UserModel> userlist;

    public user_adapter(Context context, ArrayList<UserModel> userlist) {
        this.context = context;
        this.userlist = userlist;
    }


    @NonNull
    @Override
    public user_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.users_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull user_adapter.MyViewHolder holder, int position) {
        UserModel user = userlist.get(position);
        holder.Fullname.setText(user.getFullName());
        holder.Phone.setText(user.getPhoneNumber());
        holder.Email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Fullname,Phone,Email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname=itemView.findViewById(R.id.fulltxt);
            Phone=itemView.findViewById(R.id.phonetxt);
            Email=itemView.findViewById(R.id.emailtxt);
        }
    }
}

