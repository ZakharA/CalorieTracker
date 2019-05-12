package com.example.calorietracker.helper.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepDao {
    @Query("SELECT id, daily_steps, timestamp FROM Step")
    List<Step> getAll();

    @Query("SELECT sum(daily_steps) FROM Step")
    int getTotalSteps();

    @Insert
    long insert(Step step);

    @Delete
    void delete(Step step);

    @Query("DELETE FROM Step")
    void deleteAll();

    @Update(onConflict = REPLACE)
    void update(Step... steps);
}
