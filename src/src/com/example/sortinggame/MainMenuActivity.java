package com.example.sortinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    
    //Handle Presses on the Action bar
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.mute:
            	SoundManager.playMusic(0);
            	return true;
            case R.id.sound:
            	disableSound();
            	return true;
            default:
                    return super.onOptionsItemSelected(item);
            }
    }
    
    public void loadLevelInterface(View view) {
    	SoundManager.playMusic(1);
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
    }
    
    public void loadCustomizerInterface(View view) {
    	SoundManager.playMusic(1);
    	Intent intent = new Intent(this, CustomizerActivity.class);
        startActivity(intent);         
    }
    
    public void disableSound(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Sound is currently " + SoundManager.checkSoundState(0) +
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
}