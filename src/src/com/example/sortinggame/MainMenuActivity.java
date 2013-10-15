package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Prevent Screen From rotating
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    //Handle Presses on the Action bar
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.hide_bar:
    		ActionBar actionBar = getActionBar();
    		actionBar.hide();
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    public void loadLevelInterface(View view) {
    	MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.click);
    	clickSound.start();
    	Intent intent = new Intent(this, LevelActivity.class);
    	startActivity(intent);
    }
    
    public void loadCustomizerInterface(View view) {
    	MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.click);
    	clickSound.start();
    	Intent intent = new Intent(this, CustomizerActivity.class);
    	startActivity(intent); 	
    }
}
