package com.example.calorietracker.helper.api.Entities;

import java.util.Date;

public class Report {
    private int dailyCalorieGoal;
    private String  date;
    private int reportId;
    private float totalCalorieBurned;
    private int totalCalorieConsumed;
    private int totalStepsTaken;
    private User userId;

    public Report(int dailyCalorieGoal, String date, float totalCalorieBurned, int totalCalorieConsumed, int totalStepsTaken, User userId) {
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.date = date;
        this.totalCalorieBurned = totalCalorieBurned;
        this.totalCalorieConsumed = totalCalorieConsumed;
        this.totalStepsTaken = totalStepsTaken;
        this.userId = userId;
    }

    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public float getTotalCalorieBurned() {
        return totalCalorieBurned;
    }

    public void setTotalCalorieBurned(float totalCalorieBurned) {
        this.totalCalorieBurned = totalCalorieBurned;
    }

    public int getTotalCalorieConsumed() {
        return totalCalorieConsumed;
    }

    public void setTotalCalorieConsumed(int totalCalorieConsumed) {
        this.totalCalorieConsumed = totalCalorieConsumed;
    }

    public int getTotalStepsTaken() {
        return totalStepsTaken;
    }

    public void setTotalStepsTaken(int totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
