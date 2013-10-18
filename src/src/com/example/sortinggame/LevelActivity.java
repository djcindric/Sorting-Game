package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class LevelActivity extends Activity {
	public final static String Icon_Position = "com.example.sortinggame.MESSAGE"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //Prevent Screen From rotating
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		
		//Enable icon to function as back button
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		//Create a grid of icons for choosing a level
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapterLevel(this));

	    //Load Game Interface when icon is clicked
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	goToGameInterface(v, position);
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level, menu);
		return true;
	}
	
	//Handle clicks on the action bar
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.hide_bar:
    		ActionBar actionBar = getActionBar();
    		actionBar.hide();
    		return true;
    	case android.R.id.home:
    		MediaPlayer clickSound = MediaPlayer.create(getBaseContext(), R.raw.click);
        	clickSound.start();
    		NavUtils.navigateUpFromSameTask(this);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	
	public void goBackToMain(View view){
		Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
	}
	
	//Load the game interface, and pass in the icon position
	public void goToGameInterface(View view, int position){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(Icon_Position, position);
    	startActivity(intent);
	}
}


