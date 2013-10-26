package com.example.sortinggame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterLevel extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> images;
    SortingDB db;

    public ImageAdapterLevel(Context c) {
        mContext = c;
        db = new SortingDB(c);
        images = new ArrayList<Integer>();
        loadImages();
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(images.get(position));
        return imageView;
    }
    
    //Gets path from database for preloaded images and finds reference id in R.java class
    private void loadImages() {
    	Cursor test = db.query("SELECT iconPath FROM  Level", null);
    	Class res = R.drawable.class;
		Field field;
		while(test.moveToNext()) {
			try {
				field = res.getField(test.getString(test.getColumnIndex("iconPath")));
				int identifier = field.getInt(null);
				images.add(identifier);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag", "Failure to get drawable id. Path = " + test.getString(test.getColumnIndex("iconPath")), e);
			}
		}
    }

    //Store the images to be displayed on the grid
    private Integer[] mThumbIds = {
    		//Sample images
            R.drawable.sample_1, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
    };
}
