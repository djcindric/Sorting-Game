package com.example.sortinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// force landscape
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        SoundManager.setContext(this);
        SoundManager.initializePlayers();
        
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
      
    //Handle Presses on the Action bar
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
            case R.id.action_settings:
            	loadSettings();
            default:
                    return super.onOptionsItemSelected(item);
            }
    }
    
    public void loadLevelInterface(View view) {
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }
    
    public void loadCustomizerInterface(View view) {
    	final Intent intent = new Intent(this, CustomizerActivity.class);
    	final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
    	
    	//Dialog box prompts user to input password and only proceeds to next menu if password is correct.
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter Password to Continue").setTitle("Customize");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String value = passwordView.getText().toString();
    			if(value.equals(prefs.getString("prefPassword", "NULL"))){
    				startActivity(intent); 
    			}	
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show();       
    }
    
    public void loadSettings() {
    	Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);         
    }}