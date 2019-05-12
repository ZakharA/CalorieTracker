package com.example.calorietracker.helper;

import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class Utilities {

    public static String getMD5(String message){
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(message.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) hexString.append(Integer.toHexString(0xFF & b));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentFormatedDateAsString(){
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
    }

    public static String getCurrentFormatedDateAsStringWithoutTimezone(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String formatDate(int year, int month, int day){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(year, month, day));
    }

    public static String formatDateShort(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getStrFrom(EditText tv){
        return tv.getText().toString();
    }

    public static int getIntFrom(Spinner sp){
        return Integer.parseInt(sp.getSelectedItem().toString());
    }

    public static String getStrFrom(Spinner sp){
        return sp.getSelectedItem().toString();
    }

    public static String getStrFrom(RadioButton rb){
        return rb.getText().toString();
    }

    public static Double getDoubleFrom(EditText spinner){
        return Double.parseDouble(getStrFrom(spinner));
    }

    public static int getIntFrom(EditText tv){
        return Integer.parseInt(getStrFrom(tv));
    }

    public static String getDate() {
        String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        return format;
    }
}
