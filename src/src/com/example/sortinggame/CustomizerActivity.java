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
import android.widget.EditText;

public class CustomizerActivity extends Activity {

	public final static String LEVEL_NAME = "com.example.sortinggame.MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Forces landscape mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//Enable icon to function as back button
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customizer);
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
		getMenuInflater().inflate(R.menu.customizer, menu);
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
	
	//Handle clicks on the action bar
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
    	case android.R.id.home:
    		NavUtils.navigateUpFromSameTask(this);
    		return true;	
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	
	public void loadUpdateInterface(View view) {
    	Intent intent = new Intent(this, AddUpdateActivity.class);
    	startActivity(intent); 	
    }
	
	public void loadAddInterface(View view) {
		final Intent intent = new Intent(this, EditCategoryActivity.class);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Level");
		alert.setMessage("Enter the name for your level");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  intent.putExtra(LEVEL_NAME, value);
		  startActivity(intent); 
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
    }
	public void loadDeleteInterface(View view) {
    	Intent intent = new Intent(this, DeleteActivity.class);
    	startActivity(intent); 	
    }
	
	
}
