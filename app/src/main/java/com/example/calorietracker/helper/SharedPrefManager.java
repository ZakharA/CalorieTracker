package com.example.calorietracker.helper;

import android.content.SharedPreferences;

import com.example.calorietracker.R;
import com.example.calorietracker.helper.api.Entities.User;
import com.google.gson.Gson;

public class SharedPrefManager {
    private SharedPreferences shPref;

    public SharedPrefManager(SharedPreferences shPref){
        this.shPref = shPref;
    }

    public void addUserInfo(User user){
        SharedPreferences.Editor editor = shPref.edit();
        editor.putString("Pref_name", user.getName());
        editor.putString("user_address", user.getAddress());
        editor.putString("user_postCode", user.getPostcode());
        editor.putInt("user_id", user.getUserId());
        editor.putString("user", new Gson().toJson(user));
        editor.commit();
    }

    public int getCaloriesGoal(){
        return shPref.getInt("calorie_goal", 0);
    }

    public String getUserId(){
        return String.valueOf(shPref.getInt("user_id", 0));
    }

    public String getUserObject() {
        return shPref.getString("user", "");
    }

    public User getUser() {
        return new Gson().fromJson(shPref.getString("user", ""), User.class);
    }

}
