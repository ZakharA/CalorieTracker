package com.example.calorietracker.helper.api;


import com.example.calorietracker.helper.api.Entities.Report;
import com.example.calorietracker.helper.api.Entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReportController extends Controller {
    private static final String BASE_URL = "http://10.0.2.2:8080/CalorieTrackerWS/webresources/ctrackerws.report/";
    private static final String BY_SPECIFIC_DATE = "getCaloriesReport/";
    private static final String BY_PERIOD_DATE = "getCalorieInfoForDatePeriod/";

    public void add(Report report) {
        add(report, BASE_URL);
    }

    public Report getReportForDate(String userId, String date) {
        Report[] reports = new Gson().fromJson(getJsonResponse(BASE_URL + BY_SPECIFIC_DATE + userId + "/" + date), Report[].class);
        if ( reports.length > 0 ) {
            return reports[0];
        } else {
            return null;
        }
    }

    public Report[] getReportsForPeriod(String userId, String startDate, String endDate) {
        Report[] reports = new Gson().fromJson(getJsonResponse(BASE_URL + BY_PERIOD_DATE + userId + "/" + startDate + "/" + endDate), Report[].class);
        if ( reports != null && reports.length > 0 ) {
            return reports;
        } else {
            return null;
        }
    }
}
