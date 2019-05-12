package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.calorietracker.helper.SharedPrefManager;
import com.example.calorietracker.helper.Utilities;
import com.example.calorietracker.helper.api.CredentialController;
import com.example.calorietracker.helper.api.Entities.Credential;
import com.example.calorietracker.helper.api.Entities.User;
import com.example.calorietracker.helper.api.UserController;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static com.example.calorietracker.helper.Utilities.*;


public class SignupFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private EditText signup_dobEt;
    private Button signupBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        signup_dobEt.setText(Utilities.formatDate(year - 1900, month, day));
                    }
                }, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDay());

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signupBtn = getView().findViewById(R.id.signup_btn);
        signup_dobEt = getView().findViewById(R.id.signup_dob);
        signup_dobEt.setInputType(InputType.TYPE_NULL);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupTask signupTask = new SignupTask();
                signupTask.execute(assambleUserFromView());
            }
        });
        signup_dobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }

    private User assambleUserFromView() {
        EditText address = getView().findViewById(R.id.signup_address);
        EditText dob = getView().findViewById(R.id.signup_dob);
        EditText email = getView().findViewById(R.id.signup_email);
        RadioGroup genderGr = getView().findViewById(R.id.signup_gender);
        RadioButton gender = getView().findViewById(genderGr.getCheckedRadioButtonId());
        EditText firstname = getView().findViewById(R.id.signup_firstname);
        EditText surname = getView().findViewById(R.id.signup_surname);
        EditText weight = getView().findViewById(R.id.signup_weight);
        EditText height = getView().findViewById(R.id.signup_height);
        EditText step_per_mile = getView().findViewById(R.id.signup_steps_per_mile);
        EditText postcode = getView().findViewById(R.id.signup_postcode);
        Spinner loa = getView().findViewById(R.id.signup_level_of_activity);
        User user = new User(getStrFrom(firstname), getStrFrom(surname), getStrFrom(email),
                getStrFrom(dob)+"T00:00:00+10:00", getDoubleFrom(height), getDoubleFrom(weight),
                gender.getText().toString(), getStrFrom(address), getStrFrom(postcode),
                getIntFrom(loa), getIntFrom(step_per_mile));
        return user;
    }

    private Credential assambleCredentialFromView(User user){
        EditText passwordEdTv = getView().findViewById(R.id.signup_password);
        EditText usernameEdTv = getView().findViewById(R.id.signup_username);
        String hashPassword = Utilities.getMD5(getStrFrom(passwordEdTv));
        String username = getStrFrom(usernameEdTv);
        return new Credential(user, username, hashPassword, Utilities.getDate());
    }



    private class SignupTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {
            UserController userController = new UserController();
            userController.add(users[0]);
            User createdUser = userController.getUserByEmail(users[0].getEmail());
            CredentialController credentialController = new CredentialController();
            credentialController.add(assambleCredentialFromView(createdUser));
            return createdUser;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPrefManager sharedPrefManager = new SharedPrefManager(sharedPref);
            sharedPrefManager.addUserInfo(user);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new
                    HomeFragment()).commit();
        }
    }

}
