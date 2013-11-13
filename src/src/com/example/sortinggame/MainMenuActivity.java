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
}