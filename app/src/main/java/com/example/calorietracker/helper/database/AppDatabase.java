package com.example.calorietracker.helper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Step.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StepDao stepDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "customer_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
