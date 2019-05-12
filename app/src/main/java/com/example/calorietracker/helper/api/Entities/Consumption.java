package com.example.calorietracker.helper.api.Entities;

import java.util.Date;

public class Consumption {
    private int consumptionId;
    private String date;
    private Food foodId;
    private short numberOfServings;
    private User userId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
    }

    public short getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(short numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Consumption(String date, Food foodId, short numberOfServings, User userId) {
        this.date = date;
        this.foodId = foodId;
        this.numberOfServings = numberOfServings;
        this.userId = userId;
    }
}
