package com.example.calorietracker.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calorietracker.R;
import com.example.calorietracker.helper.api.Entities.Food;
import com.example.calorietracker.helper.api.GoogleSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<Food> {
    private Context mContext;
    private List<Food> foodsList = new ArrayList<>();

    public FoodAdapter( Context context, ArrayList<Food> list) {
        super(context, 0 , list);
        mContext = context;
        foodsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.food_item_fragment, parent, false);

        Food currentFood = foodsList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_photo);
        //image.setImageResource(currentFood.getmImageDrawable());
        image.setImageBitmap(loadImageFromStorageByName(currentFood.getName()));
        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentFood.getName());

        TextView release = (TextView) listItem.findViewById(R.id.textView_info);
        release.setText(currentFood.getInfo());

        return listItem;
    }

    private Bitmap loadImageFromStorageByName(String name)
    {
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f=new File( directory, name +".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;

    }

}
