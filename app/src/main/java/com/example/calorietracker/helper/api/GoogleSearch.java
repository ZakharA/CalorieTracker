package com.example.calorietracker.helper.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.example.calorietracker.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleSearch extends Controller {
    final static private String KEY = BuildConfig.googleCustomSearchAPI;
    final static private String SEARCH_KEY = BuildConfig.searchEngineID;
    final static private String URL = "https://www.googleapis.com/customsearch/v1?";

    public Bitmap getImageByName(String name){
        String[] params = {"key=" + KEY,
                "cx=" + SEARCH_KEY,
                "searchType=" + "image",
                "q=" + name};

        String respone = getJsonResponse(URL + TextUtils.join("&", params));
        if(getTotalResults(respone) > 0){
            String imageLink = getFirstImageLink(respone);
            Bitmap imageBitmap = downloadImage(imageLink);
            return imageBitmap;
        }
            return null;
    }

    private int getTotalResults(String json) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject response = (JsonObject)parser.parse(json);
        JsonElement searchInformationElement = response.get("searchInformation");
        return searchInformationElement.getAsJsonObject().get("totalResults").getAsInt();
    }

    private String getFirstImageLink(String json) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject response = (JsonObject)parser.parse(json);
        JsonArray jsonItemArray = response.get("items").getAsJsonArray();
        JsonObject firstItem = jsonItemArray.get(0).getAsJsonObject();
        return firstItem.get("link").getAsString();

    }

    private Bitmap downloadImage(String url) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        }catch(Exception e){}
        return bmp;
    }
}
