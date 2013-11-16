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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditCategoryActivity extends Activity{
	private CustomizerControl control;
	private String level;
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

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Add Images to " + level);
		
		control = new CustomizerControl(this);
		
		catergory1Images = new ArrayList<String>();
		catergory2Images = new ArrayList<String>();
		catergory3Images = new ArrayList<String>();
		setOnClicks();
	}
	protected void onStart(){
    	super.onStart();
    	
    	//Start menu music
    	if(SoundManager.isMuted[0] == false){
    		SoundManager.playMusic(0);
    	}
    	
    }

    protected void onPause(){
    	super.onPause();
    	//Pause music when app is out of focus
    	if(SoundManager.players[0].isPlaying()){
    		SoundManager.playMusic(0);
    		SoundManager.isMuted[0] = false;
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editcategory, menu);
		return true;
	}

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mute:
        	SoundManager.playMusic(0);
        	return true;
		case R.id.save:
			saveLevel();
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
	public void saveLevel() {
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
	 
	 private void setOnClicks(){
		 //Add onClickListeners to the views
		 ImageView icon1 = (ImageView) findViewById(R.id.categoryImage1);
		 	icon1.setOnClickListener(myHandler1);
		 ImageView icon2 = (ImageView) findViewById(R.id.categoryImage2);
		 	icon2.setOnClickListener(myHandler2);
		 ImageView icon3 = (ImageView) findViewById(R.id.categoryImage3);
		 	icon3.setOnClickListener(myHandler3);
		 LinearLayout images1 = (LinearLayout) findViewById(R.id.images1);
		 	images1.setOnClickListener(myHandler4);
		 LinearLayout images2 = (LinearLayout) findViewById(R.id.images2);
		 	images2.setOnClickListener(myHandler5);
		 LinearLayout images3 = (LinearLayout) findViewById(R.id.images3);
		 	images3.setOnClickListener(myHandler6);
	 }
	 
	 //Change the level background
	 public void changeBackground(View view){
		Toast toast = Toast.makeText(getApplicationContext(), "Change Background", Toast.LENGTH_SHORT);
		toast.show();
	 }
	 
	 //Change the level icon
	 public void changeIcon(View view){
		 Toast toast = Toast.makeText(getApplicationContext(), "Change Icon", Toast.LENGTH_SHORT);
		 toast.show();
	 }
	 
	 //Handle clicks on icon for category 1
	 View.OnClickListener myHandler1 = new View.OnClickListener(){
		public void onClick(View v) {
			Toast toast = Toast.makeText(getApplicationContext(), "Icon 1", Toast.LENGTH_SHORT);
			toast.show();
		}
	 };
	//Handle clicks on icon for category 2
	 View.OnClickListener myHandler2 = new View.OnClickListener(){
		public void onClick(View v) {
			Toast toast = Toast.makeText(getApplicationContext(), "Icon 2", Toast.LENGTH_SHORT);
			toast.show();
		}
	 };
	//Handle clicks on icon for category 3
	 View.OnClickListener myHandler3 = new View.OnClickListener(){
		public void onClick(View v) {
			Toast toast = Toast.makeText(getApplicationContext(), "Icon 3", Toast.LENGTH_SHORT);
			toast.show();
		}
	 };
	//Handle clicks on images for category 1
	 View.OnClickListener myHandler4 = new View.OnClickListener(){
		public void onClick(View v) {
			chooseCat1Images(v);
		}
	 };
	//Handle clicks on images for category 2
	 View.OnClickListener myHandler5 = new View.OnClickListener(){
		public void onClick(View v) {
			chooseCat2Images(v);
		}
	 };
	//Handle clicks on images for category 3
	 View.OnClickListener myHandler6 = new View.OnClickListener(){
		public void onClick(View v) {
			chooseCat3Images(v);
		}
	 };
	 
}
