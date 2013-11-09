package com.example.sortinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class LevelActivity extends Activity {
	public final static String LEVEL_NAME = "com.example.sortinggame.MESSAGE";
	ImageAdapterLevel imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		
		//Enable icon to function as back button
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		//Create a grid of icons for choosing a level
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    imageAdapter = new ImageAdapterLevel(this);
	    gridview.setAdapter(imageAdapter);

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
    	case R.id.mute:
        	SoundManager.playMusic(0);
        	return true;
        case R.id.sound:
        	disableSound();
        	return true;
    	case android.R.id.home:
    		NavUtils.navigateUpFromSameTask(this);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	
	public void disableSound(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Sound is currently " + SoundManager.checkSoundState() +
    				". Would you like to change it?").setTitle("Change Sound State");
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			SoundManager.playMusic(0);
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
	
	public void goBackToMain(View view){
		Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
	}
	
	//Load the game interface, and pass in the icon position
	public void goToGameInterface(View view, int position){
		String level = imageAdapter.getLevel(position);
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(LEVEL_NAME, level);
		//Stop menu music
		SoundManager.stopPlayer(0);
    	startActivity(intent);
	}
}


