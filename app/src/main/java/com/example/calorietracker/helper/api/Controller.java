package com.example.calorietracker.helper.api;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

class Controller {

    protected static void  add(Object obj,  String base_url) {
        try {
            postRequest(obj, base_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void postRequest(Object obj, String base_url) throws IOException {
        URL url = null;
        HttpURLConnection httpConnection = null;
        try {
            Gson gson =new Gson();
            String stringCourseJson = gson.toJson(obj);
            url = new URL(base_url);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            PrintWriter out= new PrintWriter(httpConnection.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(httpConnection.getResponseCode()).toString());
        } catch (Exception e){
            Log.e(TAG, "postRequest: ", e );
        }
        finally {
            httpConnection.disconnect();
        }
    }

    protected static String getJsonResponse(String urlString) {
        URL url;
        HttpURLConnection httpConnection = null;
        StringBuilder textResult = new StringBuilder();
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(httpConnection.getInputStream());
            while (inStream.hasNextLine()) {
                textResult.append(inStream.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert httpConnection != null;
            httpConnection.disconnect();
        }
        return textResult.toString();
    }

    protected static String getPlainResponse(String urlString) {
        URL url;
        HttpURLConnection httpConnection = null;
        StringBuilder textResult = new StringBuilder();
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "text/plain");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                textResult.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert httpConnection != null;
            httpConnection.disconnect();
        }
        return textResult.toString();
    }
}
