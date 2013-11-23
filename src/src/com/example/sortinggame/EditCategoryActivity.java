package com.example.sortinggame;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class EditCategoryActivity extends Activity{
	private CustomizerControl control;
	private String level, ulevel;
	private ArrayList<String> catergory1Images;
	private ArrayList<String> catergory2Images;
	private ArrayList<String> catergory3Images;
	
	protected void onCreate(Bundle savedInstanceState) {

		// Forces Screen into landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);
		
		Intent i = getIntent();
		level = i.getExtras().getString(CustomizerActivity.LEVEL_NAME);
		if(i.hasExtra(CustomizerActivity.LEVEL_NAME))
			level = i.getExtras().getString(CustomizerActivity.LEVEL_NAME);
		if(i.hasExtra(CustomizerActivity.LEVEL))
			level = j.getExtras().getString(AddUpdateActivity.LEVEL);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

			actionBar.setTitle("Add Images to " + level);
		
		control = new CustomizerControl(this);
		
		catergory1Images = new ArrayList<String>();
		catergory2Images = new ArrayList<String>();
		catergory3Images = new ArrayList<String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customizer, menu);
		return true;
	}

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.hide_bar:
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void loadEditLevelInterface(View view) {
		Intent intent = new Intent(this, CustomizerActivity.class);
		startActivity(intent);
	}
	
	public void chooseCat1Images(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		startActivityForResult(intent, 100);
	}
	
	public void chooseCat2Images(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		startActivityForResult(intent, 200);
	}
	
	public void chooseCat3Images(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		startActivityForResult(intent, 300);
	}
	public void saveLevel(View view) {
		control.saveLevel(level, "bagel", "bookcase_background");
		control.saveCategory(level, 1, null);
		control.saveCategory(level, 2, null);
		control.saveCategory(level, 3, null);
		
		ArrayList<Integer> ids = control.getCategoryIDs(level);
		for(String path : catergory1Images)
			control.saveImage(path, ids.get(0));
		
		for(String path : catergory2Images)
			control.saveImage(path, ids.get(1));
		
		for(String path : catergory3Images)
			control.saveImage(path, ids.get(2));
		
		Toast.makeText(this, "Adding Level...", Toast.LENGTH_LONG).show();
	}
	public void updateLevel(View view) {
		//set icon to book and bg to shapes_background to show successful update
		control.updateLevel(ulevel, "book", "shapes_background");
		control.updateCategory(ulevel, 1, null);
		control.updateCategory(ulevel, 2, null);
		control.updateCategory(ulevel, 3, null);
		
		ArrayList<Integer> ids = control.getCategoryIDs(ulevel);
		for(String path : catergory1Images)
			control.updateImage(path, ids.get(0));
		
		for(String path : catergory2Images)
			control.updateImage(path, ids.get(1));
		
		for(String path : catergory3Images)
			control.updateImage(path, ids.get(2));
		
		Toast.makeText(this, "Updating Level...", Toast.LENGTH_LONG).show();
	}
	 @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);

             if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            	 String[] all_path = data.getStringArrayExtra("image_paths");

                 for (String string : all_path) {
                	 catergory1Images.add(string);
                 }
                 System.out.println(catergory1Images.toString());
             }
             else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            	 String[] all_path = data.getStringArrayExtra("image_paths");

                 for (String string : all_path) {
                	 catergory2Images.add(string);
                 }
             }
             else  if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            	 String[] all_path = data.getStringArrayExtra("image_paths");

                 for (String string : all_path) {
                	 catergory3Images.add(string);
                 }
             }
     }
}
