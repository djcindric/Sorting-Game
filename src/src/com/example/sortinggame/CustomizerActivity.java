package com.example.sortinggame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CustomizerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Prevent Screen From rotating
		
		//Remove Icon from Action Bar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customizer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customizer, menu);
		return true;
	}
	
	//Handle clicks on the action bar
		public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.hide_bar:
	    		ActionBar actionBar = getActionBar();
	    		actionBar.hide();
	    		return true;
	    	case R.id.go_back:
	    		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.back);
	        	clickSound.start();
	    		Intent intent = new Intent(this, MainMenuActivity.class);
	        	startActivity(intent);
	    	default:
	    		return super.onOptionsItemSelected(item);
	    	}
	    }
	
	public void loadAddUpdateInterface(View view) {
		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.click);
    	clickSound.start();
    	Intent intent = new Intent(this, AddUpdateActivity.class);
    	startActivity(intent); 	
    }
	
	public void loadDeleteInterface(View view) {
		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.click);
    	clickSound.start();
    	Intent intent = new Intent(this, DeleteActivity.class);
    	startActivity(intent); 	
    }
}
