package com.example.sortinggame;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageAdapterGallery extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> images;
	private Cursor cursor;
	private ImageLoader imageLoader;
	LayoutInflater mInflater;
	SparseBooleanArray mSparseBooleanArray;


	public ImageAdapterGallery(Context c, ArrayList<String> img, ImageLoader imageLoader) {
		mContext = c;
		images = img;
		this.imageLoader = imageLoader;
		mInflater = LayoutInflater.from(mContext);
		mSparseBooleanArray = new SparseBooleanArray();	        
	}
	
	public ArrayList<String> getCheckedItems() {
		ArrayList<String> mTempArry = new ArrayList<String>();

		for(int i=0;i<images.size();i++) {
			if(mSparseBooleanArray.get(i)) {
				mTempArry.add(images.get(i));
			}
		}

		return mTempArry;
	}

	public int getCount() {
		return images.size();
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
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.multiphoto_item, null);
		}

		CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
		imageView = (ImageView) convertView.findViewById(R.id.imgQueue);
		
		// gets the current value for the requested index
		String imagePath = images.get(position);
		
		// Set the content of the image based on the provided path		
		imageLoader.displayImage("file://" + imagePath, imageView);
		
		mCheckBox.setTag(position);
		mCheckBox.setChecked(mSparseBooleanArray.get(position));
		mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
		
		return convertView;
	}
	
	OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
		}
	};
}
