package com.example.calorietracker.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.calorietracker.R;

public class EditCaloriesDialogFragment extends DialogFragment {
    private EditText editText;

    public EditCaloriesDialogFragment() {
    }

    public interface EditCaloriesDialogListener {
        void onFinishEditDialog(int inputText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_calories, container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View mainView = inflater.inflate(R.layout.dialog_calories, null);
        builder.setView(mainView);

        editText = (EditText) mainView.findViewById(R.id.dialog_nCalories);

        Bundle bundle = this.getArguments();

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                sendBackResult();
            }
        });


        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditCaloriesDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void sendBackResult() {
        EditCaloriesDialogListener listener = (EditCaloriesDialogListener) getTargetFragment();
        listener.onFinishEditDialog(Integer.parseInt(editText.getText().toString()));
        dismiss();
    }

}

