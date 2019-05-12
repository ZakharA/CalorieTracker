package com.example.calorietracker.helper.api;

import com.example.calorietracker.helper.api.Entities.User;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class UserController extends Controller {
    private static final String BASE_URL = "http://10.0.2.2:8080/CalorieTrackerWS/webresources/ctrackerws.appuser/";
    private static final String EMAIL_URL = "findByEmail/";
    private static final String CAL_BURN_AT_REST_URL = "totalDailyCalorieBurnedAtRest/";
    private static final String CAL_BURN_PER_STEP_URL = "caloriesBurnedPerStep/";

    public User getUserByEmail(String email) {
        Gson gson = new Gson();

        String jsonResponse = getJsonResponse(BASE_URL + EMAIL_URL + email);
        List<User> userList = Arrays.asList(gson.fromJson(jsonResponse, User[].class));
        return userList.get(0);
    }

    public static void add(User user) {
        add(user, BASE_URL);
    }

    public float getUserCalorieBurnAtRest(String userId){
        return Float.parseFloat(getPlainResponse(BASE_URL + CAL_BURN_AT_REST_URL + userId));
    }

    public float getUserCalorieBurnPerStep(String userId){
        return Float.parseFloat(getPlainResponse(BASE_URL + CAL_BURN_PER_STEP_URL + userId));
    }

}
