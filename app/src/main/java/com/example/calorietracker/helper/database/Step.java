package com.example.calorietracker.helper.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
public class Step {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int uid;

    @ColumnInfo(name = "daily_steps")
    int steps;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "timestamp")
    Date timestamp;

    public Step() {}

    public Step(int steps, Date date){
        this.steps = steps;
        timestamp = date;
    }
    public Step(int uid, int steps, Date date){
        this.uid = uid;
        this.steps = steps;
        timestamp = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return steps + ", " + timestamp.toString();
    }
}
