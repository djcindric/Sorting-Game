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
	public final static String LEVEL_NAME = "com.example.sortinggame.MESSAGE";
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

		if(i.hasExtra(CustomizerActivity.LEVEL_NAME))
			level = i.getExtras().getString(CustomizerActivity.LEVEL_NAME);
		if(i.hasExtra(AddUpdateActivity.LEVEL))
			ulevel = i.getExtras().getString(AddUpdateActivity.LEVEL);


		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		if(level != null)
			actionBar.setTitle("Add Images to " + level);
		else
			actionBar.setTitle("Add Images to " + ulevel);
		
		control = new CustomizerControl(this);
		
		catergory1Images = new ArrayList<String>();
		catergory2Images = new ArrayList<String>();
		catergory3Images = new ArrayList<String>();
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
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu){
    	//Adjust action bar icons based on if music is currently muted
    	if(SoundManager.isMuted[0]){
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button_off);
    	}
    	else{
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button);
    	}
    	
    	return true;
    }

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mute:
        	if(SoundManager.isMuted[0]){
        		item.setIcon(R.drawable.mute_button);
        	}
        	else{
        		item.setIcon(R.drawable.mute_button_off);
        	}
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
		if(level != null)
			intent.putExtra(LEVEL_NAME,level);
		else
			intent.putExtra(LEVEL_NAME,ulevel);
		startActivityForResult(intent, 100);
	}
	
	public void chooseCat2Images(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		if(level != null)
			intent.putExtra(LEVEL_NAME,level);
		else
			intent.putExtra(LEVEL_NAME,ulevel);
		startActivityForResult(intent, 200);
	}
	
	public void chooseCat3Images(View view){
		Intent intent = new Intent(this, GalleryActivity.class);
		if(level != null)
			intent.putExtra(LEVEL_NAME,level);
		else
			intent.putExtra(LEVEL_NAME,ulevel);
		startActivityForResult(intent, 300);
	}
	public void saveLevel() {
		new Thread(new Runnable() {
	        public void run() {

				ArrayList<Integer> ids = new ArrayList<Integer>();
				if (level != null) {
					control.saveLevel(level, "pbm_launch_icon",
							"pbm_background");
					control.saveCategory(level, 1, null);
					control.saveCategory(level, 2, null);
					control.saveCategory(level, 3, null);
					ids = control.getCategoryIDs(level);
				} else
					ids = control.getCategoryIDs(ulevel);

				for (String path : catergory1Images)
					control.saveImage(path, ids.get(0));

				for (String path : catergory2Images)
					control.saveImage(path, ids.get(1));

				for (String path : catergory3Images)
					control.saveImage(path, ids.get(2));

				control.close();
			}
		}).start();

		Toast.makeText(this, "Saving Level...", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(this, CustomizerActivity.class);
		startActivity(intent);
		finish();
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
