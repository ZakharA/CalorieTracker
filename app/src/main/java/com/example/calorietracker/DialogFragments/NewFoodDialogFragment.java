package com.example.calorietracker.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.calorietracker.R;
import com.example.calorietracker.helper.api.Entities.Food;
import com.example.calorietracker.helper.api.FoodController;
import com.example.calorietracker.helper.api.GoogleSearch;
import com.example.calorietracker.helper.api.NndAPIController;
import com.example.calorietracker.helper.api.Entities.NndItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class NewFoodDialogFragment extends DialogFragment {
    private EditText editText;
    private Context context;
    public NewFoodDialogFragment() {
    }

    public interface NewFoodDialogListener {
        void onNameEnteredDialog(String inputText);
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
        context = getActivity();

        View mainView = inflater.inflate(R.layout.dialog_food, null);
        builder.setView(mainView);

        editText = (EditText) mainView.findViewById(R.id.dialog_foodName);

        Bundle bundle = this.getArguments();

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                sendBackResult();
            }
        });


        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NewFoodDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void sendBackResult() {
        FetchFood fetchFood = new FetchFood();
        fetchFood.execute(editText.getText().toString());
        dismiss();
    }

    protected class FetchFood extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            NndAPIController nndAPIController = new NndAPIController();
            List<NndItem> nndItemsList = nndAPIController.searchNndItemByName(strings[0]);
            Food newFood = nndAPIController.getFoodById(nndItemsList.get(0).getNdbno());
            if (newFood != null) {
                FoodController foodController = new FoodController();
                foodController.add(newFood);
                getImage(newFood.getName());
                return "Food successfully added";
            }
            return "No such Food";
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            NewFoodDialogFragment.NewFoodDialogListener listener = (NewFoodDialogFragment.NewFoodDialogListener) getTargetFragment();
            listener.onNameEnteredDialog(message);
        }
    }

    public void getImage(String name) {
        FetchImageTask imgTask = new FetchImageTask();
        imgTask.execute(name);
    }

    private class FetchImageTask extends AsyncTask<String, Void, HashMap<String, Bitmap>> {

        @Override
        protected HashMap<String, Bitmap> doInBackground(String... strings) {
            GoogleSearch googleSearch = new GoogleSearch();
            HashMap<String, Bitmap> result = new HashMap<>();
            result.put(strings[0], googleSearch.getImageByName(strings[0]));
            return result;
        }

        @Override
        protected void onPostExecute(HashMap<String, Bitmap> result) {
            super.onPostExecute(result);
            String imageName = result.keySet().toArray()[0].toString();
            Bitmap imageBitmap = result.get(imageName);
            saveToInternalStorage(imageBitmap, imageName);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name) {
        File directory = context.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
