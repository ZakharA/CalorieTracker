package com.example.calorietracker.helper.api;

import android.text.TextUtils;

import com.example.calorietracker.BuildConfig;
import com.example.calorietracker.helper.api.Entities.Food;
import com.example.calorietracker.helper.api.Entities.NndFood;
import com.example.calorietracker.helper.api.Entities.NndItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;


public class NndAPIController extends Controller {
    final static private String API_KEY = BuildConfig.ndbAPIKey;
    final static private String API_SEARCH_URL = "https://api.nal.usda.gov/ndb/search/?";
    final static private String API_FOOD_REPORT_URL = "https://api.nal.usda.gov/ndb/V2/reports?";
    final static private String API_FOOD_NUTRIENTS_URL = "https://api.nal.usda.gov/ndb/nutrients/?";

    public List<NndItem> searchNndItemByName(String foodName){
        Gson gson = new Gson();
        String[] param = {"format=json"
        ,"q=" + foodName
        ,"sort=n"
        ,"max=" + 25
        ,"offset=" + 0
        ,"api_key=" + API_KEY};

        String result = getJsonResponse(API_SEARCH_URL + TextUtils.join("&", param));

        JsonParser parser = new JsonParser();
        JsonObject element = (JsonObject)parser.parse(result);
        JsonArray responseWrapper = element.get("list").getAsJsonObject().get("item").getAsJsonArray();

        List<NndItem> data = Arrays.asList(gson.fromJson(responseWrapper, NndItem[].class));
        return data;
    }

    public NndFood getFoodItemWithCaloriesAndFat(String nndNumber){
        Gson gson = new Gson();
        String[] param = {"format=json"
                ,"api_key=" + API_KEY
                ,"nutrients=208"
                ,"nutrients=204"
                ,"ndbno=" + nndNumber};

        String result = getJsonResponse(API_FOOD_NUTRIENTS_URL + TextUtils.join("&", param));

        JsonParser parser = new JsonParser();
        JsonObject element = (JsonObject)parser.parse(result);
        JsonArray responseWrapper = element.get("report").getAsJsonObject().get("foods").getAsJsonArray();

        List<NndFood> data = Arrays.asList(gson.fromJson(responseWrapper, NndFood[].class));

        return data.get(0);
    }


    public Food getFoodById(String nndNumber){
        Gson gson = new Gson();
        Food newFood = new Food();
        String[] param = {"ndbno=" + nndNumber
                ,"type=f"
                ,"format=json"
                ,"api_key=" + API_KEY
                };

        String result = getJsonResponse(API_FOOD_REPORT_URL + TextUtils.join("&", param));

        JsonParser parser = new JsonParser();
        JsonObject element = (JsonObject)parser.parse(result);
        JsonArray responseWrapper = element.get("foods").getAsJsonArray();
        JsonObject foodObject = responseWrapper.get(0).getAsJsonObject().get("food").getAsJsonObject();

        JsonObject foodDescription = foodObject.get("desc").getAsJsonObject();
        String foodName = getFoodnameFrom(foodDescription.get("name").getAsString());
        String foodCategory = foodDescription.has("fg") ? foodDescription.get("fg").getAsString() : "Other";
        String foodUnits = foodDescription.get("ru").getAsString();

        int foodCalories = 0;
        float foodFat = 0;
        JsonArray nutrientsObject = foodObject.get("nutrients").getAsJsonArray();
        for (JsonElement element1: nutrientsObject){
            JsonObject elmObj = element1.getAsJsonObject();
            String nutrietId = elmObj.get("nutrient_id").getAsString();
            if( nutrietId.equals("208")){
                foodCalories = elmObj.get("value").getAsInt();
            }
            if(nutrietId.equals("204")){
                foodFat = elmObj.get("value").getAsFloat();
            }
        }

        return new Food(foodCalories, foodCategory, foodFat, foodName, 1, foodUnits);
    }

    private String getFoodnameFrom(String name) {
        String[] foodNameElements = name.split(",");
        if(foodNameElements.length > 0){
            return foodNameElements[0].concat(foodNameElements[1]);
        } else {
            return name;
        }
    }
}
