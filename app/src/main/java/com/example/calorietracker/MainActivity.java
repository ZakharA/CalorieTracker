package com.example.calorietracker;

import android.app.AlarmManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.calorietracker.helper.DailyReportService;
import com.example.calorietracker.helper.SharedPrefManager;
import com.example.calorietracker.helper.database.AppDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AppDatabase db;
    private AlarmManager alarmMgr;
    private Intent dailyReportIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        setDailyReportService();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calorie Tracker");

        FragmentManager fragmentManager = getSupportFragmentManager();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getString("Pref_name", null) != null){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new
                    HomeFragment()).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new
                    LoginFragment()).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_settings) {
             db =  Room.databaseBuilder(this,
                    AppDatabase.class, "stepsDb")
                    .fallbackToDestructiveMigration()
                    .build();

             CleanDataBase cleanDataBase = new CleanDataBase();
             cleanDataBase.execute();

             return true;
        } else if(id == R.id.logout_settings){
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear().commit();
            FragmentManager fragmentManager =  getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new LoginFragment()).commit();
        } else if(id == R.id.launch_report_service) {
            lunchReportService();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = new LoginFragment();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getString("Pref_name", null) != null){
            if (id == R.id.nav_home) {
                nextFragment = new HomeFragment();
            } else if (id == R.id.nav_diet) {
                nextFragment = new DietFragment();
            } else if (id == R.id.nav_steps) {
                nextFragment = new StepsFragment();
            } else if (id == R.id.nav_calorie) {
                nextFragment = new CalorieTrackerFragment();
            } else if (id == R.id.nav_report) {
                nextFragment = new ReportFragment();
            } else if (id == R.id.nav_map) {
                nextFragment = new MapsFragment();
            }
        }

        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class CleanDataBase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepDao().deleteAll();
            return null;
        }
    }

    public void setDailyReportService(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        SharedPrefManager prefManager = new SharedPrefManager(getPreferences(Context.MODE_PRIVATE));
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        dailyReportIntent = new Intent(this, DailyReportService.class);
        dailyReportIntent.putExtra(String.valueOf(R.string.preference_calorie_goal), prefManager.getCaloriesGoal());
        dailyReportIntent.putExtra("userId", prefManager.getUserId());
        dailyReportIntent.putExtra("user", prefManager.getUserObject());
        pendingIntent = PendingIntent.getService(this, 0, dailyReportIntent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void lunchReportService(){
        Intent i = new Intent(this, DailyReportService.class);
        SharedPrefManager prefManager = new SharedPrefManager(getPreferences(Context.MODE_PRIVATE));
        i.putExtra(String.valueOf(R.string.preference_calorie_goal), prefManager.getCaloriesGoal());
        i.putExtra("userId", prefManager.getUserId());
        i.putExtra("user", prefManager.getUserObject());
        startService(i);
    }
}
