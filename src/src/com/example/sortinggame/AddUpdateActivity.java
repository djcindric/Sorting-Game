package com.example.sortinggame;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.GridView;

public class AddUpdateActivity extends Activity {

	ImageAdapterLevel imageAdapter;
	public static String LEVEL ="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Forces Screen to Landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_update);

		// Enable icon to function as back button
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create a grid of icons for choosing a level
		GridView gridview = (GridView) findViewById(R.id.gridView1);
		imageAdapter = new ImageAdapterLevel(this);
		gridview.setAdapter(imageAdapter);
		
		//Load Edit Interface when icon is clicked
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	LEVEL = imageAdapter.getLevel(position);
	        	loadEditInterface(v, LEVEL);
	        }
	    });
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
		getMenuInflater().inflate(R.menu.add_update, menu);
		return true;
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu){
    	//Adjust action bar icons based on if music is currently muted
    	if(SoundManager.isMuted[0]){
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button);
    	}
    	else{
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button_off);
    	}
    	
    	return true;
    }

	// Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mute:
        	if(SoundManager.isMuted[0]){
        		item.setIcon(R.drawable.mute_button_off);
        	}
        	else{
        		item.setIcon(R.drawable.mute_button);
        	}
        	SoundManager.playMusic(0);
        	return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void loadCustomizerInterface(View view) {
		Intent intent = new Intent(this, CustomizerActivity.class);
		startActivity(intent);
	}

	public void loadEditInterface(View view, String level) {
		Intent intent = new Intent(this, EditCategoryActivity.class);
		intent.putExtra(LEVEL, level);
		startActivity(intent);
	}

}
