package com.example.calorietracker.helper.api;

import com.example.calorietracker.helper.api.Entities.Credential;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;


public class CredentialController extends Controller {
    private static final String BASE_URL = "http://10.0.2.2:8080/CalorieTrackerWS/webresources/ctrackerws.credential/";
    private static final String USERNAME_AND_HASH_URL = "findByUsernameAndPasswordHash/";
    private static final String USERNAME_AND_EMAIL_URL = "findByUsernameAndEmail/";

    public boolean validateByUsernameAndHash(String username, String hash) {
        Gson gson = new Gson();
        Credential credential = null;
        String jsonResponse = getJsonResponse(BASE_URL + USERNAME_AND_HASH_URL + username + "/" + hash);
        List<Credential>  credentialList = Arrays.asList(gson.fromJson(jsonResponse, Credential[].class));
        return !credentialList.isEmpty();
    }

    public Credential getCredentialsOf(String username, String hash){
        Gson gson = new Gson();
        Credential credential = null;
        if(validateByUsernameAndHash(username, hash)){
            String jsonResponse = getJsonResponse(BASE_URL + USERNAME_AND_HASH_URL + username + "/" + hash);
            List<Credential>  credentialList = Arrays.asList(gson.fromJson(jsonResponse, Credential[].class));
            return credentialList.get(0);
        } else {
            return credential;
        }
    }

    public boolean isExistsWith(String username, String email) {
        Gson gson = new Gson();
        Credential credential = null;
        String jsonResponse = getJsonResponse(BASE_URL + USERNAME_AND_EMAIL_URL + username + "/" + email);
        List<Credential>  credentialList = Arrays.asList(gson.fromJson(jsonResponse, Credential[].class));
        return !credentialList.isEmpty();
    }


    public void add(Credential cr){
        add(cr, BASE_URL);
    }
}
