package com.example.calorietracker;

import android.app.AlarmManager;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calorietracker.DialogFragments.EditCaloriesDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment implements EditCaloriesDialogFragment.EditCaloriesDialogListener {
    private TextView usernameTv;
    private TextView userCalorieGoalTv;
    private TextView currentDatetimeTv;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        usernameTv = (TextView) getView().findViewById(R.id.home_username);
        userCalorieGoalTv = (TextView) getView().findViewById(R.id.home_calorie_goal);
        currentDatetimeTv = (TextView) getView().findViewById(R.id.home_currentDateTime);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        setUsernameFromSharedPreferences();
        setCaloriesGoalIfExists();
        setCurrentDatetime();

        userCalorieGoalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                EditCaloriesDialogFragment editCaloriesDialogFragment = new EditCaloriesDialogFragment();
                editCaloriesDialogFragment.setTargetFragment(HomeFragment.this, 300);
                editCaloriesDialogFragment.show(fm, "edit_calorie_framgent");
            }
        });
    }

    private void setCurrentDatetime() {
        currentDatetimeTv.setText(new SimpleDateFormat("yyyy-mm-dd h:mm:ss a").format(new Date()));
    }

    private void setCaloriesGoalIfExists() {
        if(sharedPreferences.contains("calorie_goal")){
            userCalorieGoalTv.setText("Your calories goal for today: " +
                    sharedPreferences.getInt("calorie_goal", 0));
        }
    }

    private void setUsernameFromSharedPreferences() {
        if(sharedPreferences.contains("Pref_name")){
            usernameTv.setText(sharedPreferences.getString("Pref_name", ""));
        }
    }

    @Override
    public void onFinishEditDialog(int inputText) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("calorie_goal", inputText);
        editor.commit();
        userCalorieGoalTv.setText("Your calories goal for today: " + inputText);
    }


}
