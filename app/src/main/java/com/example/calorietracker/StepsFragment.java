package com.example.calorietracker;


import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.calorietracker.DialogFragments.StepsDialogFragment;
import com.example.calorietracker.helper.database.AppDatabase;
import com.example.calorietracker.helper.database.Step;
import com.google.gson.Gson;

import java.util.List;


public class StepsFragment extends Fragment implements StepsDialogFragment.OnStepDataEntered {
    private AppDatabase db;
    private ListView stepsLv;
    private ArrayAdapter<Step> stepArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "stepsDb")
                .fallbackToDestructiveMigration()
                .build();

        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = getView().findViewById(R.id.addStepsBtn);
        stepsLv = getView().findViewById(R.id.stepsListView);

        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(null);
            }
        });

        stepsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Step stepClicked = (Step) parent.getItemAtPosition(position);
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                bundle.putString("step", gson.toJson(stepClicked));
                showDialog(bundle);
            }
        });
    }

    private void showDialog(Bundle bundle) {
        DialogFragment newFragment = new StepsDialogFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "Steps");
    }

    @Override
    public void onStepDataEntered() {
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
    }

    private class ReadDatabase extends AsyncTask<Void, Void, List<Step>> {
        @Override
        protected List<Step> doInBackground(Void... params) {
            List<Step> stepList = db.stepDao().getAll();
            return  stepList;
        }

        protected void onPostExecute(List<Step> stepList) {
            stepArrayAdapter = new ArrayAdapter<Step>(getActivity(), R.layout.step_list_item, stepList);
            stepsLv.setAdapter(stepArrayAdapter);
        }
    }
}
