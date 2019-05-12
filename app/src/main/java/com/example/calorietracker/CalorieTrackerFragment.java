package com.example.calorietracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calorietracker.helper.SharedPrefManager;
import com.example.calorietracker.helper.Utilities;
import com.example.calorietracker.helper.api.ConsumptionController;
import com.example.calorietracker.helper.api.Entities.User;
import com.example.calorietracker.helper.api.UserController;
import com.example.calorietracker.helper.database.AppDatabase;
import com.google.gson.Gson;


public class CalorieTrackerFragment extends Fragment {
    TextView calGoalTv;
    TextView totalCalConsumedTv;
    TextView burnedCaloriesTv;
    TextView totalNumberOfStepsTv;
    private SharedPrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);

        prefManager = new SharedPrefManager(getActivity().getPreferences(Context.MODE_PRIVATE));
        GetUserStatTask getUserStatTask = new GetUserStatTask();
        getUserStatTask.execute(new Gson().fromJson(prefManager.getUserObject(), User.class));
        String calorieGoal = String.valueOf(prefManager.getCaloriesGoal());
        calGoalTv = view.findViewById(R.id.calories_goal_tv);
        totalCalConsumedTv = view.findViewById(R.id.total_calories_consumed_tv);
        burnedCaloriesTv = view.findViewById(R.id.burned_calories_tv);
        totalNumberOfStepsTv = view.findViewById(R.id.totalStepTakenTv);
        calGoalTv.setText(calorieGoal);
        return view;
    }

    private class GetUserStatTask extends AsyncTask<User, Void, String[]> {

        @Override
        protected String[] doInBackground(User... params) {
            User user = params[0];
            AppDatabase db = Room.databaseBuilder(getActivity(),
                    AppDatabase.class, "stepsDb")
                    .fallbackToDestructiveMigration()
                    .build();

            int total_number_of_steps = db.stepDao().getTotalSteps();
            ConsumptionController consumptionController = new ConsumptionController();
            UserController userController = new UserController();
            String userId = String.valueOf(user.getUserId());
            float user_calories_burn_per_step = userController.getUserCalorieBurnPerStep(userId);
            float user_cbr = userController.getUserCalorieBurnAtRest(userId);
            int total_calorie_consumed = consumptionController.getCurrnetTotalCaloriesConsumed(
                    userId, Utilities.formatDateShort());
            float total_calories_burned = total_number_of_steps * user_calories_burn_per_step + user_cbr;

            return new String[]{String.valueOf(total_number_of_steps), String.valueOf(total_calorie_consumed),
                    String.valueOf(total_calories_burned)} ;
        }

        @Override
        protected void onPostExecute(String[] results) {
            super.onPostExecute(results);
            totalNumberOfStepsTv.setText(results[0]);
            totalCalConsumedTv.setText(results[1]);
            burnedCaloriesTv.setText(results[2]);
        }

    }

}
