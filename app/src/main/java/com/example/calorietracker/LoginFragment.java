package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorietracker.helper.SharedPrefManager;
import com.example.calorietracker.helper.Utilities;
import com.example.calorietracker.helper.api.Entities.Credential;
import com.example.calorietracker.helper.api.CredentialController;
import com.example.calorietracker.helper.api.Entities.User;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final TextView signupLinkTv = (TextView)  getView().findViewById(R.id.signupLink);
        final Button loginBtn = (Button) getView().findViewById(R.id.LoginBtn);
        final TextView usernameEt = (TextView)  getView().findViewById(R.id.login_usernameEditText);
        final TextView passwordEt = (TextView)  getView().findViewById(R.id.login_passwordEditText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username =  usernameEt.getText().toString();
                String md5password = Utilities.getMD5(passwordEt.getText().toString());
                Credential cr = new Credential(username, md5password);

                LoginTask loginTask = new LoginTask();
                loginTask.execute(cr);
            }
        });

        signupLinkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = new SignupFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, nextFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private class LoginTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            CredentialController credentialController = new CredentialController();
            Credential cr = (Credential) objects[0];
            return credentialController.getCredentialsOf(cr.getUsername(), cr.getPasswordHash());
        }


        protected void onPostExecute(Object result) {
            if(result == null) {
                Toast.makeText(getView().getContext(), "Login or password is incorrect", Toast.LENGTH_LONG).show();
            } else {
                Credential credential = (Credential) result;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                User user = credential.getUserId();
                SharedPrefManager sharedPrefManager = new SharedPrefManager(sharedPref);
                sharedPrefManager.addUserInfo(user);

                Fragment nextFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, nextFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

}
