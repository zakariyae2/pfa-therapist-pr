package com.example.pr_pfa2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.R;
import com.example.pr_pfa2.Model.RequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class request_adapter extends RecyclerView.Adapter<request_adapter.MyViewHolder>{
    Context context;
    ArrayList<RequestModel> requestlist;



    public request_adapter(Context context, ArrayList<RequestModel> requestlist) {
        this.context = context;
        this.requestlist = requestlist;
    }

    @NonNull
    @Override
    public request_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull request_adapter.MyViewHolder holder, int position) {

        RequestModel user = requestlist.get(position);
        holder.Fullname.setText(user.getFullName());
        holder.Emailpat.setText(user.getemailpat());
        /*if (user.isConfirmed()) {
            holder.confirmBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
            holder.confirmedTxt.setVisibility(View.VISIBLE);
        } else {
            holder.confirmBtn.setVisibility(View.VISIBLE);
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.confirmedTxt.setVisibility(View.GONE);
        }*/

        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmRequest(user.getemaildoc(), v.getContext());
            }
        });


    }

    @Override
    public int getItemCount() {
        return requestlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Fullname, Emailpat, confirmedTxt;
        Button confirmBtn, deleteBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname = itemView.findViewById(R.id.fulltxt);
            Emailpat = itemView.findViewById(R.id.emailtxt);
            confirmBtn = itemView.findViewById(R.id.confirm);
            deleteBtn = itemView.findViewById(R.id.delete);
            confirmedTxt = itemView.findViewById(R.id.confirmedtxt);
        }
    }

    public static void confirmRequest(String emaildoc, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference requestRef = db.collection("requests").document(emaildoc);

        requestRef.update("isConfirmed", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("requests").document(emaildoc).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Get the request data
                                        String fullName = document.getString("fullName");
                                        String emailpat = document.getString("emailpat");
                                        String emaildoc = document.getString("emaildoc");

                                        // Delete the request from the requests collection
                                        db.collection("requests").document(emaildoc)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Add the request to the Mypatients collection
                                                        Map<String, Object> patient = new HashMap<>();
                                                        patient.put("fullName", fullName);
                                                        patient.put("emailpat", emailpat);
                                                        patient.put("emaildoc", emaildoc);
                                                        db.collection("Mypatients").add(patient)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        ((Activity) context).runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(context, "Request confirmed and patient added to Mypatients", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        ((Activity) context).runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(context, "Error adding patient to Mypatients", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        ((Activity) context).runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(context, "Error deleting request", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Error confirming request", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

}
