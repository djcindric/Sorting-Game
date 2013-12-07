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
        
        SoundManager.setContext(this); //Set the context for media player
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); //Obtain reference to preferences
        SharedPreferences.Editor editor = prefs.edit(); 
        final Intent intent = new Intent(this, SettingsActivity.class); //Intent to open settings
        
        //Initialize the background music player. Check if a specific value is set for it in settings
        int i = 0;
        if(prefs.getString("prefMusicSelection", "NULL") != "NULL"){
        	i = getResources().getIdentifier(prefs.getString("prefMusicSelection", "NULL"), "raw", getPackageName());
        }
        SoundManager.initializePlayers(i);
        
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
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button_off);
    	}
    	else{
    		menu.findItem(R.id.mute).setIcon(R.drawable.mute_button);
    	}
    	
    	return true;
    }
      
    //Handle Presses on the Action bar
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
    	final EditText passwordView2 = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	final View view1 = view;
    	
    	if(prefs.getString("prefPassword", "NULL") == "NULL"){
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage("Create A Password").setTitle("Set Password");
	    	builder.setView(passwordView2);
	    	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			String tempInput = passwordView2.getText().toString();
	    			editor.putString("prefPassword", tempInput);
	    			editor.commit();
	    			askAgain();
	    		}
	    	});
	    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    		}
	    	});
	    	AlertDialog dialog = builder.create();
	    	dialog.show(); 
    	}
    	
    	else{
	    	//Dialog box prompts user to input password and only proceeds to next menu if password is correct.
	    	AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
	    	builder2.setMessage("Enter Password to Continue").setTitle("Load Customize");
	    	builder2.setView(passwordView);
	    	builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			String value = passwordView.getText().toString();
	    			if(value.equals(prefs.getString("prefPassword", "NULL"))){
	    				startActivity(intent); 
	    			}	
	    			else{
	    				Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
	    				loadCustomizerInterface(view1);
	    			}
	    		}
	    	});
	    	builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			
	    		}
	    	});
	    	AlertDialog dialog3 = builder2.create();
	    	dialog3.show(); 
    	}
    }
    
    public void askAgain(){
    	final EditText passwordView = new EditText(this);
    	final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	final SharedPreferences.Editor editor = prefs.edit();
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Enter Password Again").setTitle("Confirm Password");
    	builder.setView(passwordView);
    	builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			String tempInput = passwordView.getText().toString();
    			if(tempInput.equals(prefs.getString("prefPassword", "NULL"))){
    				
    			}
    			else{
    				Toast.makeText(getApplicationContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
    				editor.putString("prefPassword", "NULL");
	    			editor.commit();
    			}
    		}
    	});
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int id) {
    			editor.putString("prefPassword", "NULL");
    			editor.commit();
    		}
    	});
    	AlertDialog dialog = builder.create();
    	dialog.show(); 
    }
    
    public void loadSettings(View v){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
    
    }