package com.example.sortinggame;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterGallery extends BaseAdapter {
    private Context mContext;
    private Cursor cursor;
    private int columnIndex;
    
    public ImageAdapterGallery(Context c, Cursor images, int column) {
        mContext = c;
        cursor= images;
        columnIndex = column;
    }

    public int getCount() {
        return cursor.getCount();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            cursor.moveToPosition(position);
            
            //gets the current value for the requested column
            int imageID = cursor.getInt(columnIndex);
            
            //Set the content of the image based on the provided URI
            imageView.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imageID));
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
