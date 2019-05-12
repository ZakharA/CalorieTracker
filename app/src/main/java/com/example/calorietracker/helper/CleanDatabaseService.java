package com.example.calorietracker.helper;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.calorietracker.helper.database.AppDatabase;

public class CleanDatabaseService extends IntentService {

    private AppDatabase db;

    public CleanDatabaseService() {
        super("CleanDatabaseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "stepsDb")
                .fallbackToDestructiveMigration()
                .build();
        CleanDataBase cleanDataBase = new CleanDataBase();
        cleanDataBase.execute();
    }

    private class CleanDataBase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "stepsDb")
                    .fallbackToDestructiveMigration()
                    .build();
            db.stepDao().deleteAll();
            return null;
        }
    }
}
