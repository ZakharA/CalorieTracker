package com.example.calorietracker;


import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.calorietracker.DialogFragments.NewFoodDialogFragment;
import com.example.calorietracker.helper.FoodAdapter;
import com.example.calorietracker.helper.api.ConsumptionController;
import com.example.calorietracker.helper.api.Entities.Consumption;
import com.example.calorietracker.helper.api.Entities.Food;
import com.example.calorietracker.helper.api.Entities.User;
import com.example.calorietracker.helper.api.FoodController;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class DietFragment extends Fragment implements OnItemSelectedListener, OnItemClickListener,
        View.OnClickListener, NewFoodDialogFragment.NewFoodDialogListener {

    ArrayList<Food> foodList = new ArrayList<Food>();
    Spinner foodCategorySp;
    FoodAdapter foodArrayAdapter;
    private String selectedCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diet, container, false);
        foodCategorySp = v.findViewById(R.id.diet_category_sp);
        ListView foodLv = v.findViewById(R.id.diet_food_listview);
        FloatingActionButton fabAddNewFood = v.findViewById(R.id.addNewFood);

        foodCategorySp.setOnItemSelectedListener(this);
        foodLv.setOnItemClickListener(this);
        fabAddNewFood.setOnClickListener(this);

        foodArrayAdapter = new FoodAdapter(getActivity(), foodList);
        foodLv.setAdapter(foodArrayAdapter);

        return v;
    }

    private void fetchFood(String category) {
        FetchFoodTask fetchFoodTask = new FetchFoodTask();
        fetchFoodTask.execute(category);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = parent.getItemAtPosition(position).toString();
        fetchFood(selectedCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        DialogFragment newFragment = new NewFoodDialogFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "NewFood");
    }

    @Override
    public void onNameEnteredDialog(String message) {
        fetchFood(selectedCategory);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Number of servings");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                User user = new Gson().fromJson(sharedPref.getString("user", ""), User.class);
                short numberOfServings = Short.parseShort(input.getText().toString());
                Food selectedFood = (Food) parent.getItemAtPosition(position);
                SendConsumptionRecordTask sendTask = new SendConsumptionRecordTask();
                sendTask.execute(ConsumptionController.createConsumptionRecord(selectedFood, numberOfServings, user));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    protected class FetchFoodTask extends AsyncTask<String, Void, List<Food>> {

        @Override
        protected List<Food> doInBackground(String... strings) {
            FoodController foodController = new FoodController();
            return foodController.getFoodWithCategory(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);
            foodList = new ArrayList<Food>(foods);
            foodArrayAdapter.clear();
            foodArrayAdapter.addAll(foods);
        }
    }

    protected class SendConsumptionRecordTask extends AsyncTask<Consumption, Void, Void> {

        @Override
        protected Void doInBackground(Consumption... strings) {
            ConsumptionController consumptionController = new ConsumptionController();
            consumptionController.logConsumedFood(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "Food has been logged", Toast.LENGTH_SHORT).show();
        }
    }

}
