package com.example.sortinggame;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterGame extends BaseAdapter implements OnTouchListener {
    private Context mContext;
    private ArrayList<Integer> images;
    SortingDB db;

    public ImageAdapterGame(Context c) {
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
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(images.get(position));
        imageView.setOnTouchListener(this);
        return imageView;
    }

    private void loadImages() {
    	Cursor test = db.query("SELECT path FROM  Images", null);
    	Class res = R.drawable.class;
		Field field;
		while(test.moveToNext()) {
			try {
				field = res.getField(test.getString(test.getColumnIndex("path")));
				int identifier = field.getInt(null);
				images.add(identifier);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("MyTag", "Failure to get drawable id. Path = " + test.getString(test.getColumnIndex("path")), e);
			}
		}
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
			v.startDrag(null, shadowBuilder, v, 0);
			v.setVisibility(View.INVISIBLE);
			return true;
		} else
			return false;
	}
}
