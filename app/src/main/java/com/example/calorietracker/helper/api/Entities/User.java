package com.example.calorietracker.helper.api.Entities;

import java.util.Date;

public class User {
    private String address;
    private String dob;
    private String email;
    private String gender;
    private Double height;
    private int levelOfActivity;
    private String name;
    private String postcode;
    private int stepsPerMile;
    private String surname;
    private int userId;
    private Double weight;

    public User(String name, String surname, String email, String dob, Double height,
                Double weight, String gender, String address, String postcode, int level_of_activity,
                int steps_per_mile) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        levelOfActivity = level_of_activity;
        stepsPerMile = steps_per_mile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getLevelOfActivity() {
        return levelOfActivity;
    }

    public void setLevelOfActivity(int levelOfActivity) {
        this.levelOfActivity = levelOfActivity;
    }

    public int getStepsPerMile() {
        return stepsPerMile;
    }

    public void setStepsPerMile(int stepsPerMile) {
        this.stepsPerMile = stepsPerMile;
    }
}
