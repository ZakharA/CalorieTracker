package com.example.calorietracker.helper.api.Entities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.calorietracker.helper.api.GoogleSearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Food {
    private int foodId;
    private int calorieAmount;
    private String category;
    private float fat;
    private String name;
    private float servingAmount;
    private String servingUnit;

    public Food(int calorieAmount, String category, float fat, String name, float servingAmount, String servingUnit) {
        this.calorieAmount = calorieAmount;
        this.category = category;
        this.fat = fat;
        this.name = name;
        this.servingAmount = servingAmount;
        this.servingUnit = servingUnit;
    }

    public Food() {
    }

    @Override
    public String toString() {
        return "Food{" +
                "calorieAmount=" + calorieAmount +
                ", category='" + category + '\'' +
                ", fat=" + fat +
                ", name='" + name + '\'' +
                ", servingAmount=" + servingAmount +
                ", servingUnit='" + servingUnit + '\'' +
                '}';
    }

    public int getCalorieAmount() {
        return calorieAmount;
    }

    public void setCalorieAmount(int calorieAmount) {
        this.calorieAmount = calorieAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(short fat) {
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getServingAmount() {
        return servingAmount;
    }

    public void setServingAmount(float servingAmount) {
        this.servingAmount = servingAmount;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getInfo() {
        return  "calorie :" + calorieAmount +
                " | category: " + category +
                " | fat: " + fat +
                " | Serving Amount: " + servingAmount +
                " | Serving Unit: " + servingUnit ;
    }

    public int getFoodId() {
        return foodId;
    }
}
