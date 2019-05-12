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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportPeriodFragment extends Fragment {

    private DatePickerDialog dateStartPickerDialog;
    private DatePickerDialog dateEndPickerDialog;
    private String selectedStartDate;
    private String selectedEndDate;
    private BarChart chart;
    private SharedPrefManager sharedPrefManager;

    public ReportPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_report_period, container, false);
        sharedPrefManager = new SharedPrefManager(getActivity().getPreferences(Context.MODE_PRIVATE));

        final EditText selectedStartDateEv = view.findViewById(R.id.report_start_date_et);
        final EditText selectedEndDateEv = view.findViewById(R.id.report_end_date_et);

        dateStartPickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        selectedStartDate = Utilities.formatDate(year - 1900, month, day);
                        selectedStartDateEv.setText(selectedStartDate);

                    }
                }, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDay());


        selectedStartDateEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateStartPickerDialog.show();
            }
        });

        dateEndPickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        selectedEndDate = Utilities.formatDate(year - 1900, month, day);
                        selectedEndDateEv.setText(selectedEndDate);

                    }
                }, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDay());


        selectedEndDateEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEndPickerDialog.show();
                GetPeportRecordTask recordTask = new GetPeportRecordTask();
                recordTask.execute(sharedPrefManager.getUserId(), selectedStartDate, selectedEndDate);
            }
        });

        chart = view.findViewById(R.id.barChart);
        return view;
    }

    private class GetPeportRecordTask extends AsyncTask<String, Void, Report[]> {
        @Override
        protected Report[] doInBackground(String... strings) {
            ReportController reportController = new ReportController();
            Report[] report = reportController.getReportsForPeriod(strings[0], strings[1], strings[2]);
            return report;
        }

        @Override
        protected void onPostExecute(Report[] reports) {
            super.onPostExecute(reports);
            if (reports != null) {
                List<String> dates =  new ArrayList<>();


                List<BarEntry> consumedCalEntries = new ArrayList<>();
                List<BarEntry> burnedCalEntries = new ArrayList<>();
                for (int i = 0; i < reports.length; i++) {
                    dates.add(reports[i].getDate());
                    consumedCalEntries.add(new BarEntry(i, reports[i].getTotalCalorieConsumed()));
                    burnedCalEntries.add(new BarEntry(i, reports[i].getTotalCalorieBurned()));
                }

                BarDataSet set1 = new BarDataSet(consumedCalEntries, "Consumed Calories");
                BarDataSet set2 = new BarDataSet(burnedCalEntries, "Burned Calories");
                set1.setColor(Color.BLUE);
                set2.setColor(Color.RED);

                BarData data = new BarData(set1, set2);
                chart.setData(data);

                float barSpace = 0.02f;
                float groupSpace = 0.3f;
                int groupCount = 4;

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
                chart.getAxisLeft().setAxisMinimum(0);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setCenterAxisLabels(true);
                xAxis.setGranularityEnabled(true);

                data.setBarWidth(0.15f);
                chart.getXAxis().setAxisMinimum(0);
                chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                chart.groupBars(0, groupSpace, barSpace);
                Legend legend = chart.getLegend();
                legend.setEnabled(true);
                xAxis.setCenterAxisLabels(true);
                chart.invalidate();
            }

        }

    }
}
