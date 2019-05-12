package com.example.calorietracker.helper.api;


import com.example.calorietracker.helper.Utilities;
import com.example.calorietracker.helper.api.Entities.Consumption;
import com.example.calorietracker.helper.api.Entities.Food;
import com.example.calorietracker.helper.api.Entities.User;

import java.util.Date;

public class ConsumptionController extends Controller {
    private static final String BASE_URL = "http://10.0.2.2:8080/CalorieTrackerWS/webresources/ctrackerws.consumption/";
    private static final String TOTAL_CAL_CONSUMPTION = "totalCaloriesComsumed/";
    public static Consumption createConsumptionRecord(Food selectedFood, short numberOfServings, User userId) {
        return new Consumption(Utilities.getCurrentFormatedDateAsString(),
                selectedFood, numberOfServings, userId);
    }
    public void logConsumedFood(Consumption record) {
        add(record, BASE_URL);
    }

    public int getCurrnetTotalCaloriesConsumed(String userId, String date) {
        return Integer.parseInt(getPlainResponse(BASE_URL + TOTAL_CAL_CONSUMPTION + userId + "/" + date));
    }

}
