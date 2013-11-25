package com.example.sortinggame;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.content.Context;
import android.database.Cursor;

public class ImageAdapterDelete extends BaseAdapter{

	private Context mContext;
	private ArrayList<String> images;
    private ArrayList<String> levels;
    SortingDB db;

    public ImageAdapterDelete(Context c) {
        mContext = c;
        db = new SortingDB(c);
        images = new ArrayList<String>();
        levels = new ArrayList<String>();
        loadImages();
    }

    public int getCount() {
        return levels.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    public String getLevel(int position) {
        return levels.get(position);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        
        Class res = R.drawable.class;
		Field field;
		try {
			field = res.getField(images.get(position));
			int id = field.getInt(null);
	        imageView.setImageResource(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MyTag", "Failure to get drawable id. Path = " + images.get(position), e);
		}
        return imageView;
    }
    
  //Gets path from database for preloaded images and finds reference id in R.java class
    private void loadImages() {
    	Cursor test = db.query("SELECT name, iconPath FROM  Level", null);
    	//Toast.makeText(mContext, test.getColumnNames().toString(), Toast.LENGTH_SHORT).show();

		while(test.moveToNext()) {
			levels.add(test.getString(test.getColumnIndex("name")));
			images.add(test.getString(test.getColumnIndex("iconPath")));
		}
    }
}
