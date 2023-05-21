package com.example.pr_pfa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr_pfa2.CalendarViewHolder;
import com.example.pr_pfa2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private Context context;
    private ArrayList<String> daysOfMonth;
    private OnItemListener onItemListener;
    private int selectedDatePosition = -1;
    private int selectedYear;
    private int selectedMonth;
    private Map<String, Boolean> scheduledAppointments; // Store scheduled appointments

    public CalendarAdapter(Context context, ArrayList<String> daysOfMonth, OnItemListener onItemListener, int selectedYear, int selectedMonth) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.selectedYear = selectedYear;
        this.selectedMonth = selectedMonth;
        this.scheduledAppointments = new HashMap<>();
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, this, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        String dayText = daysOfMonth.get(position);
        if (!dayText.isEmpty()) {
            // Format the selected date based on the dayText
            LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth, Integer.parseInt(dayText));
            String date = selectedDate.toString();

            // Query Firestore to check if there is a scheduled appointment for the selected date
            FirebaseFirestore.getInstance().collection("Appointment")
                    .whereEqualTo("state", "sheduled")
                    .whereEqualTo("date", date)
                    .whereEqualTo("month", String.valueOf(selectedMonth))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Set the background color to blue if there is a scheduled appointment
                                holder.itemView.setBackgroundResource(R.color.blue);
                                holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_blue_dark));
                                holder.dayOfMonth.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, selectedDate.getDayOfWeek().name(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Set the default background color if there is no scheduled appointment
                                holder.itemView.setBackgroundResource(0);
                                holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
                            }
                        }
                    });
        } else {
            // Set the default background color for empty days
            holder.itemView.setBackgroundResource(0);
            holder.dayOfMonth.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public void setDaysOfMonth(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
        notifyDataSetChanged();
    }

    public void setSelectedDatePosition(int position) {
        this.selectedDatePosition = position;
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText, CalendarAdapter adapter);
    }
}