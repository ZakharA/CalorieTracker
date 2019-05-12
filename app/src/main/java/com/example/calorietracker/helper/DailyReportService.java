package com.example.calorietracker.helper;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.calorietracker.R;
import com.example.calorietracker.helper.api.ConsumptionController;
import com.example.calorietracker.helper.api.Entities.Report;
import com.example.calorietracker.helper.api.Entities.User;
import com.example.calorietracker.helper.api.ReportController;
import com.example.calorietracker.helper.api.UserController;
import com.example.calorietracker.helper.database.AppDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class DailyReportService extends IntentService {

    public DailyReportService() {
        super("DailyReportService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ConsumptionController consumptionController = new ConsumptionController();
        UserController userController = new UserController();
        ReportController reportController = new ReportController();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "stepsDb")
                .fallbackToDestructiveMigration()
                .build();

        String userId = intent.getExtras().getString("userId");
        String userJson = intent.getExtras().getString("user");
        User user = new Gson().fromJson(userJson, User.class);
        float user_calories_burn_per_step = userController.getUserCalorieBurnPerStep(userId);
        float user_cbr = userController.getUserCalorieBurnAtRest(userId);

        int daily_calorie_goal = intent.getExtras().getInt(String.valueOf(R.string.preference_calorie_goal), 0);
        int total_number_of_steps = db.stepDao().getTotalSteps();
        int total_calorie_consumed = consumptionController.getCurrnetTotalCaloriesConsumed(userId, Utilities.formatDateShort());
        float total_calories_burned = total_number_of_steps * user_calories_burn_per_step + user_cbr;

        reportController.add(new Report(daily_calorie_goal, Utilities.getDate(),
                total_calories_burned, total_calorie_consumed, total_number_of_steps, user));

        CleanDBTask cleanDBTask = new CleanDBTask();
        cleanDBTask.execute();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Report service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    private class CleanDBTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "stepsDb")
                    .fallbackToDestructiveMigration()
                    .build();
            db.stepDao().deleteAll();
            return null;
        }
    }
}
