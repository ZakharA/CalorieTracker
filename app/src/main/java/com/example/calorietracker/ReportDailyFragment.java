package com.example.calorietracker;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.calorietracker.helper.SharedPrefManager;
import com.example.calorietracker.helper.Utilities;
import com.example.calorietracker.helper.api.Entities.Report;
import com.example.calorietracker.helper.api.ReportController;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportDailyFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private String selectedDate;
    private PieChart chart;
    private SharedPrefManager sharedPrefManager;

    public ReportDailyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_report_daily, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity().getPreferences(Context.MODE_PRIVATE));

        final EditText selectedDateEv = view.findViewById(R.id.report_start_date_et);

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        selectedDate = Utilities.formatDate(year - 1900, month, day);
                        selectedDateEv.setText(selectedDate);
                        GetPeportRecordTask recordTask = new GetPeportRecordTask();
                        recordTask.execute(sharedPrefManager.getUserId(), selectedDate);
                    }
                }, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDay());


        selectedDateEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        chart = view.findViewById(R.id.pieChart);
        return view;
    }

    private class GetPeportRecordTask extends AsyncTask<String, Void, Report> {
        @Override
        protected void onPostExecute(Report report) {
            super.onPostExecute(report);
            if (report != null) {
                List<PieEntry> entries =  new ArrayList<>();
                entries.add(new PieEntry(report.getTotalCalorieConsumed(), "Consumed"));
                entries.add(new PieEntry(report.getTotalCalorieBurned(), "Burned"));
                entries.add(new PieEntry(report.getDailyCalorieGoal() + report.getTotalCalorieBurned() - report.getTotalCalorieConsumed() , "Remaining"));
                PieDataSet set = new PieDataSet(entries, "Result");
                set.setColors(Color.parseColor("Green"), Color.parseColor("Yellow"), Color.parseColor("Red"));
                PieData data = new PieData(set);
                chart.setData(data);
                chart.invalidate();
            } else {
                chart.setCenterText("Sorry! could not find anything!");
                chart.setCenterTextColor(Color.RED);
                chart.invalidate();
            }

        }

        @Override
        protected Report doInBackground(String... strings) {
            ReportController reportController = new ReportController();
            Report report = reportController.getReportForDate(strings[0], strings[1]);
            return report;
        }
    }
}
