package com.example.calorietracker.helper.api;

import com.example.calorietracker.helper.api.Entities.Food;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class FoodController extends Controller {
    private static final String BASE_URL = "http://10.0.2.2:8080/CalorieTrackerWS/webresources/ctrackerws.food/";
    private static final String FOOD_CATEGORY = "findByCategory/";

    public List<Food> getFoodWithCategory(String category){
        Gson gson = new Gson();
        String jsonResponse = getJsonResponse(BASE_URL + FOOD_CATEGORY + category);
        List<Food>  foodList = Arrays.asList(gson.fromJson(jsonResponse, Food[].class));
        return foodList;
    }

    public void add(Food food) {
        add(food, BASE_URL);
    }
}
