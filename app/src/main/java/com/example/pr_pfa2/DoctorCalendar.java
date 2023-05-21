package com.example.pr_pfa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pr_pfa2.Adapter.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DoctorCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private TextView date, datev2;
    private int selectedDatePosition = -1;
    private CalendarAdapter calendarAdapter;
    private int selectedYear;
    private int selectedMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_appointment);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        selectedYear = selectedDate.getYear();
        selectedMonth = selectedDate.getMonthValue();
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(DoctorCalendar.this, daysInMonth, this, selectedYear, selectedMonth);
        } else {
            calendarAdapter.setDaysOfMonth(daysInMonth);
        }

        // Set the initial selected date to today's date
        LocalDate today = LocalDate.now();
        if (selectedDate.getMonthValue() == today.getMonthValue() &&
                selectedDate.getYear() == today.getYear()) {
            selectedDatePosition = today.getDayOfMonth() + selectedDate.getDayOfWeek().getValue() - 2;
        } else {
            selectedDatePosition = -1;
        }

        calendarAdapter.setSelectedDatePosition(selectedDatePosition);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText, CalendarAdapter adapter) {
        // Update the selected date position
        adapter.setSelectedDatePosition(position);

        // Refresh the adapter to highlight the selected date
        adapter.notifyDataSetChanged();

        date = findViewById(R.id.date1);
        datev2 = findViewById(R.id.date2);
        if (!dayText.isEmpty()) {
            LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth, Integer.parseInt(dayText));
            String message = selectedDate.toString();
            date.setText(message);

            // Update the selected date
            selectedDate = selectedDate.withDayOfMonth(Integer.parseInt(dayText));

            // Update the selected date position
            if (selectedDatePosition != -1) {
                calendarAdapter.notifyItemChanged(selectedDatePosition);
            }
            selectedDatePosition = position;
            calendarAdapter.notifyItemChanged(selectedDatePosition);
        }
    }
}