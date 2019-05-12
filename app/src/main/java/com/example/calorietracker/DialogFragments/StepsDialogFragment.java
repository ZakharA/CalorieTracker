package com.example.calorietracker.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.calorietracker.R;
import com.example.calorietracker.helper.database.AppDatabase;
import com.example.calorietracker.helper.database.Step;
import com.google.gson.Gson;

import java.util.Date;

public class StepsDialogFragment extends DialogFragment {
    AppDatabase db = null;
    EditText edText;

    OnStepDataEntered callback;

    public interface OnStepDataEntered {
        public void onStepDataEntered();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        callback = (OnStepDataEntered) getTargetFragment();

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "stepsDb")
                .fallbackToDestructiveMigration()
                .build();

        View mainView = inflater.inflate(R.layout.dialog_steps, null);
        builder.setView(mainView);

        edText = mainView.findViewById(R.id.dialog_nSteps);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Gson gson = new Gson();
            String serializedStep = bundle.getString("step");
            final Step step = gson.fromJson(serializedStep, Step.class);
            edText.setText(String.valueOf(step.getSteps()));
            builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    step.setSteps(Integer.parseInt(edText.getText().toString()));
                    UpdateDatabase updateDatabase = new UpdateDatabase();
                    updateDatabase.execute(step);
                }
            });
        } else {
            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    InsertDatabase insertDatabase = new InsertDatabase();
                    insertDatabase.execute();
                }
            });
        }

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        StepsDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private class InsertDatabase extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!(edText.getText().toString().isEmpty())) {

                String steps = edText.getText().toString();
                Step step = new Step(Integer.parseInt(steps), new Date());
                long id = db.stepDao().insert(step);
                return edText.getText().toString();
            } else
                return "";
        }

        protected void onPostExecute(Object o) {
           callback.onStepDataEntered();
        }
    }

    private class UpdateDatabase extends AsyncTask<Step, Void, Void> {
        @Override
        protected Void doInBackground(Step... steps) {
            db.stepDao().update(steps);
            return  null;
        }

        protected void onPostExecute(Void result) {
            callback.onStepDataEntered();
        }
    }

}
